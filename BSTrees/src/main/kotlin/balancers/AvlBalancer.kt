package balancers

import avlTree.AvlNode
import avlTree.AvlTree


open class AvlBalancer<K : Comparable<K>, V>(tree: AvlTree<K, V>?) : Balancer<K, V, AvlNode<K, V>, AvlTree<K, V>>(tree) {

    override fun balance() {
        TODO("Not yet implemented")
    }
}
