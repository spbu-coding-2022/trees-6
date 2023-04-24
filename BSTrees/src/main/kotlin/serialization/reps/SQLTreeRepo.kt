package serialization.reps

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction
import serialization.*
import java.io.File

object SQLTreeRepo: DBTreeRepo{

    private fun connectDB(dbName: String) {
        Database.connect("jdbc:sqlite:${File(dbName)}", "org.sqlite.JDBC")
    }

    private fun createTables(){
        transaction {
            SchemaUtils.create(TreesTable)
            SchemaUtils.create(NodesTable)
        }
    }

    override fun deleteTree(treeType: String, treeName: String) {
        transaction {
            val treeEntity =
                TreeEntity.find { (TreesTable.treeName eq treeName) and (TreesTable.treeType eq treeType) }
                    .firstOrNull()
            treeEntity?.let{ NodeEntity.find(NodesTable.tree eq treeEntity.id).forEach { it.delete() } }

            treeEntity?.delete()
        }
    }

    override fun setTree(serializableTree: SerializableTree) {
        //TODO: Create a config file in which dbName will be written
        connectDB("SQLTreeDB")
        createTables()

        deleteTree(serializableTree.treeType, serializableTree.name)

        transaction {
            val newTree = TreeEntity.new {
                treeName=serializableTree.name
                treeType=serializableTree.treeType
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

    override fun getTree(treeType: String, treeName: String): SerializableTree? {
        //TODO: Create a config file in which dbName will be written
        connectDB("SQLTreeDB")
        createTables()

        val treeEntity = TreeEntity.find { (TreesTable.treeName eq treeName) and (TreesTable.treeType eq treeType) }.firstOrNull() ?: return null

        return SerializableTree(
            treeName,
            treeEntity.treeType,
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