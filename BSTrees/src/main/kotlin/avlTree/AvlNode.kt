package avlTree

import Node
import kotlin.math.max

class AvlNode<K : Comparable<K>, V>(key: K, value: V) : Node<K, V, AvlNode<K, V>>(key, value) {

    private var height = 1
    fun getHeight() = this.height

    fun setHeight(newValue: Int) {
        this.height = newValue
    }

    fun updateHeight() {
        val leftHeight = this.getLeftNode()?.getHeight() ?: 0
        val rightHeight = this.getRightNode()?.getHeight() ?: 0
        setHeight(max(leftHeight, rightHeight) + 1)
    }
}
