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
        testBSTree.setRoot(BSNode(0, 0))
        val testBSTreeRoot = testBSTree.getRoot()!!

        val leftSubTestBSTree: BSTree<Int, Int> = BSTree()
        leftSubTestBSTree.setRoot(BSNode(-2, 2))
        val leftSubTestBSTreeRoot = leftSubTestBSTree.getRoot()!!
        leftSubTestBSTreeRoot.setLeftNode(BSNode(-3, 5))
        leftSubTestBSTreeRoot.setRightNode(BSNode(-1, 6))

        val rightSubTestBSTree: BSTree<Int, Int> = BSTree()
        rightSubTestBSTree.setRoot(BSNode(2, 3))
        val rightSubTestBSTreeRoot = rightSubTestBSTree.getRoot()!!
        rightSubTestBSTreeRoot.setLeftNode(BSNode(1, 7))
        rightSubTestBSTreeRoot.setRightNode(BSNode(3, 8))


        testBSTreeRoot.setLeftNode(leftSubTestBSTreeRoot)
        testBSTreeRoot.setRightNode(rightSubTestBSTreeRoot)

        return testBSTree
    }

    fun createLeftRotatedBSTree(): BSTree<Int, Int> {
        val leftRotatedTestBSTree: BSTree<Int, Int> = BSTree()
        leftRotatedTestBSTree.setRoot(BSNode(-2, 2))
        val leftRotatedTestBSTreeRoot = leftRotatedTestBSTree.getRoot()!!

        leftRotatedTestBSTreeRoot.setLeftNode(BSNode(-3, 5))
        leftRotatedTestBSTreeRoot.setRightNode(BSNode(0, 0))

        val rightRotatedSubTestBSTree: BSTree<Int, Int> = BSTree()
        rightRotatedSubTestBSTree.setRoot(BSNode(2, 3))
        val rightRotatedSubTestBSTreeRoot = rightRotatedSubTestBSTree.getRoot()!!
        rightRotatedSubTestBSTreeRoot.setLeftNode(BSNode(1, 7))
        rightRotatedSubTestBSTreeRoot.setRightNode(BSNode(3, 8))

        leftRotatedTestBSTreeRoot.getRightNode()?.setLeftNode(BSNode(-1, 6))
        leftRotatedTestBSTreeRoot.getRightNode()?.setRightNode(rightRotatedSubTestBSTreeRoot)

        return leftRotatedTestBSTree
    }

    fun createRightRotatedBSTree(): BSTree<Int, Int> {
        val rightRotatedTestBSTree: BSTree<Int, Int> = BSTree()
        rightRotatedTestBSTree.setRoot(BSNode(2, 3))
        val rightRotatedTestBSTreeRoot = rightRotatedTestBSTree.getRoot()!!

        rightRotatedTestBSTreeRoot.setRightNode(BSNode(3, 8))
        rightRotatedTestBSTreeRoot.setLeftNode(BSNode(0, 0))

        val leftRotatedSubTestBSTree: BSTree<Int, Int> = BSTree()
        leftRotatedSubTestBSTree.setRoot(BSNode(-2, 2))
        val leftRotatedSubTestBSTreeRoot = leftRotatedSubTestBSTree.getRoot()!!
        leftRotatedSubTestBSTreeRoot.setLeftNode(BSNode(-3, 5))
        leftRotatedSubTestBSTreeRoot.setRightNode(BSNode(-1, 6))

        rightRotatedTestBSTreeRoot.getLeftNode()?.setRightNode(BSNode(1, 7))
        rightRotatedTestBSTreeRoot.getLeftNode()?.setLeftNode(leftRotatedSubTestBSTreeRoot)

        return rightRotatedTestBSTree
    }
}