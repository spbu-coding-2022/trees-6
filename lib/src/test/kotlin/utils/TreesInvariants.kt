package utils

import bstrees.model.trees.BSNode

open class TreesInvariants<K : Comparable<K>, V, NODE_TYPE : BSNode<K, V, NODE_TYPE>> {
    protected fun checkNodeInvariants(node: NODE_TYPE): Boolean {
        return (node.leftNode == null || node.leftNode!!.parent == node &&
                node.leftNode!!.key < node.key && checkNodeInvariants(node.leftNode!!)) &&
                (node.rightNode == null || node.rightNode!!.parent == node &&
                        node.rightNode!!.key > node.key && checkNodeInvariants(node.rightNode!!))
    }
}