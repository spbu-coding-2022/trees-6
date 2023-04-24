package treeInvariants

import trees.Node
import trees.avl.AvlNode
import trees.binarySearch.BSNode
import trees.redBlack.RBNode
import trees.redBlack.RBNode.Color

@Suppress("UNCHECKED_CAST")
class TreesInvariants<K : Comparable<K>, V, NODE_TYPE : Node<K, V, NODE_TYPE>> {

    fun checkRBTreeInvariants(root: RBNode<K, V>?): Boolean {
        return root == null || root.parent == null && checkNodeInvariants(root as NODE_TYPE) && root.color == Color.BLACK &&
                checkRBNodeBlackHeightInvariant(root, 0, getSomeBlackHeight(root)) &&
                checkRBNodeBlackParentForRedInvariant(root)
    }

    private fun checkRBNodeBlackParentForRedInvariant(node: RBNode<K, V>): Boolean {
        return (node.color == Color.BLACK || node.parent!!.color == Color.BLACK) &&
                (node.leftNode == null || checkRBNodeBlackParentForRedInvariant(node.leftNode!!)) &&
                (node.rightNode == null || checkRBNodeBlackParentForRedInvariant(node.rightNode!!))
    }

    private fun checkRBNodeBlackHeightInvariant(node: RBNode<K, V>, curHeight: Int, rightHeight: Int): Boolean {
        val newCurHeight = curHeight + if (node.color == Color.BLACK) 1 else 0
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

    private fun getSomeBlackHeight(root: RBNode<K, V>): Int {
        var curNode: RBNode<K, V>? = root
        var blackHeight = 0
        while (curNode != null) {
            if (curNode.color == Color.BLACK) {
                ++blackHeight
            }
            curNode = curNode.leftNode
        }
        return blackHeight
    }

    private fun checkNodeInvariants(node: NODE_TYPE): Boolean {
        return (node.leftNode == null || node.leftNode!!.parent == node &&
                node.leftNode!!.key < node.key && checkNodeInvariants(node.leftNode!!)) &&
                (node.rightNode == null || node.rightNode!!.parent == node &&
                        node.rightNode!!.key > node.key && checkNodeInvariants(node.rightNode!!))
    }

    fun checkAvlTreeInvariants(root: AvlNode<K, V>?): Boolean = root == null || checkAvlHeightInvariants(root)
            && checkNodeInvariants(root as NODE_TYPE)

    private fun checkAvlHeightInvariants(node: AvlNode<K, V>): Boolean =
        ((node.rightNode?.height ?: 0) - (node.leftNode?.height ?: 0) in -1..1)
                && (node.leftNode == null || checkAvlHeightInvariants(node.leftNode!!))
                && (node.rightNode == null || checkAvlHeightInvariants(node.rightNode!!))


    private fun checkBSSizeInvariant(node: BSNode<K, V>?): Boolean {
        return node == null ||
                node.size == (node.leftNode?.size ?: 0) + (node.rightNode?.size ?: 0) + 1
                && checkBSSizeInvariant(node.leftNode) && checkBSSizeInvariant(node.rightNode)
    }

    fun checkBsTreeInvariants(root: BSNode<K, V>?): Boolean {
        return (root == null || checkNodeInvariants(root as NODE_TYPE)) && checkBSSizeInvariant(root)
    }

}