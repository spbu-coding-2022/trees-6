package balancers

import redBlackTree.RBNode
import redBlackTree.RBNode.Color
import redBlackTree.RBTree

open class RBBalancer<K : Comparable<K>, V>: Balancer<K, V, RBNode<K, V>>(){

    fun balanceForDelete(tree: RBTree<K, V>, node: RBNode<K, V>){
        var curNode = node
        var nodeParent = node.getParent()
        while(nodeParent != null && curNode.color != Color.RED){
            if(nodeParent.getLeftNode() == curNode){

                if(nodeParent.getLeftNode() == null){
                    curNode = nodeParent
                    nodeParent = curNode.getParent()
                    continue
                }

                // brother MUST exist, because we have a 2xBLACK curNode in leftTree
                var nodeBrother = nodeParent.getRightNode()!!
                if(nodeBrother.color == Color.RED){
                    nodeBrother.color = Color.BLACK
                    nodeParent.color = Color.RED
                    if(nodeParent.getParent() == null){
                        nodeParent = leftRotate(nodeParent)
                        tree.root = nodeParent
                    }
                    else{
                        nodeParent = leftRotate(nodeParent)
                    }



                    // these nodes MUST exist because we did leftRotate with theirs nodes
                    nodeParent = nodeParent.getLeftNode()!!
                    curNode = nodeParent.getLeftNode()!!
                    nodeBrother = nodeParent.getRightNode()!!
                }

                // left and right sons of nodeBrother MUST exist, because of comment, which is located a little higher
                if((nodeBrother.getLeftNode() == null || nodeBrother.getLeftNode()!!.color == Color.BLACK) &&
                    (nodeBrother.getRightNode() == null || nodeBrother.getRightNode()!!.color == Color.BLACK)
                ){
                    nodeBrother.color = Color.RED
                    val flag = nodeParent.color == Color.RED
                    nodeParent.color = Color.BLACK
                    curNode = nodeParent
                    nodeParent = curNode.getParent()
                    if(flag)break
                }
                else{
                    if(nodeBrother.getLeftNode() != null && nodeBrother.getLeftNode()!!.color == Color.RED && (nodeBrother.getRightNode() == null || nodeBrother.getRightNode()!!.color == Color.BLACK)){
                        nodeBrother.color = Color.RED
                        nodeBrother.getLeftNode()!!.color = Color.BLACK
                        nodeBrother = rightRotate(nodeBrother)

                    }
                    nodeBrother.color = nodeParent.color
                    nodeParent.color = Color.BLACK
                    nodeBrother.getRightNode()!!.color = Color.BLACK
                    if(nodeParent.getParent() == null){
                        tree.root = leftRotate(nodeParent)
                    }
                    else {
                        leftRotate(nodeParent)
                    }
                    break
                }
            }
            else{

                if(nodeParent.getLeftNode() == null){
                    curNode = nodeParent
                    nodeParent = curNode.getParent()
                    continue
                }

                // brother MUST exist, because we have a 2xBLACK curNode in rightTree
                var nodeBrother = nodeParent.getLeftNode()!!
                if(nodeBrother.color == Color.RED){
                    nodeBrother.color = Color.BLACK
                    nodeParent.color = Color.RED

                    if(nodeParent.getParent() == null){
                        nodeParent = rightRotate(nodeParent)
                        tree.root = nodeParent
                    }
                    else{
                        nodeParent = rightRotate(nodeParent)
                    }

                    // these nodes MUST exist because we did rightRotate with theirs nodes
                    nodeParent = nodeParent.getRightNode()!!
                    curNode = nodeParent.getRightNode()!!
                    nodeBrother = nodeParent.getLeftNode()!!
                }

                // left and right sons of nodeBrother MUST exist, because of comment, which is located a little higher
                if((nodeBrother.getLeftNode() == null || nodeBrother.getLeftNode()!!.color == Color.BLACK) &&
                        (nodeBrother.getRightNode() == null || nodeBrother.getRightNode()!!.color == Color.BLACK)
                ){
                    nodeBrother.color = Color.RED
                    val flag = nodeParent.color == Color.RED
                    nodeParent.color = Color.BLACK
                    curNode = nodeParent
                    nodeParent = curNode.getParent()
                    if(flag)break
                }
                else{
                    if((nodeBrother.getLeftNode() == null || nodeBrother.getLeftNode()!!.color == Color.BLACK) && nodeBrother.getRightNode() != null && nodeBrother.getRightNode()!!.color == Color.RED){
                        nodeBrother.color = Color.RED
                        nodeBrother.getRightNode()!!.color = Color.BLACK
                        nodeBrother = leftRotate(nodeBrother)
                    }
                    nodeBrother.color = nodeParent.color
                    nodeParent.color = Color.BLACK
                    nodeBrother.getLeftNode()!!.color = Color.BLACK
                    if(nodeParent.getParent() == null) {
                        tree.root = rightRotate(nodeParent)
                    }
                    else{
                        rightRotate(nodeParent)
                    }
                    break
                }
            }
        }
        curNode.color = Color.BLACK
    }
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