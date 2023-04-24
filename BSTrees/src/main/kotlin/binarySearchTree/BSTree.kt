package binarySearchTree

import BTree
import kotlin.random.Random

class BSTree<K : Comparable<K>, V> : BTree<K, V, BSNode<K, V>>() {

    private val balancer = BSBalancer<K, V>()

    override fun insert(key: K, value: V) {
        add(BSNode(key, value))
    }

    private fun addRoot(node: BSNode<K, V>): BSNode<K, V> {

        val temp = this.root
        return if (temp == null) node
        else if (temp.key == node.key) {
            temp.value = node.value
            temp
        } else {
            val subTree = BSTree<K, V>()
            if (node.key < temp.key) {
                subTree.root = temp.leftNode
                this.root?.leftNode = subTree.addRoot(node)
                balancer.bsRightRotate(temp)
            } else {
                subTree.root = temp.rightNode
                this.root?.rightNode = subTree.addRoot(node)
                balancer.bsLeftRotate(temp)
            }
        }

    }

    private fun add(node: BSNode<K, V>) {

        val temp = this.root
        if (temp == null) this.root = node
        else if (temp.key == node.key) this.root?.value = node.value
        else {

            if (Random.nextInt() % (node.size + 1) == 0) this.root = this.addRoot(node)
            /*
            Randomized insertion into the root of the tree allows you
            to artificially balance it with a fairly small height.
            Otherwise, we perform the usual insertion
            */
            else {
                val subTree = BSTree<K, V>()
                if (temp.key > node.key) {
                    subTree.root = temp.leftNode
                    subTree.add(node)
                    subTree.root?.parent = this.root
                    this.root?.leftNode = subTree.root
                } else {
                    subTree.root = temp.rightNode
                    subTree.add(node)
                    subTree.root?.parent = this.root
                    this.root?.rightNode = subTree.root
                }
            }

            this.root?.updateSize()

        }
    }

    private fun join(left: BSNode<K, V>?, right: BSNode<K, V>?): BSNode<K, V>? {

        if (left == null) return right
        if (right == null) return left

        return if (Random.nextInt() % (left.size + right.size) < left.size) {
            left.rightNode = join(left.rightNode, right)
            left.rightNode?.parent = left
            left.updateSize()
            left
        } else {
            right.leftNode = join(left, right.leftNode)
            right.leftNode?.parent = right
            right.updateSize()
            right
        }

    }

    override fun delete(key: K) {

        val temp = this.root

        if (temp != null) {

            if (temp.key == key) this.root = join(temp.leftNode, temp.rightNode)
            else if (key < temp.key) {
                val subTree = BSTree<K, V>()
                subTree.root = temp.leftNode
                subTree.delete(key)
                subTree.root?.parent = this.root
                this.root?.leftNode = subTree.root
            } else {
                val subTree = BSTree<K, V>()
                subTree.root = temp.rightNode
                subTree.delete(key)
                subTree.root?.parent = this.root
                this.root?.rightNode = subTree.root
            }

            this.root?.updateSize()

        }

    }
}
