package bstrees.model.dataBases.serialize.types

import kotlinx.serialization.Serializable

@Serializable
class SerializableNode(
    val key: String,
    val value: String,
    val metadata: String, //If Rb meta is RED or BLACK in capital letters
    val leftNode: SerializableNode? = null,
    val rightNode: SerializableNode? = null,

    //layout
    var posX: Double = 0.0,
    var posY: Double = 0.0,
)
