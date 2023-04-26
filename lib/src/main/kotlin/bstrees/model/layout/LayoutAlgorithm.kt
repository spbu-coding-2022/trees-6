package bstrees.model.layout

import bstrees.model.dataBases.serialize.types.SerializableNode

object LayoutAlgorithm {

    // This method will assign coordinates to the nodes of the tree
    fun setNodesLayout(node:  SerializableNode){
        node.posX = 0.0
        node.posY = 0.0

        node.leftNode?.let { setNodesLayout(it) }
        node.rightNode?.let { setNodesLayout(it) }

    }

}