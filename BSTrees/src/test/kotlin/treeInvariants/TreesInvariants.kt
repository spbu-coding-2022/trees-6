package treeInvariants

import Node
import redBlackTree.RBNode
import redBlackTree.RBNode.Color

@Suppress("UNCHECKED_CAST")
class TreesInvariants<K : Comparable<K>, V, NODE_TYPE : Node<K, V, NODE_TYPE>> {

    fun checkRBTreeInvariants(root: RBNode<K, V>?): Boolean {
        return root == null || root.getParent() == null && checkNodeInvariants(root as NODE_TYPE) && root.color == Color.BLACK  &&
                checkRBNodeBlackHeightInvariant(root, 0, getSomeBlackHeight(root)) &&
                checkRBNodeBlackParentForRedInvariant(root)
    }

    private fun checkRBNodeBlackParentForRedInvariant(node: RBNode<K, V>): Boolean {
        return (node.color == Color.BLACK || node.getParent()!!.color == Color.BLACK) &&
                (node.getLeftNode() == null || checkRBNodeBlackParentForRedInvariant(node.getLeftNode()!!)) &&
                (node.getRightNode() == null || checkRBNodeBlackParentForRedInvariant(node.getRightNode()!!))
    }

    private fun checkRBNodeBlackHeightInvariant(node: RBNode<K, V>, curHeight: Int, rightHeight: Int): Boolean{
        val newCurHeight = curHeight + if(node.color == Color.BLACK) 1 else 0
        if(node.getLeftNode() == null && node.getRightNode() == null){
            return newCurHeight == rightHeight
        }
        return (node.getLeftNode() == null || checkRBNodeBlackHeightInvariant(node.getLeftNode()!!, newCurHeight, rightHeight) &&
                node.getRightNode() == null || checkRBNodeBlackHeightInvariant(node.getRightNode()!!, newCurHeight, rightHeight))
    }

    private fun getSomeBlackHeight(root: RBNode<K, V>): Int{
        var curNode: RBNode<K, V>? = root
        var blackHeight = 0
        while(curNode != null){
            if(curNode.color == Color.BLACK){
                ++blackHeight
            }
            curNode = curNode.getLeftNode()
        }
        return blackHeight
    }

    private fun checkNodeInvariants(node: NODE_TYPE): Boolean {
        return (node.getLeftNode() == null || node.getLeftNode()!!.getParent() == node &&
                node.getLeftNode()!!.getKey() < node.getKey() && checkNodeInvariants(node.getLeftNode()!!)) &&
                (node.getRightNode() == null || node.getRightNode()!!.getParent() == node &&
                        node.getRightNode()!!.getKey() > node.getKey() && checkNodeInvariants(node.getRightNode()!!))
    }
}