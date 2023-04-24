package binarySearchTree

import Node

class BSNode<K : Comparable<K>, V>(key: K, value: V) : Node<K, V, BSNode<K, V>>(key, value) {

    internal var size = 1

    fun updateSize() {
        val leftSize = leftNode?.size ?: 0
        val rightSize = rightNode?.size ?: 0
        size = leftSize + rightSize + 1
    }
}
