package binarySearchTree

import bstrees.model.trees.Node
import bstrees.model.trees.binarySearch.BSNode
import utils.TreesInvariants

@Suppress("UNCHECKED_CAST")
class BSTreeInvariants<K : Comparable<K>, V, NODE_TYPE : Node<K, V, NODE_TYPE>> : TreesInvariants<K, V, NODE_TYPE>() {
    private fun checkBSSizeInvariant(node: BSNode<K, V>?): Boolean {
        return node == null ||
                node.size == (node.leftNode?.size ?: 0) + (node.rightNode?.size ?: 0) + 1
                && checkBSSizeInvariant(node.leftNode) && checkBSSizeInvariant(node.rightNode)
    }

    fun checkBsTreeInvariants(root: BSNode<K, V>?): Boolean {
        return (root == null || checkNodeInvariants(root as NODE_TYPE)) && checkBSSizeInvariant(root)
    }
}