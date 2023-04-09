package redBlackTree

import BTree
import balancers.RBBalancer
import redBlackTree.RBNode.Color

class RBTree<K : Comparable<K>, V> : BTree<K, V, RBNode<K, V>>() {

    private val balancer = RBBalancer<K, V>()

    override fun insert(key: K, value: V) {
        add(RBNode(key, value))
    }

    override fun add(node: RBNode<K, V>) {
        var root = this.root
        if (root == null) {
            this.root = node
            return
        }
        while (true) {
            root ?: error("adding error")
            if (root.getKey() > node.getKey()) {
                val leftNode = root.getLeftNode()
                if (leftNode == null) {
                    node.color = Color.RED
                    root.setLeftNode(node)
                    node.setParent(root)
                    this.root = balancer.balance(node)
                    break
                } else {
                    root = leftNode
                }
            } else {
                val rightNode = root.getRightNode()
                if (rightNode == null) {
                    node.color = Color.RED
                    root.setRightNode(node)
                    node.setParent(root)
                    this.root = balancer.balance(node)
                    break
                } else {
                    root = rightNode
                }
            }
        }
    }

    override fun delete(key: K) {
        TODO("Not yet implemented")
    }
}