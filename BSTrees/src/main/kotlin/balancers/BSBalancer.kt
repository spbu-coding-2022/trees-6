package balancers

import binarySearchTree.BSNode

class BSBalancer<K : Comparable<K>, V>: Balancer<K, V, BSNode<K, V>>() {

    override fun balance(node: BSNode<K, V>): BSNode<K, V> {
        //We don't need balance function in BSTree, but we need rotates
        TODO("Nothing")
    }

}
