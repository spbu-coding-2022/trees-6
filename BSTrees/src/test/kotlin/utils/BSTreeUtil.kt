package utils

import binarySearchTree.BSNode
import binarySearchTree.BSTree


/**
 * BSTreeUtil performs the BSTree handler function for test classes
 */
object BSTreeUtil {

    fun checkNodeEquals(root1: BSNode<*, *>?, root2: BSNode<*, *>?): Boolean {
        if (root1 == null || root2 == null) {
            return root1 == null && root2 == null
        }
        return root1.getKey() == root2.getKey() && root1.getValue() == root1.getValue() &&
                checkNodeEquals(root1.getLeftNode(), root2.getLeftNode()) &&
                checkNodeEquals(root1.getRightNode(), root2.getRightNode())
    }


    // This will be rewritten when the BSTree implementation is added
    fun createBSTree(): BSTree<Int, Int> {
        val testBSTree: BSTree<Int, Int> = BSTree()
        testBSTree.root = BSNode(0, 0)
        val testBSTreeRoot = testBSTree.root!!

        val leftSubTestBSTree: BSTree<Int, Int> = BSTree()
        leftSubTestBSTree.root = BSNode(-2, 2)
        val leftSubTestBSTreeRoot = leftSubTestBSTree.root!!
        leftSubTestBSTreeRoot.setLeftNode(BSNode(-3, 5))
        leftSubTestBSTreeRoot.setRightNode(BSNode(-1, 6))

        val rightSubTestBSTree: BSTree<Int, Int> = BSTree()
        rightSubTestBSTree.root = BSNode(2, 3)
        val rightSubTestBSTreeRoot = rightSubTestBSTree.root!!
        rightSubTestBSTreeRoot.setLeftNode(BSNode(1, 7))
        rightSubTestBSTreeRoot.setRightNode(BSNode(3, 8))


        testBSTreeRoot.setLeftNode(leftSubTestBSTreeRoot)
        testBSTreeRoot.setRightNode(rightSubTestBSTreeRoot)

        return testBSTree
    }

    fun createLeftRotatedBSTree(): BSTree<Int, Int> {
        val leftRotatedTestBSTree: BSTree<Int, Int> = BSTree()
        leftRotatedTestBSTree.root = BSNode(2, 3)
        val leftRotatedTestBSTreeRoot = leftRotatedTestBSTree.root!!

        leftRotatedTestBSTreeRoot.setRightNode(BSNode(3, 8))
        leftRotatedTestBSTreeRoot.setLeftNode(BSNode(0, 0))

        val leftRotatedSubTestBSTree: BSTree<Int, Int> = BSTree()
        leftRotatedSubTestBSTree.root = BSNode(-2, 2)
        val leftRotatedSubTestBSTreeRoot = leftRotatedSubTestBSTree.root!!
        leftRotatedSubTestBSTreeRoot.setLeftNode(BSNode(-3, 5))
        leftRotatedSubTestBSTreeRoot.setRightNode(BSNode(-1, 6))

        leftRotatedTestBSTreeRoot.getLeftNode()?.setRightNode(BSNode(1, 7))
        leftRotatedTestBSTreeRoot.getLeftNode()?.setLeftNode(leftRotatedSubTestBSTreeRoot)

        return leftRotatedTestBSTree
    }

    fun createRightRotatedBSTree(): BSTree<Int, Int> {
        val rightRotatedTestBSTree: BSTree<Int, Int> = BSTree()
        rightRotatedTestBSTree.root = BSNode(-2, 2)
        val rightRotatedTestBSTreeRoot = rightRotatedTestBSTree.root!!

        rightRotatedTestBSTreeRoot.setLeftNode(BSNode(-3, 5))
        rightRotatedTestBSTreeRoot.setRightNode(BSNode(0, 0))

        val rightRotatedSubTestBSTree: BSTree<Int, Int> = BSTree()
        rightRotatedSubTestBSTree.root = BSNode(2, 3)
        val rightRotatedSubTestBSTreeRoot = rightRotatedSubTestBSTree.root!!
        rightRotatedSubTestBSTreeRoot.setLeftNode(BSNode(1, 7))
        rightRotatedSubTestBSTreeRoot.setRightNode(BSNode(3, 8))

        rightRotatedTestBSTreeRoot.getRightNode()?.setLeftNode(BSNode(-1, 6))
        rightRotatedTestBSTreeRoot.getRightNode()?.setRightNode(rightRotatedSubTestBSTreeRoot)

        return rightRotatedTestBSTree
    }
}