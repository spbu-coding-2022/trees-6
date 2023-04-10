package binarySearchTree

import BTree
import balancers.BSBalancer
import kotlin.random.Random

class BSTree<K : Comparable<K>, V> : BTree<K, V, BSNode<K, V>>() {

    val balancer = BSBalancer<K, V>()

    override fun insert(key: K, value: V) {
        add(BSNode(key, value))
    }

    private fun addRoot(root: BSNode<K, V>): BSNode<K, V> {

        val temp = this.root
        if (temp == null) return root
        else {
            val subTree = BSTree<K, V>()
            if (root.getKey() < temp.getKey()) {
                subTree.root = temp.getLeftNode()
                this.root?.setLeftNode(subTree.addRoot(root))
                return balancer.rightRotate(temp)
            } else {
                subTree.root = temp.getRightNode()
                this.root?.setRightNode(subTree.addRoot(root))
                return balancer.leftRotate(temp)
            }
        }

    }

    override fun add(node: BSNode<K, V>) {

        val temp = this.root
        if (temp == null || temp.getKey() == node.getKey()) this.root = root

        else {

            if (Random.nextInt() % (node.getHeight() + 1) == 0) this.root = this.addRoot(node)
            /*
            Randomized insertion into the root of the tree allows you
            to artificially balance it with a fairly small height.
            Otherwise, we perform the usual insertion
            */
            else {
                val subTree = BSTree<K, V>()
                if (temp.getKey() > node.getKey()) {
                    subTree.root = temp.getLeftNode()
                    subTree.add(node)
                    this.root?.setLeftNode(subTree.root)
                } else {
                    subTree.root = temp.getRightNode()
                    subTree.add(node)
                    this.root?.setRightNode(subTree.root)
                }
            }

        }
    }

    override fun delete(key: K) {
        TODO("Not yet implemented")
    }
}
