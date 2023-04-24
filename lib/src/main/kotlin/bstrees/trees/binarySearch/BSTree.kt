package bstrees.trees.binarySearch

import bstrees.trees.BTree
import kotlin.random.Random

/**
 * A class representing a randomized binary search tree.
 *
 *
 * @generic <K> the type of key stored in the tree. It must be comparable
 * @generic <V> the type of value stored in the tree
 */
class BSTree<K : Comparable<K>, V> : BTree<K, V, BSNode<K, V>>() {

    /**
     * A balancer class providing rotations of the tree with updating the size
     */
    private val balancer = BSBalancer<K, V>()

    /**
     * Insert a node to the tree.
     * It is actually a wrapper for the add function. Necessary to implement recursion
     *
     * @param value the value to add
     * @param key the key under which the value is stored
     */
    override fun insert(key: K, value: V) {
        add(BSNode(key, value))
    }

    /**
     * Add a node to the root of the tree.
     * It's necessary to implement random adding.
     *
     * @param node the node to add
     */
    private fun addRoot(node: BSNode<K, V>): BSNode<K, V> {
        //temp is needed to avoid possibility of changing the root
        val temp = this.root
        return if (temp == null) node
        else if (temp.key == node.key) {
            temp.value = node.value
            temp
        } else {
            /**
             * Some kind of recursive implementation
             * Creating subtree to execute add function again
             * Then we return the node to the desired son
             */
            val subTree = BSTree<K, V>()
            //We make the usual adding, then we put the node in the root by rotation it tom the top
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

    /**
     * Add a node to the tree
     *
     * @param node the node to add
     */
    private fun add(node: BSNode<K, V>) {
        //temp is needed to avoid possibility of changing the root
        val temp = this.root
        if (temp == null) this.root = node
        else if (temp.key == node.key) this.root?.value = node.value
        else {

            if (Random.nextInt() % (node.size + 1) == 0) this.root = this.addRoot(node)
            /*
            Randomized insertion into the root of the tree allows you
            to artificially balance it with a fairly small height.
            The idea is that inserting into the root shuffles the tree well, which provides a relatively small height.
            The tree is not perfectly balanced, but differs from the logarithm on average by no more than twice,
            which is a constant

            Otherwise, we perform the usual insertion
            */
            else {
                /**
                 * Some kind of recursive implementation
                 * Creating subtree to execute add function again
                 * Then we return the node to the desired son
                 */
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

            //Updating this node before leaving recursion
            this.root?.updateSize()

        }
    }

    /**
     * Merge two nodes into one.
     * One of the ways to implement removal from the tree
     *
     * @param left
     * @param right
     * The nodes to merge
     */
    private fun join(left: BSNode<K, V>?, right: BSNode<K, V>?): BSNode<K, V>? {

        //If one of the nodes is null then we can just return the second one
        if (left == null) return right
        if (right == null) return left

        //Choosing the node that would be a new root
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

    /**
     * Delete a value from the tree.
     *
     * @param key the key under which the value is stored
     */
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

            //Updating this node before leaving recursion
            this.root?.updateSize()

        }

    }
}
