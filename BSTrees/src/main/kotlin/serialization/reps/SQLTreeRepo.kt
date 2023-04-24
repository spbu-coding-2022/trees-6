package serialization.reps

import mu.KotlinLogging
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction
import serialization.*
import java.io.File

private val logger = KotlinLogging.logger { }

object SQLTreeRepo : DBTreeRepo {

    private fun connectDB(dbName: String) {
        Database.connect("jdbc:sqlite:${File(dbName)}", "org.sqlite.JDBC")

        logger.info { "[SQLite] The connection to the database is established" }
    }

    private fun createTables() {
        transaction {
            SchemaUtils.create(TreesTable)
            SchemaUtils.create(NodesTable)
        }

        logger.info { "[SQLite] Database tables have been created successfully" }
    }

    override fun getTree(treeType: String, treeName: String): SerializableTree? {
        //TODO: Create a config file in which dbName will be written
        connectDB("SQLTreeDB")
        createTables()

        var treeEntity: TreeEntity? = null
        transaction {
            treeEntity = TreeEntity.find { (TreesTable.treeName eq treeName) and (TreesTable.treeType eq treeType) }
                .firstOrNull()
        }

        if (treeEntity == null) {
            logger.info { "[SQLite] Tree not found - treeName: $treeName, treeType: $treeType" }
            return null
        }

        var serializableTree: SerializableTree? = null
        transaction {
            treeEntity?.let { tree ->
                serializableTree = SerializableTree(
                    treeName,
                    tree.treeType,
                    tree.root?.toSerializableEntity(tree)
                )
            }
        }

        logger.info { "[SQLite] Got tree - treeName: $treeName, treeType: $treeType" }

        return serializableTree
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

    override fun setTree(serializableTree: SerializableTree) {
        //TODO: Create a config file in which dbName will be written
        connectDB("SQLTreeDB")
        createTables()

        deleteTree(serializableTree.treeType, serializableTree.name)

        transaction {
            val newTree = TreeEntity.new {
                treeName = serializableTree.name
                treeType = serializableTree.treeType
            }

            newTree.root = serializableTree.root?.toNodeEntity(newTree)
        }

        logger.info { "[SQLite] Set tree - treeName: ${serializableTree.name}, treeType: ${serializableTree.treeType}" }
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

    override fun deleteTree(treeType: String, treeName: String) {
        transaction {
            val treeEntity =
                TreeEntity.find { (TreesTable.treeName eq treeName) and (TreesTable.treeType eq treeType) }
                    .firstOrNull()
            treeEntity?.let { NodeEntity.find(NodesTable.tree eq treeEntity.id).forEach { it.delete() } }

            treeEntity?.delete()
        }

        logger.info { "[SQLite] Deleted tree - treeName: $treeName, treeType: $treeType" }
    }
}