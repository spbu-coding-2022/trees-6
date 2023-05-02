package bstrees.model.dataBases.reps

import mu.KotlinLogging
import org.neo4j.driver.AuthTokens
import org.neo4j.driver.GraphDatabase
import org.neo4j.driver.TransactionContext
import bstrees.model.dataBases.NodeData
import bstrees.model.dataBases.TreeData
import java.io.Closeable

private val logger = KotlinLogging.logger { }

class Neo4jTreeRepo(host: String, username: String, password: String) : Closeable, TreeRepo {
    private val driver = GraphDatabase.driver(host, AuthTokens.basic(username, password))
    private val session = driver.session()

    override fun getTree(treeName: String, treeType: String): TreeData? {
        var treeData: TreeData? = null

        session.executeRead { tx ->
            val resultTreeInfo = tx.run(
                "MATCH (tree: Tree {name: \$name, type: \$type}) RETURN tree.keyType as keyType, tree.valueType as valueType",
                mutableMapOf(
                    "name" to treeName,
                    "type" to treeType,
                ) as Map<String, Any>?
            )
            val resultRootKey = tx.run(
                "MATCH (tree: Tree {name: \$name, type: \$type}) " +
                        "WITH tree MATCH (tree)-[:root]->(root) RETURN root.key AS rootKey",
                mutableMapOf(
                    "name" to treeName,
                    "type" to treeType,
                ) as Map<String, Any>?
            )

            var rootKey = ""
            if (resultRootKey.hasNext()) {
                val info: Map<String, Any> = resultRootKey.next().asMap()
                rootKey = info["rootKey"].toString()
            }

            if (resultTreeInfo.hasNext()) {
                val info: Map<String, Any> = resultTreeInfo.next().asMap()
                treeData = TreeData(
                    treeName,
                    treeType,
                    info["keyType"].toString(),
                    info["valueType"].toString(),
                    if (rootKey != "") getSerializedNodes(tx, rootKey) else null
                )
            }
        }

        logger.info { "[NEO4J] Got tree - treeName: $treeName, treeType: $treeType" }

        return treeData
    }


    private fun getSerializedNodes(tx: TransactionContext, nodeKey: String): NodeData? {
        var nodeData = mapOf<String, Any>()
        var leftSonKey = mapOf<String, Any>()
        var rightSonKey = mapOf<String, Any>()

        val resultNodeData = tx.run(
            "MATCH (node:Node {key: $nodeKey}) RETURN node.key AS key, node.value AS value, node.metadata AS metadata, node.posX AS posX, node.posY AS posY"
        )
        val resultLeftSonKey = tx.run(
            "MATCH (node:Node {key: $nodeKey}) MATCH (node)-[:leftSon]->(leftSon) RETURN leftSon.key as key"
        )
        val resultRightSonKey = tx.run(
            "MATCH (node:Node {key: $nodeKey}) MATCH (node)-[:rightSon]->(rightSon) RETURN rightSon.key as key"
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
        return NodeData(
            nodeData["key"].toString(),
            nodeData["value"].toString(),
            nodeData["metadata"].toString(),
            nodeData["posX"].toString().toInt(),
            nodeData["posY"].toString().toInt(),
            getSerializedNodes(tx, leftSonKey["key"].toString()),
            getSerializedNodes(tx, rightSonKey["key"].toString()),
        )
    }

    override fun setTree(treeData: TreeData) {

        deleteTree(treeData.name, treeData.treeType)

        session.executeWrite { tx ->
            tx.run(
                "CREATE (:Tree {name: \$name, type: \$type, keyType: \$keyType, valueType: \$valueType})",
                mutableMapOf(
                    "name" to treeData.name,
                    "type" to treeData.treeType,
                    "keyType" to treeData.keyType,
                    "valueType" to treeData.valueType,
                ) as Map<String, Any>?
            )

            treeData.root?.let { root ->
                setNeo4jNodes(tx, root)
                tx.run(
                    "MATCH (tree: Tree {name: \$name, type: \$type}) " +
                            "MATCH (node: NewNode {key: ${root.key} }) " +
                            "CREATE (tree)-[:root]->(node) " +
                            "REMOVE node:NewNode",
                    mutableMapOf(
                        "name" to treeData.name,
                        "type" to treeData.treeType,
                    ) as Map<String, Any>?
                )
            }
        }

        logger.info { "[NEO4J] Set tree - treeName: ${treeData.name}, treeType: ${treeData.treeType}" }
    }

    private fun setNeo4jNodes(tx: TransactionContext, node: NodeData) {
        tx.run(
            "CREATE (:Node:NewNode {key: ${node.key}, value: ${node.value}, " +
                    "metadata: ${node.metadata}, posX: ${node.posX}, posY: ${node.posY} })"
        )
        node.leftNode?.let { leftNode ->
            setNeo4jNodes(tx, leftNode)
            tx.run(
                "MATCH (node: NewNode {key: ${node.key}}) " +
                        "MATCH (leftSon: NewNode {key: ${leftNode.key}}) " +
                        "CREATE (node)-[:leftSon]->(leftSon) " +
                        "REMOVE leftSon:NewNode"
            )
        }
        node.rightNode?.let { rightNode ->
            setNeo4jNodes(tx, rightNode)
            tx.run(
                "MATCH (node: NewNode {key: ${node.key}}) " +
                        "MATCH (rightSon: NewNode {key: ${rightNode.key}}) " +
                        "CREATE (node)-[:rightSon]->(rightSon) " +
                        "REMOVE rightSon:NewNode"
            )
        }
    }

    override fun deleteTree(treeName: String, treeType: String) {
        session.executeWrite { tx ->
            tx.run(
                "MATCH (tree: Tree {name: \$name, type: \$type}) " +
                        "MATCH (tree)-[*]->(node:Node) " +
                        "DETACH DELETE tree, node",
                mutableMapOf(
                    "name" to treeName,
                    "type" to treeType,
                ) as Map<String, Any>?
            )
        }

        logger.info { "[NEO4J] Deleted tree - treeName: $treeName, treeType: $treeType" }
    }

    override fun close() {
        session.close()
        driver.close()

        logger.info { "[NEO4J] The connection to the database is finished" }
    }
}