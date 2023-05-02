package binarySearchTree

import bstrees.model.trees.BSNode
import bstrees.model.trees.randomBinarySearch.RandomBSNode
import utils.TreesInvariants

@Suppress("UNCHECKED_CAST")
class BSTreeInvariants<K : Comparable<K>, V, NODE_TYPE : BSNode<K, V, NODE_TYPE>> : TreesInvariants<K, V, NODE_TYPE>() {
    private fun checkBSSizeInvariant(node: RandomBSNode<K, V>?): Boolean {
        return node == null ||
                node.size == (node.leftNode?.size ?: 0) + (node.rightNode?.size ?: 0) + 1
                && checkBSSizeInvariant(node.leftNode) && checkBSSizeInvariant(node.rightNode)
    }

    fun checkBsTreeInvariants(root: RandomBSNode<K, V>?): Boolean {
        return (root == null || checkNodeInvariants(root as NODE_TYPE)) && checkBSSizeInvariant(root)
    }
}