package bstrees.model.dataBases.serialize.types

import kotlinx.serialization.Serializable

@Serializable
class SerializableNode(
    val key: String,
    val value: String,
    val metadata: String, //If Rb meta is RED or BLACK in capital letters

    //layout
    var posX: Int,
    var posY: Int,

    val leftNode: SerializableNode? = null,
    val rightNode: SerializableNode? = null,
)