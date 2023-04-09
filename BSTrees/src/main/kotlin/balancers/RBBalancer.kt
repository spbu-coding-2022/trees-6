package balancers

import redBlackTree.RBNode

open class RBBalancer<K : Comparable<K>, V>: Balancer<K, V, RBNode<K, V>>(){

    override fun balance(node: RBNode<K, V>): RBNode<K, V> {
        TODO("Not yet implemented")
    }
}
