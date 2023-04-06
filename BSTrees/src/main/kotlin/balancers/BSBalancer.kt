package balancers

import binarySearchTree.BSNode
import binarySearchTree.BSTree

open class BSBalancer<K : Comparable<K>, V>(tree: BSTree<K, V>?) : Balancer<K, V, BSNode<K, V>, BSTree<K, V>>(tree) {

    override fun balance() {
        TODO("Not yet implemented")
    }

}
