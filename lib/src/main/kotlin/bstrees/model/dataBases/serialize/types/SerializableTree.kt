package bstrees.model.dataBases.serialize.types

import kotlinx.serialization.Serializable

@Serializable
class SerializableTree(
    val name: String,
    val treeType: String, //RB, AVL or BS in capital letters!!!
    val keyType: String,
    val valueType: String,
    val root: SerializableNode?,
)
