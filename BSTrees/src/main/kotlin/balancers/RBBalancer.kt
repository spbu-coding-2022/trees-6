package balancers

import redBlackTree.RBNode
import redBlackTree.RBNode.Color
import redBlackTree.RBTree

open class RBBalancer<K : Comparable<K>, V> : Balancer<K, V, RBNode<K, V>>() {

    fun balanceAfterDeletion(tree: RBTree<K, V>, node: RBNode<K, V>) {
        var curNode = node
        var nodeParent = node.getParent()
        while (nodeParent != null && curNode.color != Color.RED) {

            if (nodeParent.getLeftNode() == curNode) {

                if (nodeParent.getLeftNode() == null) {
                    curNode = nodeParent
                    nodeParent = curNode.getParent()
                    continue
                }

                // brother MUST exist, because we have a 2xBLACK curNode in leftTree
                var nodeBrother = nodeParent.getRightNode() ?: throw Exception("An attempt to take a non-existent son")

                // Consideration of the case 1.
                // nodeBrother is red
                if (nodeBrother.color == Color.RED) {
                    nodeBrother.color = Color.BLACK
                    nodeParent.color = Color.RED
                    if (nodeParent.getParent() == null) {
                        nodeParent = leftRotate(nodeParent)
                        tree.root = nodeParent
                    } else {
                        nodeParent = leftRotate(nodeParent)
                    }


                    // these nodes MUST exist because we did leftRotate with theirs nodes
                    nodeParent = nodeParent.getLeftNode() ?: throw Exception("An attempt to take a non-existent son")
                    curNode = nodeParent.getLeftNode() ?: throw Exception("An attempt to take a non-existent son")
                    nodeBrother = nodeParent.getRightNode() ?: throw Exception("An attempt to take a non-existent son")
                }

                // Consideration of the case 2.
                // nodeBrother's sons are black
                val leftSonOfBrother = nodeBrother.getLeftNode()
                val rightSonOfBrother = nodeBrother.getRightNode()
                if ((leftSonOfBrother == null || leftSonOfBrother.color == Color.BLACK) &&
                    (rightSonOfBrother == null || rightSonOfBrother.color == Color.BLACK)
                ) {
                    nodeBrother.color = Color.RED
                    curNode = nodeParent
                    nodeParent = curNode.getParent()
                } else {

                    // Consideration of the case 3.
                    // nodeBrother's left son is red, right son is black
                    if (leftSonOfBrother != null && leftSonOfBrother.color == Color.RED && (rightSonOfBrother == null || rightSonOfBrother.color == Color.BLACK)) {
                        nodeBrother.color = Color.RED
                        leftSonOfBrother.color = Color.BLACK
                        nodeBrother = rightRotate(nodeBrother)
                    }

                    // Consideration of the case 4.
                    // nodeBrother's right son is red
                    nodeBrother.color = nodeParent.color
                    nodeParent.color = Color.BLACK
                    nodeBrother.getRightNode()?.color = Color.BLACK
                    if (nodeParent.getParent() == null) {
                        tree.root = leftRotate(nodeParent)
                    } else {
                        leftRotate(nodeParent)
                    }
                    break
                }
            } else {

                if (nodeParent.getLeftNode() == null) {
                    curNode = nodeParent
                    nodeParent = curNode.getParent()
                    continue
                }

                // brother MUST exist, because we have a 2xBLACK curNode in rightTree
                var nodeBrother = nodeParent.getLeftNode() ?: throw Exception("An attempt to take a non-existent son")

                // Consideration of the case 1.
                // nodeBrother is red
                if (nodeBrother.color == Color.RED) {
                    nodeBrother.color = Color.BLACK
                    nodeParent.color = Color.RED

                    if (nodeParent.getParent() == null) {
                        nodeParent = rightRotate(nodeParent)
                        tree.root = nodeParent
                    } else {
                        nodeParent = rightRotate(nodeParent)
                    }

                    // these nodes MUST exist because we did rightRotate with theirs nodes
                    nodeParent = nodeParent.getRightNode() ?: throw Exception("An attempt to take a non-existent son")
                    curNode = nodeParent.getRightNode() ?: throw Exception("An attempt to take a non-existent son")
                    nodeBrother = nodeParent.getLeftNode() ?: throw Exception("An attempt to take a non-existent son")
                }

                // Consideration of the case 2.
                // nodeBrother's sons are black
                val leftSonOfBrother = nodeBrother.getLeftNode()
                val rightSonOfBrother = nodeBrother.getRightNode()
                if ((leftSonOfBrother == null || leftSonOfBrother.color == Color.BLACK) &&
                    (rightSonOfBrother == null || rightSonOfBrother.color == Color.BLACK)
                ) {
                    nodeBrother.color = Color.RED
                    curNode = nodeParent
                    nodeParent = curNode.getParent()
                } else {

                    // Consideration of the case 3.
                    // nodeBrother's left son is black, right son is red
                    if ((leftSonOfBrother == null || leftSonOfBrother.color == Color.BLACK) && rightSonOfBrother != null && rightSonOfBrother.color == Color.RED) {
                        nodeBrother.color = Color.RED
                        rightSonOfBrother.color = Color.BLACK
                        nodeBrother = leftRotate(nodeBrother)
                    }

                    // Consideration of the case 4.
                    // nodeBrother's right son is red
                    nodeBrother.color = nodeParent.color
                    nodeParent.color = Color.BLACK
                    nodeBrother.getLeftNode()?.color = Color.BLACK
                    if (nodeParent.getParent() == null) {
                        tree.root = rightRotate(nodeParent)
                    } else {
                        rightRotate(nodeParent)
                    }
                    break
                }
            }
        }
        curNode.color = Color.BLACK
    }


