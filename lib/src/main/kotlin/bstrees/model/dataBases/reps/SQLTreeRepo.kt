package bstrees.model.dataBases.reps

import bstrees.model.dataBases.NodeData
import bstrees.model.dataBases.TreeData
import mu.KotlinLogging
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import java.io.File


private val logger = KotlinLogging.logger { }

object TreesTable : IntIdTable() {
    val treeName = varchar("nameTree", 20)
    val treeType = varchar("typeTree", 20)
    val keyType = varchar("keyType", 20)
    val valueType = varchar("valueType", 20)
    val root = reference("root", NodesTable).nullable()
}

class TreeEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<TreeEntity>(TreesTable)

    var treeName by TreesTable.treeName
    var treeType by TreesTable.treeType
    var keyType by TreesTable.keyType
    var valueType by TreesTable.valueType
    var root by NodeEntity optionalReferencedOn TreesTable.root
}

object NodesTable : IntIdTable() {
    val key = varchar("key", 255)
    val value = varchar("value", 255)
    val metadata = varchar("metadata", 255)
    val leftNode = reference("leftNode", NodesTable).nullable()
    val rightNode = reference("rightNode", NodesTable).nullable()
    val posX = integer("posX")
    val posY = integer("posY")
    val tree = reference("tree", TreesTable, onDelete = ReferenceOption.CASCADE)
}

class NodeEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<NodeEntity>(NodesTable)

    var key by NodesTable.key
    var value by NodesTable.value
    var metadata by NodesTable.metadata
    var leftNode by NodeEntity optionalReferencedOn NodesTable.leftNode
    var rightNode by NodeEntity optionalReferencedOn NodesTable.rightNode
    var posX by NodesTable.posX
    var posY by NodesTable.posY
    var tree by TreeEntity referencedOn NodesTable.tree
}

class SQLTreeRepo(dbName: String) : TreeRepo {

    init {
        connectDB(dbName)
        createTables()
    }

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

    override fun getTree(treeName: String, treeType: String): TreeData? {
        var treeEntity: TreeEntity? = null
        transaction {
            treeEntity = TreeEntity.find { (TreesTable.treeName eq treeName) and (TreesTable.treeType eq treeType) }
                .firstOrNull()
        }

        if (treeEntity == null) {
            logger.info { "[SQLite] Tree not found - treeName: $treeName, treeType: $treeType" }
            return null
        }

        var treeData: TreeData? = null
        transaction {
            treeEntity?.let { tree ->
                treeData = TreeData(
                    treeName,
                    tree.treeType,
                    tree.keyType,
                    tree.valueType,
                    tree.root?.toSerializableEntity(tree)
                )
            }
        }

        logger.info { "[SQLite] Got tree - treeName: $treeName, treeType: $treeType" }

        return treeData
    }

    private fun NodeEntity.toSerializableEntity(treeEntity: TreeEntity): NodeData {
        return NodeData(
            this@toSerializableEntity.key,
            this@toSerializableEntity.value,
            this@toSerializableEntity.metadata,
            this@toSerializableEntity.posX,
            this@toSerializableEntity.posY,
            this@toSerializableEntity.leftNode?.toSerializableEntity(treeEntity),
            this@toSerializableEntity.rightNode?.toSerializableEntity(treeEntity),
        )
    }

    override fun setTree(treeData: TreeData) {
        deleteTree(treeData.name, treeData.treeType)

        transaction {
            val newTree = TreeEntity.new {
                treeName = treeData.name
                treeType = treeData.treeType
                keyType = treeData.keyType
                valueType = treeData.valueType
            }

            newTree.root = treeData.root?.toNodeEntity(newTree)
        }

        logger.info { "[SQLite] Set tree - treeName: ${treeData.name}, treeType: ${treeData.treeType}" }
    }

    private fun NodeData.toNodeEntity(treeEntity: TreeEntity): NodeEntity {
        return NodeEntity.new {
            key = this@toNodeEntity.key
            value = this@toNodeEntity.value
            metadata = this@toNodeEntity.metadata
            leftNode = this@toNodeEntity.leftNode?.toNodeEntity(treeEntity)
            rightNode = this@toNodeEntity.rightNode?.toNodeEntity(treeEntity)
            posX = this@toNodeEntity.posX
            posY = this@toNodeEntity.posY
            tree = treeEntity
        }
    }

    override fun deleteTree(treeName: String, treeType: String) {
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