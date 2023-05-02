package app.presenter

import bstrees.model.dataBases.NodeData
import bstrees.model.dataBases.TreeData
import app.presenter.utils.TreeHeightPresenter
import kotlin.math.pow

object LayoutPresenter {

    private const val windowHeight = 800
    private const val windowWidth = 800
    private var edgeLengthY = 10


    fun getNodeSize(tree: TreeData): Int{
        val treeHeight = TreeHeightPresenter.getTreeHeight(tree)
        return windowHeight / 5 / (treeHeight + 1)
    }

    fun setTreeLayout(tree: TreeData){

        val treeHeight = TreeHeightPresenter.getTreeHeight(tree)

        edgeLengthY = windowHeight / (treeHeight + 1)

        tree.root?.let {root->
            root.posX = 800
            root.posY = 0

            setNodesLayout(root, 0)
        }

    }

    // This method will assign coordinates to the nodes of the tree
    private fun setNodesLayout(node: NodeData, height: Int){
        val edgeLengthX = windowWidth / 2.0.pow(height + 1).toInt()

        node.leftNode?.let {leftNode->
            leftNode.posX = node.posX - edgeLengthX
            leftNode.posY = node.posY + edgeLengthY

            setNodesLayout(leftNode, height + 1)
        }

        node.rightNode?.let { rightNode ->
            rightNode.posX = node.posX + edgeLengthX
            rightNode.posY = node.posY + edgeLengthY

            setNodesLayout(rightNode, height + 1)
        }
    }
}