package redBlackTree

import BTree
import balancers.RBBalancer

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
            root ?: error("IT IS IMPOSSIBLE!!!")
            if (root.getKey() > node.getKey()) {
                val leftNode = root.getLeftNode()
                if (leftNode == null) {
                    node.color = RBNode.Color.RED
                    root.setLeftNode(node)
                    root.getLeftNode()?.setParent(root)
                    balancer.balance(root)
                    break
                } else {
                    root = leftNode
                    val newTree = RBTree<K, V>()
                    newTree.root = root

                }
            } else {
                val rightNode = root.getRightNode()
                if (rightNode == null) {
                    node.color = RBNode.Color.RED
                    root.setRightNode(node)
                    root.getRightNode()?.setParent(root)
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
