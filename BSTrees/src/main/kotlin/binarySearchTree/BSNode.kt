package binarySearchTree

import Node

class BSNode<K : Comparable<K>, V>(key: K, value: V) : Node<K, V, BSNode<K, V>>(key, value) {

    private var size = 1

    fun getSize() = size

    fun setSize(newSize: Int) {
        this.size = newSize
    }

    fun updateSize() {
        val leftSize = getLeftNode()?.getSize() ?: 0
        val rightSize = getRightNode()?.getSize() ?: 0
        setSize(leftSize + rightSize + 1)
    }
}
