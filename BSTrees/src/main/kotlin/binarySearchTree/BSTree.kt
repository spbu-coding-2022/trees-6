package binarySearchTree

import BTree
import balancers.BSBalancer
import kotlin.random.Random

class BSTree<K : Comparable<K>, V> : BTree<K, V, BSNode<K, V>>() {

    private val balancer = BSBalancer<K, V>()

    override fun insert(key: K, value: V) {
        add(BSNode(key, value))
    }

    private fun addRoot(node: BSNode<K, V>): BSNode<K, V> {

        val temp = this.root
        return if (temp == null) node
        else if (temp.getKey() == node.getKey()) {
            temp.setValue(node.getValue())
            temp
        } else {
            val subTree = BSTree<K, V>()
            if (node.getKey() < temp.getKey()) {
                subTree.root = temp.getLeftNode()
                this.root?.setLeftNode(subTree.addRoot(node))
                balancer.bsRightRotate(temp)
            } else {
                subTree.root = temp.getRightNode()
                this.root?.setRightNode(subTree.addRoot(node))
                balancer.bsLeftRotate(temp)
            }
        }

    }

    override fun add(node: BSNode<K, V>) {

        val temp = this.root
        if (temp == null) this.root = node
        else if (temp.getKey() == node.getKey()) this.root?.setValue(node.getValue())
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
                    subTree.root?.setParent(this.root)
                    this.root?.setLeftNode(subTree.root)
                } else {
                    subTree.root = temp.getRightNode()
                    subTree.add(node)
                    subTree.root?.setParent(this.root)
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
            left.getRightNode()?.setParent(left)
            left.updateSize()
            left
        } else {
            right.setLeftNode(join(left, right.getLeftNode()))
            right.getLeftNode()?.setParent(right)
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
                subTree.root?.setParent(this.root)
                this.root?.setLeftNode(subTree.root)
            } else {
                val subTree = BSTree<K, V>()
                subTree.root = temp.getRightNode()
                subTree.delete(key)
                subTree.root?.setParent(this.root)
                this.root?.setRightNode(subTree.root)
            }

            this.root?.updateSize()

        }

    }
}
