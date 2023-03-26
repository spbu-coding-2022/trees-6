package binarySearchTree

import BTree

class BSTree<K: Comparable<K>, V>(root: BSNode<K, V>): BTree<K, V, BSNode<K, V>, BSTree<K, V>>(root) {

    override fun add(node: BSNode<K, V>) {
        TODO("Not yet implemented")
    }

    override fun delete(key: K) {
        TODO("Not yet implemented")
    }

}
