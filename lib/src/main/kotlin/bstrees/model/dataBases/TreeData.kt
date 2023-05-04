package bstrees.model.dataBases

import kotlinx.serialization.Serializable

@Serializable
class TreeData(
    val name: String,
    val treeType: String, //RB, AVL or BS in capital letters!!!
    val keyType: String,
    val valueType: String,
    val root: NodeData?,
)
