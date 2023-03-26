package avlTree

import balancers.AvlBalancer
import BTree

class AvlTree<K: Comparable<K>, V>(root: AvlNode<K, V>): BTree<K, V, AvlNode<K, V>, AvlTree<K, V>>(root){

    private val balancer = AvlBalancer()

    override fun add(node: AvlNode<K, V>) {
        TODO("Not yet implemented")
    }

    override fun delete(key: K) {
        TODO("Not yet implemented")
    }
}
