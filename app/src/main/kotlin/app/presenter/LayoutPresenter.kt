package app.presenter

import bstrees.model.dataBases.NodeData
import bstrees.model.dataBases.TreeData
import app.presenter.utils.TreeHeightPresenter

object LayoutPresenter {

    private const val windowHeight = 800
    private const val windowWidth = 800
    private const val nodeSize = 30
    private const val edgeLength = 10


    fun setTreeLayout(tree: TreeData, windowHeight: Int, windowWidth: Int){

        val treeHeight = TreeHeightPresenter.getTreeHeight(tree)

        tree.root?.let {root->
            root.posX = 800
            root.posY = 0

            setNodesLayout(root, treeHeight)
        }

    }

    // This method will assign coordinates to the nodes of the tree
    private fun setNodesLayout(node: NodeData, height: Int){
        val xDiff = (edgeLength + 2 * nodeSize) * height + (height - 1)
        val yDiff = (edgeLength + 2 * nodeSize) * 3
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