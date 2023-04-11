package redBlackTree

import BTree
import balancers.RBBalancer
import redBlackTree.RBNode.Color

class RBTree<K : Comparable<K>, V> : BTree<K, V, RBNode<K, V>>() {

    private val balancer = RBBalancer<K, V>()

    private fun findNodeByKey(key: K): RBNode<K, V>?{
        var temp: RBNode<K, V>? = root ?: return null
        while(temp != null){
            if(temp.getKey() == key)return temp
            temp = if(temp.getKey() > key){
                temp.getLeftNode()
            } else{
                temp.getRightNode()
            }
        }
        return null
    }

    override fun insert(key: K, value: V) {
        add(RBNode(key, value))
    }

    override fun add(node: RBNode<K, V>) {

        // if a node with such a key already exists, then we do nothing
        if(findNodeByKey(node.getKey()) != null)return

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
                    this.root = balancer.balance(node)
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
                    this.root = balancer.balance(node)
                    break
                } else {
                    root = rightNode
                }
            }
        }
    }

    private fun treeMinimum(node: RBNode<K, V>): RBNode<K, V>{
        var curNode = node
        while(curNode.getLeftNode() != null){
            curNode = curNode.getLeftNode()!!
        }
        return curNode
    }

    override fun delete(key: K){
        val curNode = findNodeByKey(key) ?: return

        val nodeForSwapping = if(curNode.getLeftNode() == null || curNode.getRightNode() == null){
            curNode
        }
        else{
            // If we got here, then curNode has both a left and a right son
            treeMinimum(curNode.getRightNode()!!)
        }

        if(nodeForSwapping == curNode.getRightNode() && nodeForSwapping.getRightNode() == null){
            val virtualSonNodeOfSwapping = RBNode(curNode.getKey(), curNode.getValue())
            virtualSonNodeOfSwapping.color = Color.BLACK

            nodeForSwapping.setLeftNode(curNode.getLeftNode())
            curNode.getLeftNode()?.setParent(nodeForSwapping)
            nodeForSwapping.setRightNode(virtualSonNodeOfSwapping)
            virtualSonNodeOfSwapping.setParent(nodeForSwapping)

            val nodeForSwappingOldColor = nodeForSwapping.color

            nodeForSwapping.color = curNode.color

            val curNodeParent = curNode.getParent()
            if(curNodeParent == null){
                nodeForSwapping.setParent(null)
                this.root = nodeForSwapping
            }
            else{
                nodeForSwapping.setParent(curNode.getParent())

                if(curNodeParent.getLeftNode() == curNode){
                    curNodeParent.setLeftNode(nodeForSwapping)
                }
                else{
                    curNodeParent.setRightNode(nodeForSwapping)
                }
            }

            if(nodeForSwappingOldColor == Color.BLACK) {
                balancer.balanceForDelete(this, virtualSonNodeOfSwapping)
            }

            if(virtualSonNodeOfSwapping.getParent()?.getLeftNode() == virtualSonNodeOfSwapping){
                virtualSonNodeOfSwapping.getParent()?.setLeftNode(null)
            }
            else{
                virtualSonNodeOfSwapping.getParent()?.setRightNode(null)
            }
        }
        else {
            var isVirtualNode = false

            val sonOfNodeForSwapping = if(nodeForSwapping.getLeftNode() == null && nodeForSwapping.getRightNode() == null){
                isVirtualNode = true

                val virtualSonNodeOfSwapping = RBNode(curNode.getKey(), curNode.getValue())
                virtualSonNodeOfSwapping.color = Color.BLACK

                virtualSonNodeOfSwapping
            }
            else {
                if (nodeForSwapping.getLeftNode() != null) {
                    nodeForSwapping.getLeftNode()
                } else {
                    nodeForSwapping.getRightNode()
                }
            }

            // adding the old links to the new sonOfNodeForSwapping
            sonOfNodeForSwapping?.setParent(nodeForSwapping.getParent())

            val parentNodeForSwapping = nodeForSwapping.getParent()
            if (parentNodeForSwapping == null) {
                sonOfNodeForSwapping?.setParent(null)
                this.root = if(!isVirtualNode)sonOfNodeForSwapping else null
            } else {
                if (nodeForSwapping == parentNodeForSwapping.getLeftNode()) {
                    parentNodeForSwapping.setLeftNode(sonOfNodeForSwapping)
                } else {
                    parentNodeForSwapping.setRightNode(sonOfNodeForSwapping)
                }
            }

            val nodeForSwappingOldColor = nodeForSwapping.color

            // we replace nodeForSwapping with the one that we delete and transfer all links
            if (curNode != nodeForSwapping) {
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

                nodeForSwapping.color = curNode.color
            }

            if (nodeForSwappingOldColor == Color.BLACK) {
                balancer.balanceForDelete(this, sonOfNodeForSwapping!!)
            }

            if(isVirtualNode){
                if(sonOfNodeForSwapping?.getParent()?.getLeftNode() == sonOfNodeForSwapping){
                    sonOfNodeForSwapping?.getParent()?.setLeftNode(null)
                }
                else{
                    sonOfNodeForSwapping?.getParent()?.setRightNode(null)
                }
            }
        }
    }
}