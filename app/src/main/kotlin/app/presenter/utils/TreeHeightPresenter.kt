package app.presenter.utils

import bstrees.model.dataBases.NodeData
import bstrees.model.dataBases.TreeData
import java.lang.Integer.max

object TreeHeightPresenter {

    fun getTreeHeight(tree: TreeData): Int = getNodeHeight(tree.root)

    private fun getNodeHeight(node: NodeData?): Int {
        return node?.let { 1 + max(getNodeHeight(node.leftNode), getNodeHeight(node.rightNode))  } ?: 0
    }

}