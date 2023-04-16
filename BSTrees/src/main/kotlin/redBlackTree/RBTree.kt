package redBlackTree

import BTree
import balancers.RBBalancer
import redBlackTree.RBNode.Color

class RBTree<K : Comparable<K>, V> : BTree<K, V, RBNode<K, V>>() {

    private val balancer = RBBalancer<K, V>()

    override fun insert(key: K, value: V) {
        add(RBNode(key, value))
    }

    override fun add(node: RBNode<K, V>) {

        // if a node with such a key already exists, then we update Value
        val nodeWithEqualKey = findNodeByKey(node.getKey())
        if (nodeWithEqualKey != null){
            nodeWithEqualKey.setValue(node.getValue())
            return
        }

        var root = this.root
        if (root == null) {
            this.root = node
            return
        }
        while (true) {
            root ?: error("adding error")
            if (root.getKey() > node.getKey()) {
                val leftNode = root.getLeftNode()
                if (leftNode == null) {
                    node.color = Color.RED
                    root.setLeftNode(node)
                    node.setParent(root)
                    this.root = balancer.balanceAfterAdding(node)
                    break
                } else {
                    root = leftNode
                }
            } else {
                val rightNode = root.getRightNode()
                if (rightNode == null) {
                    node.color = Color.RED
                    root.setRightNode(node)
                    node.setParent(root)
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
            if (temp.getKey() == key) return temp
            temp = if (temp.getKey() > key) {
                temp.getLeftNode()
            } else {
                temp.getRightNode()
            }
        }
        return null
    }

    override fun delete(key: K) {
        // This is the node that needs to be deleted
        val curNode = findNodeByKey(key) ?: return

        // This is the node that we will put in place of curNode
        val nodeForSwapping = getNodeForSwapping(curNode)

        var sonIsNillNode = false

        // This is the node that will take the place of nodeForSwapping
        // If nodeForSwapping has no sons, then we create an imaginary(NILL) sonNode
        val sonNodeForSwapping =
            if (nodeForSwapping.getLeftNode() == null && nodeForSwapping.getRightNode() == null) {
                sonIsNillNode = true
                getNillNode(curNode)
            } else {
                getSonNodeForSwapping(nodeForSwapping)
            }

        // We put the sonOfNodeForSwapping in the place of nodeForSwapping and establish the necessary links
        setLinksWithSonOfNodeForSwapping(nodeForSwapping, sonNodeForSwapping, sonIsNillNode)

        val colorOfNodeForSwapping = nodeForSwapping.color

        // If nodeForSwapping != curNode, then we need to add the necessary links to nodeForSwapping to replace it.
        if (curNode != nodeForSwapping) {
            setLinksWithNodeForSwapping(curNode, nodeForSwapping)
            nodeForSwapping.color = curNode.color
        }

        // If the color of the deleted node is black, then we have to balance the tree
        if (colorOfNodeForSwapping == Color.BLACK) {
            balancer.balanceAfterDeletion(this, sonNodeForSwapping!!)
        }

        // If we used an imaginary(NILL) node, we have to remove unnecessary links
        if (sonIsNillNode) {
            if (sonNodeForSwapping?.getParent()?.getLeftNode() == sonNodeForSwapping) {
                sonNodeForSwapping?.getParent()?.setLeftNode(null)
            } else {
                sonNodeForSwapping?.getParent()?.setRightNode(null)
            }
        }
    }

    private fun setLinksWithNodeForSwapping(curNode: RBNode<K, V>, nodeForSwapping: RBNode<K, V>) {
        nodeForSwapping.setLeftNode(curNode.getLeftNode())
        nodeForSwapping.getLeftNode()?.setParent(nodeForSwapping)
        nodeForSwapping.setRightNode(curNode.getRightNode())
        nodeForSwapping.getRightNode()?.setParent(nodeForSwapping)

        val parentCurNode = curNode.getParent()
        if (parentCurNode == null) {
            nodeForSwapping.setParent(null)
            this.root = nodeForSwapping
        } else {
            nodeForSwapping.setParent(parentCurNode)
            if (curNode == parentCurNode.getLeftNode()) {
                parentCurNode.setLeftNode(nodeForSwapping)
            } else {
                parentCurNode.setRightNode(nodeForSwapping)
            }
        }
    }

    private fun setLinksWithSonOfNodeForSwapping(
        nodeForSwapping: RBNode<K, V>,
        sonOfNodeForSwapping: RBNode<K, V>?,
        sonIsNillNode: Boolean
    ) {
        sonOfNodeForSwapping?.setParent(nodeForSwapping.getParent())

        val parentNodeForSwapping = nodeForSwapping.getParent()
        if (parentNodeForSwapping == null) {
            sonOfNodeForSwapping?.setParent(null)
            this.root = if (!sonIsNillNode) sonOfNodeForSwapping else null
        } else {
            if (nodeForSwapping == parentNodeForSwapping.getLeftNode()) {
                parentNodeForSwapping.setLeftNode(sonOfNodeForSwapping)
            } else {
                parentNodeForSwapping.setRightNode(sonOfNodeForSwapping)
            }
        }
    }

    // nodeForSwapping is the node with the next largest key or the node itself if it has a NILL son
    private fun <K : Comparable<K>, V> getNodeForSwapping(curNode: RBNode<K, V>): RBNode<K, V> {
        return if (curNode.getLeftNode() == null || curNode.getRightNode() == null) {
            curNode
        } else {
            // If we got here, then curNode has both a left and a right son
            nodeWithMinKey(curNode.getRightNode()!!)
        }
    }

    private fun <K : Comparable<K>, V> getSonNodeForSwapping(nodeForSwapping: RBNode<K, V>): RBNode<K, V>? {
        return if (nodeForSwapping.getLeftNode() != null) {
            nodeForSwapping.getLeftNode()
        } else {
            nodeForSwapping.getRightNode()
        }
    }

    // We get an imaginary(NILL) node filled with some unnecessary data
    private fun <K : Comparable<K>, V> getNillNode(nodeExample: RBNode<K, V>): RBNode<K, V> {
        val nillSonNodeOfSwapping = RBNode(nodeExample.getKey(), nodeExample.getValue())
        nillSonNodeOfSwapping.color = Color.BLACK

        return nillSonNodeOfSwapping
    }

    // We are looking for a node with the minimum key available from this node
    private fun <K : Comparable<K>, V> nodeWithMinKey(node: RBNode<K, V>): RBNode<K, V> {
        var curNode = node
        while (curNode.getLeftNode() != null) {
            curNode = curNode.getLeftNode()!!
        }
        return curNode
    }
}