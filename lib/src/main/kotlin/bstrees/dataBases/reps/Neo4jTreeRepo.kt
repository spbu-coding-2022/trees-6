package bstrees.dataBases.reps

import org.neo4j.driver.AuthTokens
import org.neo4j.driver.GraphDatabase
import org.neo4j.driver.TransactionContext
import bstrees.dataBases.SerializableNode
import bstrees.dataBases.SerializableTree
import java.io.Closeable

object Neo4jTreeRepo : Closeable, DBTreeRepo {
    private val driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "qwertyui"))
    private val session = driver.session()

    override fun getTree(treeType: String, treeName: String): SerializableTree? {
        var serializableTree: SerializableTree? = null

        session.executeRead { tx ->
            val resultRootKey = tx.run(
                "MATCH (tree:Tree {name: \$name, type: \$type})" +
                        "WITH tree MATCH (tree)-[:root]->(root) RETURN root.key AS key",
                mutableMapOf(
                    "name" to treeName,
                    "type" to treeType,
                ) as Map<String, Any>?
            )
            if (resultRootKey.hasNext()) {
                val info: Map<String, Any> = resultRootKey.next().asMap()
                serializableTree = SerializableTree(
                    treeName,
                    treeType,
                    getSerializedNodes(tx, info["key"].toString())
                )
            }
        }

        return serializableTree
    }


    private fun getSerializedNodes(tx: TransactionContext, nodeKey: String): SerializableNode? {
        var nodeData = mapOf<String, Any>()
        var leftSonKey = mapOf<String, Any>()
        var rightSonKey = mapOf<String, Any>()

        val resultNodeData = tx.run(
            "MATCH (node:Node {key: $nodeKey}) RETURN node.key AS key, node.value AS value, node.metadata AS metadata "
        )
        val resultLeftSonKey = tx.run(
            "MATCH (node:Node {key: $nodeKey}) MATCH (node)-[:leftSon]->(leftSon) RETURN leftSon.key as key "
        )
        val resultRightSonKey = tx.run(
            "MATCH (node:Node {key: $nodeKey}) MATCH (node)-[:rightSon]->(rightSon) RETURN rightSon.key as key "
        )

        if (resultNodeData.hasNext()) {
            nodeData = resultNodeData.next().asMap()
        }
        if (resultLeftSonKey.hasNext()) {
            leftSonKey = resultLeftSonKey.next().asMap()
        }
        if (resultRightSonKey.hasNext()) {
            rightSonKey = resultRightSonKey.next().asMap()
        }

        if (nodeData.isEmpty()) return null
        return SerializableNode(
            nodeData["key"].toString(),
            nodeData["value"].toString(),
            nodeData["metadata"].toString(),
            getSerializedNodes(tx, leftSonKey["key"].toString()),
            getSerializedNodes(tx, rightSonKey["key"].toString()),
        )
    }

    override fun setTree(serializableTree: SerializableTree) {

        deleteTree(serializableTree.treeType, serializableTree.name)

        session.executeWrite { tx ->
            tx.run(
                "CREATE (:Tree {name: \$name, type: \$type})",
                mutableMapOf(
                    "name" to serializableTree.name,
                    "type" to serializableTree.treeType,
                ) as Map<String, Any>?
            )

            serializableTree.root?.let { root ->
                setNeo4jNodes(tx, root)
                tx.run(
                    "MATCH (tree: Tree {name: \$name, type: \$type})" +
                            "MATCH (node: NewNode {key: ${root.key} })" +
                            "CREATE (tree)-[:root]->(node)" +
                            "REMOVE node:NewNode",
                    mutableMapOf(
                        "name" to serializableTree.name,
                        "type" to serializableTree.treeType,
                    ) as Map<String, Any>?
                )
            }
        }
    }

    private fun setNeo4jNodes(tx: TransactionContext, node: SerializableNode) {
        tx.run(
            "CREATE (:Node:NewNode {key: ${node.key}, value: ${node.value}, metadata: ${node.metadata} })"
        )
        node.leftNode?.let { leftNode ->
            setNeo4jNodes(tx, leftNode)
            tx.run(
                "MATCH (node: NewNode {key: ${node.key}})" +
                        "MATCH (leftSon: NewNode {key: ${leftNode.key}})" +
                        "CREATE (node)-[:leftSon]->(leftSon)" +
                        "REMOVE leftSon:NewNode"
            )
        }
        node.rightNode?.let { rightNode ->
            setNeo4jNodes(tx, rightNode)
            tx.run(
                "MATCH (node: NewNode {key: ${node.key}})" +
                        "MATCH (rightSon: NewNode {key: ${rightNode.key}})" +
                        "CREATE (node)-[:rightSon]->(rightSon)" +
                        "REMOVE rightSon:NewNode"
            )
        }
    }

    override fun deleteTree(treeType: String, treeName: String) {
        session.executeWrite { tx ->
            tx.run(
                "MATCH (tree: Tree {name: \$name, type: \$type})" +
                        "MATCH (tree)-[*]->(node:Node) " +
                        "DETACH DELETE tree, node",
                mutableMapOf(
                    "name" to treeName,
                    "type" to treeType,
                ) as Map<String, Any>?
            )
        }
    }

    override fun close() {
        session.close()
        driver.close()
    }
}