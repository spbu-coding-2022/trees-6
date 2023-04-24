package bstrees.dataBases.reps

import bstrees.dataBases.SerializableNode
import bstrees.dataBases.SerializableTree
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File

object SQLTreeRepo: DBTreeRepo {


    private fun connectDB(dbName: String) {
        Database.connect("jdbc:sqlite:${File(dbName)}", "org.sqlite.JDBC")
    }

    private fun createTables(){
        transaction {
            SchemaUtils.create(TreesTable)
            SchemaUtils.create(NodesTable)
        }
    }

    override fun deleteTree(typeTree: String, treeName: String) {
        transaction {
            val treeEntity =
                TreeEntity.find { (TreesTable.nameTree eq treeName) and (TreesTable.typeTree eq typeTree) }
                    .firstOrNull()
            treeEntity?.let{ NodeEntity.find(NodesTable.tree eq treeEntity.id).forEach { it.delete() } }

            treeEntity?.delete()
        }
    }

    override fun setTree(serializableTree: SerializableTree) {
        //TODO: Create a config file in which dbName will be written
        connectDB("SQLTreeDB")
        createTables()

        deleteTree(serializableTree.typeOfTree, serializableTree.name)

        transaction {
            val newTree = TreeEntity.new {
                nameTree=serializableTree.name
                typeTree=serializableTree.typeOfTree
            }

            newTree.root = serializableTree.root?.toNodeEntity(newTree)
        }
    }

    private fun SerializableNode.toNodeEntity(treeEntity: TreeEntity): NodeEntity {
        return NodeEntity.new {
            key = this@toNodeEntity.key
            value = this@toNodeEntity.value
            metadata = this@toNodeEntity.metadata
            leftNode = this@toNodeEntity.leftNode?.toNodeEntity(treeEntity)
            rightNode = this@toNodeEntity.rightNode?.toNodeEntity(treeEntity)
            tree = treeEntity
        }
    }

    override fun getTree(typeTree: String, treeName: String): SerializableTree? {
        //TODO: Create a config file in which dbName will be written
        connectDB("SQLTreeDB")
        createTables()

        val treeEntity = TreeEntity.find { (TreesTable.nameTree eq treeName) and (TreesTable.typeTree eq typeTree) }.firstOrNull() ?: return null

        return SerializableTree(
            treeName,
            treeEntity.typeTree,
            treeEntity.root?.toSerializableEntity(treeEntity)
        )
    }

    private fun NodeEntity.toSerializableEntity(treeEntity: TreeEntity): SerializableNode {
        return SerializableNode(
            this@toSerializableEntity.key,
            this@toSerializableEntity.value,
            this@toSerializableEntity.metadata,
            this@toSerializableEntity.leftNode?.toSerializableEntity(treeEntity),
            this@toSerializableEntity.rightNode?.toSerializableEntity(treeEntity),
        )
    }
}