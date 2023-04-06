package redBlackTree

import BTree
import balancers.RBBalancer

class RBTree<K : Comparable<K>, V> : BTree<K, V, RBNode<K, V>>() {

    private val balancer = RBBalancer<K, V>()

    override fun insert(key: K, value: V) {
        add(RBNode(key, value))
    }

    override fun add(node: RBNode<K, V>) {
        var root = this.getRoot()
        if (root == null) {
            this.setRoot(node)
            return
        }
        while (true) {
            root ?: error("IT IS IMPOSSIBLE!!!")
            if (root.getKey() > node.getKey()) {
                val leftNode = root.getLeftNode()
                if (leftNode == null) {
                    node.setNodeColor(RBNode.Color.RED)
                    root.setLeftNode(node)
                    root.getLeftNode()?.setParent(root)
                    balancer.balance(root)
                    break
                } else {
                    root = leftNode
                    val newTree = RBTree<K, V>()
                    newTree.setRoot(root)

                }
            } else {
                val rightNode = root.getRightNode()
                if (rightNode == null) {
                    node.setNodeColor(RBNode.Color.RED)
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
