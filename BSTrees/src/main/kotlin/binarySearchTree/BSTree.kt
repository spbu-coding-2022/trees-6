package binarySearchTree

import BTree
import balancers.BSBalancer
import kotlin.random.Random

class BSTree<K : Comparable<K>, V> : BTree<K, V, BSNode<K, V>>() {

    private val balancer = BSBalancer<K, V>()

    override fun insert(key: K, value: V) {
        add(BSNode(key, value))
    }

    private fun addRoot(root: BSNode<K, V>): BSNode<K, V> {

        val temp = this.root
        return if (temp == null) root
        else {
            val subTree = BSTree<K, V>()
            if (root.getKey() < temp.getKey()) {
                subTree.root = temp.getLeftNode()
                this.root?.setLeftNode(subTree.addRoot(root))
                balancer.rightRotate(temp)
            } else {
                subTree.root = temp.getRightNode()
                this.root?.setRightNode(subTree.addRoot(root))
                balancer.leftRotate(temp)
            }
        }

    }

    override fun add(node: BSNode<K, V>) {

        val temp = this.root
        if (temp == null || temp.getKey() == node.getKey()) this.root = root
        else {

            if (Random.nextInt() % (node.getSize() + 1) == 0) this.root = this.addRoot(node)
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

            this.root?.updateSize()

        }
    }

    private fun join(left: BSNode<K, V>?, right: BSNode<K, V>?): BSNode<K, V>? {

        if (left == null) return right
        if (right == null) return left

        return if (Random.nextInt() % (left.getSize() + right.getSize()) < left.getSize()) {
            left.setRightNode(join(left.getRightNode(), right))
            left.updateSize()
            left
        } else {
            right.setLeftNode(join(right.getLeftNode(), left))
            right.updateSize()
            right
        }

    }

    override fun delete(key: K) {

        val temp = this.root

        if (temp != null) {

            if (temp.getKey() == key) this.root = join(temp.getLeftNode(), temp.getRightNode())
            else if (key < temp.getKey()) {
                val subTree = BSTree<K, V>()
                subTree.root = temp.getLeftNode()
                subTree.delete(key)
                this.root?.setLeftNode(subTree.root)
            } else {
                val subTree = BSTree<K, V>()
                subTree.root = temp.getRightNode()
                subTree.delete(key)
                this.root?.setRightNode(subTree.root)
            }

        }

    }
}
