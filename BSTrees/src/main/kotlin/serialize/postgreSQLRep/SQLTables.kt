package serialize.postgreSQLRep

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.dao.id.IntIdTable



class AvlNode(name: String) : IdTable<String>(name){
    override val id = varchar("key", 255).entityId()
    val value = varchar("value", 255)
    val height = integer("height")
    val size = integer("size")
    val leftNodeKey = varchar("leftNodeKey", 255).nullable()
    val rightNodeKey = varchar("rightNodeKey", 255).nullable()
    val parentKey = varchar("parent", 255).nullable()
}

object TreeTable : IntIdTable() {
    val nameTree = varchar("nameTree", 20).entityId()
}

class TreeString(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<TreeString>(TreeTable)

    var nameTree by TreeTable.nameTree
}