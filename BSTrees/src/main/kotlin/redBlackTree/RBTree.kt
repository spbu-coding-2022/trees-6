package redBlackTree

import BTree
import balancers.RBBalancer

class RBTree<K : Comparable<K>, V> : BTree<K, V, RBNode<K, V>>() {

    private val balancer = RBBalancer<K, V>()

    override fun insert(key: K, value: V) {
        add(RBNode(key, value))
    }

    override fun add(node: RBNode<K, V>) {
        TODO("Not yet implemented")
    }

    override fun delete(key: K) {
        TODO("Not yet implemented")
    }

}
