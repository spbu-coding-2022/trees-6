package redBlackTree

import bstrees.model.trees.Node
import bstrees.model.trees.redBlack.RBNode
import utils.TreesInvariants

@Suppress("UNCHECKED_CAST")
class RBTreeInvariants<K : Comparable<K>, V, NODE_TYPE : Node<K, V, NODE_TYPE>> : TreesInvariants<K, V, NODE_TYPE>() {
    fun checkRBTreeInvariants(root: RBNode<K, V>?): Boolean {
        return root == null || root.parent == null && checkNodeInvariants(root as NODE_TYPE) && root.color == RBNode.Color.BLACK &&
                checkRBNodeBlackHeightInvariant(root, 0, getBlackHeightExample(root)) &&
                checkRBNodeBlackParentForRedInvariant(root)
    }

    private fun checkRBNodeBlackParentForRedInvariant(node: RBNode<K, V>): Boolean {
        return (node.color == RBNode.Color.BLACK || node.parent!!.color == RBNode.Color.BLACK) &&
                (node.leftNode == null || checkRBNodeBlackParentForRedInvariant(node.leftNode!!)) &&
                (node.rightNode == null || checkRBNodeBlackParentForRedInvariant(node.rightNode!!))
    }

    private fun checkRBNodeBlackHeightInvariant(node: RBNode<K, V>, curHeight: Int, rightHeight: Int): Boolean {
        val newCurHeight = curHeight + if (node.color == RBNode.Color.BLACK) 1 else 0
        if (node.leftNode == null && node.rightNode == null) {
            return newCurHeight == rightHeight
        }
        return ((node.leftNode == null || checkRBNodeBlackHeightInvariant(
            node.leftNode!!,
            newCurHeight,
            rightHeight
        )) &&
                (node.rightNode == null || checkRBNodeBlackHeightInvariant(
                    node.rightNode!!,
                    newCurHeight,
                    rightHeight
                )))
    }

    /**
     * We take the height of the leftmost leaf in order to compare it
     * with the heights to other leaves of the tree
     */
    private fun getBlackHeightExample(root: RBNode<K, V>): Int {
        var curNode: RBNode<K, V>? = root
        var blackHeight = 0
        while (curNode != null) {
            if (curNode.color == RBNode.Color.BLACK) {
                ++blackHeight
            }
            curNode = curNode.leftNode
        }
        return blackHeight
    }
}