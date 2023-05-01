package app.presenter

import bstrees.model.dataBases.serialize.types.SerializableNode
import bstrees.model.dataBases.serialize.types.SerializableTree
import app.presenter.utils.TreeHeightPresenter

object LayoutPresenter {

    private const val nodeRadius = 50
    private const val edgeLength = 50


    fun setTreeLayout(tree: SerializableTree, windowHeight: Int, windowWidth: Int){

        val treeHeight = TreeHeightPresenter.getTreeHeight(tree)

        tree.root?.let {root->
            root.posX = 0
            root.posY = 0

            setNodesLayout(root, treeHeight)
        }
    }

    // This method will assign coordinates to the nodes of the tree
    private fun setNodesLayout(node:  SerializableNode, height: Int){
        val xDiff = (edgeLength + 2 * nodeRadius) * height + (height - 1)
        val yDiff = (edgeLength + 2 * nodeRadius)
        node.leftNode?.let {leftNode->
            leftNode.posX = node.posX - xDiff
            leftNode.posY = node.posY + yDiff

            setNodesLayout(leftNode, height - 1)
        }

        node.rightNode?.let { rightNode ->
            rightNode.posX = node.posX + xDiff
            rightNode.posY = node.posY + yDiff

            setNodesLayout(rightNode, height - 1)
        }
    }

}