    fun balanceAfterAdding(node: RBNode<K, V>): RBNode<K, V> {
        var nodeParent = node.getParent()
        var curNode = node
        while (nodeParent != null && nodeParent.color == Color.RED) {
            // It must exist, since the root of the tree cannot be red
            val nodeGrandParent = nodeParent.getParent() ?: throw Exception("An attempt to take a non-existent parent")

            if (nodeParent == nodeGrandParent.getLeftNode()) {
                val nodeUncle = nodeGrandParent.getRightNode()

                if (nodeUncle == null || nodeUncle.color == Color.BLACK) {
                    if (curNode == nodeParent.getRightNode()) {
                        nodeParent = leftRotate(nodeParent)
                        curNode = nodeParent.getLeftNode() ?: throw Exception("An attempt to take a non-existent son")
                    }
                    nodeParent.color = Color.BLACK
                    nodeGrandParent.color = Color.RED
                    rightRotate(nodeGrandParent)
                } else {
                    nodeParent.color = Color.BLACK
                    nodeUncle.color = Color.BLACK
                    nodeGrandParent.color = Color.RED

                    curNode = nodeGrandParent
                    nodeParent = curNode.getParent()
                }
            } else {
                val nodeUncle = nodeGrandParent.getLeftNode()

                if (nodeUncle == null || nodeUncle.color == Color.BLACK) {
                    if (curNode == nodeParent.getLeftNode()) {
                        nodeParent = rightRotate(nodeParent)
                        curNode = nodeParent.getRightNode() ?: throw Exception("An attempt to take a non-existent son")
                    }
                    nodeParent.color = Color.BLACK
                    nodeGrandParent.color = Color.RED
                    leftRotate(nodeGrandParent)
                } else {
                    nodeParent.color = Color.BLACK
                    nodeUncle.color = Color.BLACK
                    nodeGrandParent.color = Color.RED

                    curNode = nodeGrandParent
                    nodeParent = curNode.getParent()
                }
            }
        }
        while (curNode.getParent() != null) {
            curNode = curNode.getParent() ?: throw Exception("An attempt to take a non-existent parent")
        }

        curNode.color = Color.BLACK
        return curNode
    }

    override fun balance(node: RBNode<K, V>): RBNode<K, V> {
        TODO("Not yet implemented")
    }
}