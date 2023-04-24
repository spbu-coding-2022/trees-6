package binarySearchTree

import Balancer

internal class BSBalancer<K : Comparable<K>, V> : Balancer<K, V, BSNode<K, V>>() {

    internal fun bsRightRotate(node: BSNode<K, V>): BSNode<K, V> {
        val temp = rightRotate(node)
        temp.leftNode?.updateSize()
        temp.rightNode?.updateSize()
        temp.updateSize()
        return temp
    }

    internal fun bsLeftRotate(node: BSNode<K, V>): BSNode<K, V> {
        val temp = leftRotate(node)
        temp.leftNode?.updateSize()
        temp.rightNode?.updateSize()
        temp.updateSize()
        return temp
    }

}
