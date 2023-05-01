package app.presenter.utils

import bstrees.model.dataBases.serialize.types.SerializableNode
import bstrees.model.dataBases.serialize.types.SerializableTree
import java.lang.Integer.max

object TreeHeightPresenter {

    fun getTreeHeight(tree: SerializableTree): Int = getNodeHeight(tree.root)

    private fun getNodeHeight(node: SerializableNode?): Int {
        return node?.let { 1 + max(getNodeHeight(node.leftNode), getNodeHeight(node.rightNode))  } ?: 0
    }

}