package avlTree

import Balancer


internal class AvlBalancer<K : Comparable<K>, V> : Balancer<K, V, AvlNode<K, V>>() {

    private fun balanceFactor(node: AvlNode<K, V>): Int {
        return (node.rightNode?.height ?: 0) - (node.leftNode?.height ?: 0)
    }

    private fun avlRightRotate(node: AvlNode<K, V>): AvlNode<K, V> {
        val temp = rightRotate(node)
        temp.leftNode?.updateHeight()
        temp.rightNode?.updateHeight()
        temp.updateHeight()
        return temp
    }

    private fun avlLeftRotate(node: AvlNode<K, V>): AvlNode<K, V> {
        val temp = leftRotate(node)
        temp.leftNode?.updateHeight()
        temp.rightNode?.updateHeight()
        temp.updateHeight()
        return temp
    }

    internal fun balance(node: AvlNode<K, V>): AvlNode<K, V> {

        node.updateHeight()
        val bf = balanceFactor(node)

        if (bf == 2) {

            val temp = node.rightNode
            if (temp != null) {
                if (balanceFactor(temp) < 0) {
                    node.rightNode = avlRightRotate(temp)
                }
            }

            return avlLeftRotate(node)
        }

        if (bf == -2) {

            val temp = node.leftNode
            if (temp != null) {
                if (balanceFactor(temp) > 0) {
                    node.leftNode = avlLeftRotate(temp)
                }
            }

            return avlRightRotate(node)
        }

        return node
    }

}
