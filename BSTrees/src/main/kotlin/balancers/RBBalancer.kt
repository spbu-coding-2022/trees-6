package balancers

import redBlackTree.RBNode
import redBlackTree.RBTree

open class RBBalancer<K : Comparable<K>, V>(tree: RBTree<K, V>?) : Balancer<K, V, RBNode<K, V>, RBTree<K, V>>(tree) {

    //update parent
    override fun balance() {
        TODO("Not yet implemented")
    }
}
