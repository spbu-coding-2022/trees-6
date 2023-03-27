package balancers

import binarySearchTree.BSNode
import binarySearchTree.BSTree

class BSBalancer<K:Comparable<K>, V>(tree: BSTree<K, V>):Balancer<K, V, BSNode<K, V>, BSTree<K, V>>(tree){

    override fun balance() {}

}