package balancers

import redBlackTree.RBNode
import redBlackTree.RBNode.Color

open class RBBalancer<K : Comparable<K>, V>: Balancer<K, V, RBNode<K, V>>(){

    override fun balance(node: RBNode<K, V>): RBNode<K, V> {
        var nodeParent = node.getParent()
        var curNode = node
        while (nodeParent != null && nodeParent.color == Color.RED){
            // It must exist, since the root of the tree cannot be red
            val nodeGrandParent = nodeParent.getParent() ?: error("RTTree structure error")

            if(nodeParent == nodeGrandParent.getLeftNode()){
                val nodeUncle = nodeGrandParent.getRightNode()

                if(nodeUncle == null || nodeUncle.color == Color.BLACK){
                    if(curNode == nodeParent.getRightNode()) {
                        nodeParent = leftRotate(nodeParent)
                        curNode = nodeParent.getLeftNode()!!
                    }
                    nodeParent.color = Color.BLACK
                    nodeGrandParent.color = Color.RED
                    rightRotate(nodeGrandParent)
                }
                else{
                    nodeParent.color = Color.BLACK
                    nodeUncle.color = Color.BLACK
                    nodeGrandParent.color = Color.RED

                    curNode = nodeGrandParent
                    nodeParent = curNode.getParent()
                }
            }
            else{
                val nodeUncle = nodeGrandParent.getLeftNode()

                if(nodeUncle == null || nodeUncle.color == Color.BLACK){
                    if(curNode == nodeParent.getLeftNode()) {
                        nodeParent = rightRotate(nodeParent)
                        curNode = nodeParent.getRightNode()!!
                    }
                    nodeParent.color = Color.BLACK
                    nodeGrandParent.color = Color.RED
                    leftRotate(nodeGrandParent)
                }
                else{
                    nodeParent.color = Color.BLACK
                    nodeUncle.color = Color.BLACK
                    nodeGrandParent.color = Color.RED

                    curNode = nodeGrandParent
                    nodeParent = curNode.getParent()
                }
            }
        }
        while(curNode.getParent() != null){
            curNode = curNode.getParent()!!
        }

        curNode.color = Color.BLACK
        return curNode
    }
}