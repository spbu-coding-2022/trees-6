package balancers

import avlTree.AvlNode


open class AvlBalancer<K : Comparable<K>, V> : Balancer<K, V, AvlNode<K, V>>() {

    override fun balance(node: AvlNode<K, V>?) {
        TODO("Not yet implemented")
    }
}
