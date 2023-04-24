package redBlackTree

import Balancer
import redBlackTree.RBNode.Color

open class RBBalancer<K : Comparable<K>, V> : Balancer<K, V, RBNode<K, V>>() {

    internal fun balanceAfterDeletion(tree: RBTree<K, V>, node: RBNode<K, V>) {
        var curNode = node
        var nodeParent = node.parent
        while (nodeParent != null && curNode.color != Color.RED) {

            if (nodeParent.leftNode == curNode) {

                if (nodeParent.leftNode == null) {
                    curNode = nodeParent
                    nodeParent = curNode.parent
                    continue
                }

                // brother MUST exist, because we have a 2xBLACK curNode in leftTree
                var nodeBrother = nodeParent.rightNode ?: throw Exception("An attempt to take a non-existent son")

                // Consideration of the case 1.
                // nodeBrother is red
                if (nodeBrother.color == Color.RED) {
                    nodeBrother.color = Color.BLACK
                    nodeParent.color = Color.RED
                    if (nodeParent.parent == null) {
                        nodeParent = leftRotate(nodeParent)
                        tree.root = nodeParent
                    } else {
                        nodeParent = leftRotate(nodeParent)
                    }


                    // these nodes MUST exist because we did leftRotate with theirs nodes
                    nodeParent = nodeParent.leftNode ?: throw Exception("An attempt to take a non-existent son")
                    curNode = nodeParent.leftNode ?: throw Exception("An attempt to take a non-existent son")
                    nodeBrother = nodeParent.rightNode ?: throw Exception("An attempt to take a non-existent son")
                }

                // Consideration of the case 2.
                // nodeBrother's sons are black
                val leftSonOfBrother = nodeBrother.leftNode
                val rightSonOfBrother = nodeBrother.rightNode
                if ((leftSonOfBrother == null || leftSonOfBrother.color == Color.BLACK) &&
                    (rightSonOfBrother == null || rightSonOfBrother.color == Color.BLACK)
                ) {
                    nodeBrother.color = Color.RED
                    curNode = nodeParent
                    nodeParent = curNode.parent
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
                    nodeBrother.rightNode?.color = Color.BLACK
                    if (nodeParent.parent == null) {
                        tree.root = leftRotate(nodeParent)
                    } else {
                        leftRotate(nodeParent)
                    }
                    break
                }
            } else {

                if (nodeParent.leftNode == null) {
                    curNode = nodeParent
                    nodeParent = curNode.parent
                    continue
                }

                // brother MUST exist, because we have a 2xBLACK curNode in rightTree
                var nodeBrother = nodeParent.leftNode ?: throw Exception("An attempt to take a non-existent son")

                // Consideration of the case 1.
                // nodeBrother is red
                if (nodeBrother.color == Color.RED) {
                    nodeBrother.color = Color.BLACK
                    nodeParent.color = Color.RED

                    if (nodeParent.parent == null) {
                        nodeParent = rightRotate(nodeParent)
                        tree.root = nodeParent
                    } else {
                        nodeParent = rightRotate(nodeParent)
                    }

                    // these nodes MUST exist because we did rightRotate with theirs nodes
                    nodeParent = nodeParent.rightNode ?: throw Exception("An attempt to take a non-existent son")
                    curNode = nodeParent.rightNode ?: throw Exception("An attempt to take a non-existent son")
                    nodeBrother = nodeParent.leftNode ?: throw Exception("An attempt to take a non-existent son")
                }

                // Consideration of the case 2.
                // nodeBrother's sons are black
                val leftSonOfBrother = nodeBrother.leftNode
                val rightSonOfBrother = nodeBrother.rightNode
                if ((leftSonOfBrother == null || leftSonOfBrother.color == Color.BLACK) &&
                    (rightSonOfBrother == null || rightSonOfBrother.color == Color.BLACK)
                ) {
                    nodeBrother.color = Color.RED
                    curNode = nodeParent
                    nodeParent = curNode.parent
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
                    nodeBrother.leftNode?.color = Color.BLACK
                    if (nodeParent.parent == null) {
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


    internal fun balanceAfterAdding(node: RBNode<K, V>): RBNode<K, V> {
        var nodeParent = node.parent
        var curNode = node
        while (nodeParent != null && nodeParent.color == Color.RED) {
            // It must exist, since the root of the tree cannot be red
            val nodeGrandParent = nodeParent.parent ?: throw Exception("An attempt to take a non-existent parent")

            if (nodeParent == nodeGrandParent.leftNode) {
                val nodeUncle = nodeGrandParent.rightNode

                if (nodeUncle == null || nodeUncle.color == Color.BLACK) {
                    if (curNode == nodeParent.rightNode) {
                        nodeParent = leftRotate(nodeParent)
                        curNode = nodeParent.leftNode ?: throw Exception("An attempt to take a non-existent son")
                    }
                    nodeParent.color = Color.BLACK
                    nodeGrandParent.color = Color.RED
                    rightRotate(nodeGrandParent)
                } else {
                    nodeParent.color = Color.BLACK
                    nodeUncle.color = Color.BLACK
                    nodeGrandParent.color = Color.RED

                    curNode = nodeGrandParent
                    nodeParent = curNode.parent
                }
            } else {
                val nodeUncle = nodeGrandParent.leftNode

                if (nodeUncle == null || nodeUncle.color == Color.BLACK) {
                    if (curNode == nodeParent.leftNode) {
                        nodeParent = rightRotate(nodeParent)
                        curNode = nodeParent.rightNode ?: throw Exception("An attempt to take a non-existent son")
                    }
                    nodeParent.color = Color.BLACK
                    nodeGrandParent.color = Color.RED
                    leftRotate(nodeGrandParent)
                } else {
                    nodeParent.color = Color.BLACK
                    nodeUncle.color = Color.BLACK
                    nodeGrandParent.color = Color.RED

                    curNode = nodeGrandParent
                    nodeParent = curNode.parent
                }
            }
        }
        while (curNode.parent != null) {
            curNode = curNode.parent ?: throw Exception("An attempt to take a non-existent parent")
        }

        curNode.color = Color.BLACK
        return curNode
    }

}