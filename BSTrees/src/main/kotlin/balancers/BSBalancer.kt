package balancers

import binarySearchTree.BSNode

class BSBalancer<K : Comparable<K>, V> : Balancer<K, V, BSNode<K, V>>() {

    fun bsRightRotate(node: BSNode<K, V>): BSNode<K, V> {
        val temp = rightRotate(node)
        temp.getLeftNode()?.updateSize()
        temp.getRightNode()?.updateSize()
        temp.updateSize()
        return temp
    }

    fun bsLeftRotate(node: BSNode<K, V>): BSNode<K, V> {
        val temp = leftRotate(node)
        temp.getLeftNode()?.updateSize()
        temp.getRightNode()?.updateSize()
        temp.updateSize()
        return temp
    }

    override fun balance(node: BSNode<K, V>): BSNode<K, V> {
        //We don't need balance function in BSTree, but we need rotates
        TODO("Nothing")
    }

}
