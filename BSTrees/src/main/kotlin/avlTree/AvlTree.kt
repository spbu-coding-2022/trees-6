package avlTree

import BTree
import balancers.AvlBalancer

class AvlTree<K : Comparable<K>, V> : BTree<K, V, AvlNode<K, V>>() {

    private val balancer = AvlBalancer<K, V>()

    override fun insert(key: K, value: V) {
        add(AvlNode(key, value))
    }

    override fun add(node: AvlNode<K, V>) {
        val temp = this.root
        if (temp?.getKey() == node.getKey() || temp == null) this.root = node
        else {

            val subTree = AvlTree<K, V>()

            if (node.getKey() < temp.getKey()) {
                subTree.root = temp.getLeftNode()
                subTree.add(node)
                this.root?.setLeftNode(subTree.root)
            } else if (node.getKey() > temp.getKey()) {
                subTree.root = temp.getRightNode()
                subTree.add(node)
                this.root?.setRightNode(subTree.root)
            }

        }

        this.root = this.root?.let { balancer.balance(it) }
    }

    private fun findMin(node: AvlNode<K, V>): AvlNode<K, V> {
        val temp = node.getLeftNode()
        return if (temp != null) findMin(temp) else node
    }

    private fun removeMin(node: AvlNode<K, V>): AvlNode<K, V>? {
        val temp = node.getLeftNode()
        if (temp == null) return node.getRightNode()
        node.setLeftNode(removeMin(temp))
        return balancer.balance(node)
    }

    override fun delete(key: K) {

        val temp = this.root

        if (temp != null) {

            val subTree = AvlTree<K, V>()

            if (key < temp.getKey()) {

                subTree.root = temp.getLeftNode()
                subTree.delete(key)
                this.root?.setLeftNode(subTree.root)

            } else if (key > temp.getKey()) {

                subTree.root = temp.getRightNode()
                subTree.delete(key)
                this.root?.setRightNode(subTree.root)

            } else {

                val leftNode = this.root?.getLeftNode()
                val rightNode = this.root?.getRightNode()

                if (rightNode == null) this.root = leftNode

                else {
                    val tempMin: AvlNode<K, V> = findMin(rightNode)
                    tempMin.setRightNode(removeMin(rightNode))
                    tempMin.setLeftNode(leftNode)
                    this.root = balancer.balance(tempMin)
                }

            }

            val nullCheck = this.root
            if (nullCheck != null) this.root = balancer.balance(nullCheck)

        }
    }
}
