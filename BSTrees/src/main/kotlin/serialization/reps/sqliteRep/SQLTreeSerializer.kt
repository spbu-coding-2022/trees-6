package serialization.reps.sqliteRep

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction
import serialization.*
import java.io.File

object SQLTreeSerializer {

    private fun connectBD(file: File) {
        Database.connect("jdbc:sqlite:${file}", "org.sqlite.JDBC")
    }

    private fun createTables(){
        transaction {
            SchemaUtils.create(TreesTable)
            SchemaUtils.create(NodesTable)
        }
    }

    private fun deleteTree(serializableTree: SerializableTree) {
        transaction {
            val treeEntity =
                TreeEntity.find { (TreesTable.nameTree eq serializableTree.name) and (TreesTable.typeTree eq serializableTree.typeOfTree) }
                    .firstOrNull()
            treeEntity?.let{NodeEntity.find(NodesTable.tree eq treeEntity.id).forEach { it.delete() } }

            treeEntity?.delete()
        }
    }

    fun setTree(file: File, serializableTree: SerializableTree) {
        connectBD(file)
        createTables()

        deleteTree(serializableTree)

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
            parentNode = this@toNodeEntity.parent?.toNodeEntity(treeEntity)
            tree = treeEntity
        }
    }

    fun getTree(file: File, treeName: String): SerializableTree? {
        connectBD(file)

        val treeEntity = TreeEntity.find { TreesTable.nameTree eq treeName }.firstOrNull() ?: return null

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
            this@toSerializableEntity.parentNode?.toSerializableEntity(treeEntity)
        )
    }
}