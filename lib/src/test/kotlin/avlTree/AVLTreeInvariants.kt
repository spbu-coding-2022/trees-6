package avlTree

import bstrees.model.trees.Node
import bstrees.model.trees.avl.AvlNode
import utils.TreesInvariants

@Suppress("UNCHECKED_CAST")
class AVLTreeInvariants<K : Comparable<K>, V, NODE_TYPE : Node<K, V, NODE_TYPE>> : TreesInvariants<K, V, NODE_TYPE>() {
    fun checkAvlTreeInvariants(root: AvlNode<K, V>?): Boolean = root == null || checkAvlHeightInvariants(root)
            && checkNodeInvariants(root as NODE_TYPE)

    private fun checkAvlHeightInvariants(node: AvlNode<K, V>): Boolean =
        ((node.rightNode?.height ?: 0) - (node.leftNode?.height ?: 0) in -1..1)
                && (node.leftNode == null || checkAvlHeightInvariants(node.leftNode!!))
                && (node.rightNode == null || checkAvlHeightInvariants(node.rightNode!!))

}