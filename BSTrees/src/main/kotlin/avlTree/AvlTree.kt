package avlTree

import BTree
import balancers.AvlBalancer

class AvlTree<K: Comparable<K>, V>: BTree<K, V, AvlNode<K, V>>() {

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
            }
            if (node.getKey() > temp.getKey()) {
                subTree.root = temp.getRightNode()
                subTree.add(node)
                this.root?.setRightNode(subTree.root)
            }
        }

        this.root = this.root?.let { balancer.balance(it) }
    }

    override fun delete(key: K) {
        TODO("Not yet implemented")
    }
}
