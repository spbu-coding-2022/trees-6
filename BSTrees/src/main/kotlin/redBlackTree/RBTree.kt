package redBlackTree

import BTree
import balancers.RBBalancer

class RBTree<K: Comparable<K>, V>(root: RBNode<K, V>): BTree<K, V, RBNode<K, V>, RBTree<K, V>>(root) {

    private val balancer = RBBalancer(this)

    override fun add(node: RBNode<K, V>) {
        TODO("Not yet implemented")
    }

    override fun delete(key: K) {
        TODO("Not yet implemented")
    }

}
