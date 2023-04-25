package bstrees.model.trees.redBlack

import bstrees.model.trees.BTree
import bstrees.model.trees.redBlack.RBNode.Color

/**
 * A class representing a red black binary search tree.
 * It maintains balance through a strict hierarchy of red and black vertices,
 * as well as some rules that they follow
 *
 * @generic <K> the type of key stored in the tree. It must be comparable
 * @generic <V> the type of value stored in the tree
 */
class RBTree<K : Comparable<K>, V> : BTree<K, V, RBNode<K, V>>() {
    /**
     * A balancer class providing balancing the tree when adding or deleting nodes
     */
    private val balancer = RBBalancer<K, V>()

    /**
     * Insert a node to the tree.
     * It is actually a wrapper for the add function.
     *
     * @param value the value to add
     * @param key the key under which the value is stored
     */
    override fun insert(key: K, value: V) {
        add(RBNode(key, value))
    }

    /**
     * Add a node to the root of the tree.
     *
     * @param node the node to add
     */
    private fun add(node: RBNode<K, V>) {

        // if a node with such a key already exists, then we update Value
        val nodeWithEqualKey = findNodeByKey(node.key)
        if (nodeWithEqualKey != null) {
            nodeWithEqualKey.value = node.value
            return
        }

        var root = this.root
        if (root == null) {
            this.root = node
            return
        }
        while (true) {
            root ?: error("adding error")
            if (root.key > node.key) {
                val leftNode = root.leftNode
                if (leftNode == null) {
                    node.color = Color.RED
                    root.leftNode = node
                    node.parent = root
                    this.root = balancer.balanceAfterAdding(node)
                    break
                } else {
                    root = leftNode
                }
            } else {
                val rightNode = root.rightNode
                if (rightNode == null) {
                    node.color = Color.RED
                    root.rightNode = node
                    node.parent = root
                    this.root = balancer.balanceAfterAdding(node)
                    break
                } else {
                    root = rightNode
                }
            }
        }
    }

    /**
     * Looking for a node in the tree by its key.
     *
     * @param key the key by which the search is performed
     */
    private fun findNodeByKey(key: K): RBNode<K, V>? {
        var temp: RBNode<K, V>? = root ?: return null
        while (temp != null) {
            if (temp.key == key) return temp
            temp = if (temp.key > key) {
                temp.leftNode
            } else {
                temp.rightNode
            }
        }
        return null
    }

    /**
     * Delete a value from the tree.
     *
     * @param key the key under which the value is stored
     */
    override fun delete(key: K) {
        // This is the node that needs to be deleted
        val curNode = findNodeByKey(key) ?: return

        // This is the node that we will put in place of curNode
        val nodeForSwapping = getNodeForSwapping(curNode)

        var sonIsNilNode = false

        // This is the node that will take the place of nodeForSwapping
        // If nodeForSwapping has no sons, then we create an imaginary(Nil) sonNode
        val sonNodeForSwapping =
            if (nodeForSwapping.leftNode == null && nodeForSwapping.rightNode == null) {
                sonIsNilNode = true
                getNilNode(curNode)
            } else {
                if (nodeForSwapping.leftNode != null) nodeForSwapping.leftNode else nodeForSwapping.rightNode
            }

        // We put the sonOfNodeForSwapping in the place of nodeForSwapping and establish the necessary links
        setLinksWithSonOfNodeForSwapping(nodeForSwapping, sonNodeForSwapping, sonIsNilNode)

        val colorOfNodeForSwapping = nodeForSwapping.color

        // If nodeForSwapping != curNode, then we need to add the necessary links to nodeForSwapping to replace it.
        if (curNode != nodeForSwapping) {
            setLinksWithNodeForSwapping(curNode, nodeForSwapping)
            nodeForSwapping.color = curNode.color
        }

        // If the color of the deleted node is black, then we have to balance the tree
        if (colorOfNodeForSwapping == Color.BLACK) {
            sonNodeForSwapping?.let { balancer.balanceAfterDeletion(this, sonNodeForSwapping) }
        }

        // If we used an imaginary(Nil) node, we have to remove unnecessary links
        if (sonIsNilNode) {
            if (sonNodeForSwapping?.parent?.leftNode == sonNodeForSwapping) {
                sonNodeForSwapping?.parent?.leftNode = null
            } else {
                sonNodeForSwapping?.parent?.rightNode = null
            }
        }
    }

    /**
     * TODO ??
     */
    private fun setLinksWithNodeForSwapping(curNode: RBNode<K, V>, nodeForSwapping: RBNode<K, V>) {
        nodeForSwapping.leftNode = curNode.leftNode
        nodeForSwapping.leftNode?.parent = nodeForSwapping
        nodeForSwapping.rightNode = curNode.rightNode
        nodeForSwapping.rightNode?.parent = nodeForSwapping

        val parentCurNode = curNode.parent
        if (parentCurNode == null) {
            nodeForSwapping.parent = null
            this.root = nodeForSwapping
        } else {
            nodeForSwapping.parent = parentCurNode
            if (curNode == parentCurNode.leftNode) {
                parentCurNode.leftNode = nodeForSwapping
            } else {
                parentCurNode.rightNode = nodeForSwapping
            }
        }
    }

    /**
     * TODO ??
     */
    private fun setLinksWithSonOfNodeForSwapping(
        nodeForSwapping: RBNode<K, V>,
        sonOfNodeForSwapping: RBNode<K, V>?,
        sonIsNilNode: Boolean
    ) {
        sonOfNodeForSwapping?.parent = nodeForSwapping.parent

        val parentNodeForSwapping = nodeForSwapping.parent
        if (parentNodeForSwapping == null) {
            sonOfNodeForSwapping?.parent = null
            this.root = if (!sonIsNilNode) sonOfNodeForSwapping else null
        } else {
            if (nodeForSwapping == parentNodeForSwapping.leftNode) {
                parentNodeForSwapping.leftNode = sonOfNodeForSwapping
            } else {
                parentNodeForSwapping.rightNode = sonOfNodeForSwapping
            }
        }
    }

    /**
     * Find the node with the next largest key or the node itself if it has a Nil son
     * It is an auxiliary function for the delete function
     *
     * @param curNode the node that we are swapping
     */
    private fun <K : Comparable<K>, V> getNodeForSwapping(curNode: RBNode<K, V>): RBNode<K, V> {
        return if (curNode.leftNode == null || curNode.rightNode == null) {
            curNode
        } else {
            // If we got here, then curNode has both a left and a right son
            val temp = requireNotNull(curNode.rightNode) { "An attempt to take a non-existent son" }
            nodeWithMinKey(temp)
        }
    }

    /**
     * Create an imaginary(Nil) node
     * It's filled with some unnecessary data
     *
     * @param nodeExample some kind of pattern for Nil son
     */
    private fun <K : Comparable<K>, V> getNilNode(nodeExample: RBNode<K, V>): RBNode<K, V> {
        val nilSonNodeOfSwapping = RBNode(nodeExample.key, nodeExample.value)
        nilSonNodeOfSwapping.color = Color.BLACK

        return nilSonNodeOfSwapping
    }

    /**
     * Find a node with a minimum key in a tree
     * It is an auxiliary function for the delete function
     *
     * @param node the root of the tree where to find the minimum node
     */
    private fun <K : Comparable<K>, V> nodeWithMinKey(node: RBNode<K, V>): RBNode<K, V> {
        var curNode = node
        while (curNode.leftNode != null) {
            curNode = requireNotNull(curNode.leftNode) { "An attempt to take a non-existent son" }
        }
        return curNode
    }
}
