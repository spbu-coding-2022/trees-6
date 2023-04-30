package bstrees.presenter

import bstrees.model.dataBases.serialize.types.SerializableNode
import bstrees.model.dataBases.serialize.types.SerializableTree

object LayoutPresenter {

    private val defaultNodeLength = 1
    private val windowHeight = 800
    private val windowWidth = 800

    fun setTreeLayout(tree: SerializableTree, height: Int, width: Int){

    }

    // This method will assign coordinates to the nodes of the tree
    private fun setNodesLayout(node:  SerializableNode){
        node.posX = 0.0
        node.posY = 0.0

        node.leftNode?.let { setNodesLayout(it) }
        node.rightNode?.let { setNodesLayout(it) }

    }

}