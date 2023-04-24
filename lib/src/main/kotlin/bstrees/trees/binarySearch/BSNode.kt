package bstrees.trees.binarySearch

import bstrees.trees.Node

/**
 * A class representing a randomized binary search tree node.
 * The difference from the abstract node class is in the presence of size and the function of updating it
 *
 * @generic <K> the type of key stored in the tree. It must be comparable
 * @generic <V> the type of value stored in the tree
 */
class BSNode<K : Comparable<K>, V>(key: K, value: V) : Node<K, V, BSNode<K, V>>(key, value) {

    /**
     * The variable that contains size of the tree
     * It isn't a height.
     * This variable contains the height, which is measured in the number of nodes in the tree including the root
     */
    internal var size = 1

    /**
     * Update size of the tree to set the correct value
     */
    fun updateSize() {
        val leftSize = leftNode?.size ?: 0
        val rightSize = rightNode?.size ?: 0
        size = leftSize + rightSize + 1
    }
}
