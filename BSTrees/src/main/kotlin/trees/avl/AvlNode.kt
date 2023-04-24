package trees.avl

import trees.Node
import kotlin.math.max

/**
 * A class representing an AVL binary search tree node.
 * The difference from the abstract node class is in the presence of a height and the function of updating it
 *
 * @generic <K> the type of key stored in the tree. It must be comparable
 * @generic <V> the type of value stored in the tree
 */
class AvlNode<K : Comparable<K>, V>(key: K, value: V) : Node<K, V, AvlNode<K, V>>(key, value) {

    /**
     * The variable that contains a height of the tree
     */
    internal var height = 1

    /**
     * Update a height of the tree to set the correct value
     */
    fun updateHeight() {
        val leftHeight = this.leftNode?.height ?: 0
        val rightHeight = this.rightNode?.height ?: 0
        height = (max(leftHeight, rightHeight) + 1)
    }
}
