package serialization.reps

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption


object TreesTable : IntIdTable() {
    val treeName = varchar("nameTree", 20).uniqueIndex().default("")
    val treeType = varchar("typeTree", 20).default("")
    val root = reference("root", NodesTable).nullable()
}

class TreeEntity(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<TreeEntity>(TreesTable)

    var treeName by TreesTable.treeName
    var treeType by TreesTable.treeType
    var root by NodeEntity optionalReferencedOn TreesTable.root
}

object NodesTable : IntIdTable(){
    val key = varchar("key", 255)
    val value = varchar("value", 255)
    val metadata = varchar("metadata", 255)
    val leftNode = reference("leftNode", NodesTable).nullable()
    val rightNode = reference("rightNode", NodesTable).nullable()
    val tree = reference("tree", TreesTable, onDelete = ReferenceOption.CASCADE)
}

class NodeEntity(id: EntityID<Int>) : IntEntity(id){
    companion object : IntEntityClass<NodeEntity>(NodesTable)

    var key by NodesTable.key
    var value by NodesTable.value
    var metadata by NodesTable.metadata
    var leftNode by NodeEntity optionalReferencedOn NodesTable.leftNode
    var rightNode by NodeEntity optionalReferencedOn NodesTable.rightNode
    var tree by TreeEntity referencedOn NodesTable.tree
}
