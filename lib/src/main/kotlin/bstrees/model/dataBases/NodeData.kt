package bstrees.model.dataBases

import kotlinx.serialization.Serializable

@Serializable
class NodeData(
    val key: String,
    val value: String,
    val metadata: String, //If RB meta is RED or BLACK in capital letters

    //layout
    var posX: Int,
    var posY: Int,

    val leftNode: NodeData? = null,
    val rightNode: NodeData? = null,
)