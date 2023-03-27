package balancers

import BTree
import Node

abstract class Balancer<K : Comparable<K>, V, NODE_TYPE : Node<K, V, NODE_TYPE>, TREE_TYPE : BTree<K, V, NODE_TYPE, TREE_TYPE>>(
    protected val tree: TREE_TYPE
) {
    protected fun leftRotate() {
        val temp = tree.getLeftTree()
        tree.setLeftTree(temp?.getRightTree())
        temp?.setRightTree(tree)
        tree.updateHeight()
        temp?.updateHeight()
    }

    protected fun rightRotate() {
        val temp = tree.getRightTree()
        tree.setRightTree(temp?.getLeftTree())
        temp?.setLeftTree(tree)
        tree.updateHeight()
        temp?.updateHeight()
    }

    protected fun balanceFactory(): Int {
        return (tree.getRightTree()?.getRoot()?.getHeight() ?: 0) - (tree.getLeftTree()?.getRoot()?.getHeight() ?: 0)
    }

    abstract fun balance()
}
