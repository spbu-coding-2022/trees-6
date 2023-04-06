package avlTree

import balancers.AvlBalancer
import BTree

class AvlTree<K: Comparable<K>, V>: BTree<K, V, AvlNode<K, V>>() {

    private val balancer = AvlBalancer<K, V>()

    override fun insert(key: K, value: V) {
        TODO("Not yet implemented")
    }
    override fun add(node: AvlNode<K, V>) {
        TODO("Not yet implemented")
    }

    override fun delete(key: K) {
        TODO("Not yet implemented")
    }
}
