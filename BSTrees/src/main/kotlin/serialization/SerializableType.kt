package serialization

import kotlinx.serialization.Serializable

@Serializable
class SerializableTree(
    val name: String,
    val typeOfTree: String,
    val root: SerializableNode?
)

@Serializable
class SerializableNode(
    val key: String,
    val value: String,
    val metadata: String,
    val leftNode: SerializableNode? = null,
    val rightNode: SerializableNode? = null,
)