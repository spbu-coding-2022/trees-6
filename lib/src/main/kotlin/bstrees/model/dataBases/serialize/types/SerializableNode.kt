package bstrees.model.dataBases.serialize.types

import kotlinx.serialization.Serializable

@Serializable
class SerializableNode(
    val key: String,
    val value: String,
    val metadata: String,
    val leftNode: SerializableNode? = null,
    val rightNode: SerializableNode? = null,
)