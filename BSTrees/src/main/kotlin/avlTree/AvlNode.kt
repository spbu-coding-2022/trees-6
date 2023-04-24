package avlTree

import Node
import kotlin.math.max

class AvlNode<K : Comparable<K>, V>(key: K, value: V) : Node<K, V, AvlNode<K, V>>(key, value) {

    internal var height = 1

    fun updateHeight() {
        val leftHeight = this.leftNode?.height ?: 0
        val rightHeight = this.rightNode?.height ?: 0
        height = (max(leftHeight, rightHeight) + 1)
    }
}
