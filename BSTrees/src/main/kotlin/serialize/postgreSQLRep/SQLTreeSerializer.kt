package serialize.postgreSQLRep

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import serialize.*
import java.io.File

class SQLTreeSerializer {
    
    private fun connectBD(file: File){
        Database.connect("jdbc:sqlite:${file}", "org.sqlite.JDBC")
    }

    private fun createNewTreeWithName(nameTree: String){
        connectBD(File("treesTable"))

        transaction {
            SchemaUtils.create(TreeTable)

            val correctTable = TreeString.find { TreeTable.nameTree eq nameTree }
            if (correctTable.empty()) {
                TreeTable.batchInsert(mutableListOf(nameTree)) {
                    this[TreeTable.nameTree] = it
                }
            }
            else{
                throw Exception("A tree with that name already exists")
            }
        }
    }

    fun findTreeByName(inputNameTree: String): String {
        connectBD(File("treesTable"))

        val nameTree = mutableListOf<String>()
        transaction {
            SchemaUtils.create(TreeTable)
            val correctTable = TreeString.find { TreeTable.nameTree eq inputNameTree }
            if (!correctTable.empty()) {
                correctTable.forEach { nameTree.add(it.nameTree.toString())}
            } else {
                throw Exception("There is no tree with that name")
            }
        }
        return nameTree[0]
    }

    fun serializeAvlTree(file: File, serializableAvlTree: SerializableAvlTree) {
        createNewTreeWithName(serializableAvlTree.name)

        connectBD(file)
        transaction {
            val tree = AvlNode(serializableAvlTree.name)
            SchemaUtils.drop(tree)
            SchemaUtils.create(tree)

            tree.batchInsert(serializableAvlTree.nodes){
                this[tree.id] = it.key
                this[tree.value] = it.value
                this[tree.height] = it.height
                this[tree.size] = it.size
                this[tree.leftNodeKey] = it.leftSonKey.toString()
                this[tree.rightNodeKey] = it.rightSonKey.toString()
                this[tree.parentKey] = it.parentKey.toString()
            }
        }
    }

    fun deserializeAvlTree(file: File, tableName: String): SerializableAvlTree {
        connectBD(file)

        val tree = AvlNode(tableName)
        val nodeList = mutableListOf<AvlNodeMetaData>()
        transaction {
            tree.selectAll().forEach{
                val node =  AvlNodeMetaData(
                    key= it[tree.id].value,
                    value= it[tree.value],
                    height= it[tree.height],
                    size= it[tree.size],
                    leftSonKey= it[tree.leftNodeKey],
                    rightSonKey= it[tree.rightNodeKey],
                    parentKey= it[tree.parentKey]
                )
                nodeList.add(node)
            }
        }
        return SerializableAvlTree(tableName, nodeList)
    }
}
