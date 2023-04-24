package redBlackTree

import BTree
import redBlackTree.RBNode.Color

class RBTree<K : Comparable<K>, V> : BTree<K, V, RBNode<K, V>>() {

    private val balancer = RBBalancer<K, V>()

    override fun insert(key: K, value: V) {
        add(RBNode(key, value))
    }

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
                getSonNodeForSwapping(nodeForSwapping)
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

    // nodeForSwapping is the node with the next largest key or the node itself if it has a Nil son
    private fun <K : Comparable<K>, V> getNodeForSwapping(curNode: RBNode<K, V>): RBNode<K, V> {
        return if (curNode.leftNode == null || curNode.rightNode == null) {
            curNode
        } else {
            // If we got here, then curNode has both a left and a right son
            val temp = curNode.rightNode ?: throw Exception("An attempt to take a non-existent son")
            nodeWithMinKey(temp)
        }
    }

    private fun <K : Comparable<K>, V> getSonNodeForSwapping(nodeForSwapping: RBNode<K, V>): RBNode<K, V>? {
        return if (nodeForSwapping.leftNode != null) {
            nodeForSwapping.leftNode
        } else {
            nodeForSwapping.rightNode
        }
    }

    // We get an imaginary(Nil) node filled with some unnecessary data
    private fun <K : Comparable<K>, V> getNilNode(nodeExample: RBNode<K, V>): RBNode<K, V> {
        val nilSonNodeOfSwapping = RBNode(nodeExample.key, nodeExample.value)
        nilSonNodeOfSwapping.color = Color.BLACK

        return nilSonNodeOfSwapping
    }

    // We are looking for a node with the minimum key available from this node
    private fun <K : Comparable<K>, V> nodeWithMinKey(node: RBNode<K, V>): RBNode<K, V> {
        var curNode = node
        while (curNode.leftNode != null) {
            curNode = curNode.leftNode ?: throw Exception("An attempt to take a non-existent son")
        }
        return curNode
    }
}