package balancers

import binarySearchTree.BSNode

open class BSBalancer<K : Comparable<K>, V>: Balancer<K, V, BSNode<K, V>>() {

    override fun balance(node: BSNode<K, V>): BSNode<K, V> {
        TODO("Not yet implemented")
    }

}
