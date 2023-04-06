package utils

import binarySearchTree.BSNode
import binarySearchTree.BSTree


/**
 * BSTreeUtil performs the BSTree handler function for test classes
 */
object BSTreeUtil {

    fun checkTreeEquals(tree1: BSTree<*, *>?, tree2: BSTree<*, *>?): Boolean {
        if (tree1 == null || tree2 == null) {
            return tree1 == null && tree2 == null
        }
        return checkRootEquals(tree1.getRoot(), tree2.getRoot()) &&
                checkTreeEquals(tree1.getLeftTree(), tree2.getLeftTree()) &&
                checkTreeEquals(tree1.getRightTree(), tree2.getRightTree())
    }

    fun checkRootEquals(root1: BSNode<*, *>?, root2: BSNode<*, *>?): Boolean {
        if (root1 == null || root2 == null) {
            return root1 == null && root2 == null
        }
        return root1.getKey() == root2.getKey() && root1.getValue() == root1.getValue()
    }

    fun createBSTree(): BSTree<Int, Int> {
        val testBSTree: BSTree<Int, Int> = BSTree(BSNode(0, 0))

        val leftSubTestBSTree = BSTree(BSNode(-2, 2))
        leftSubTestBSTree.setLeftTree(BSTree(BSNode(-3, 5)))
        leftSubTestBSTree.setRightTree(BSTree(BSNode(-1, 6)))

        val rightSubTestBSTree = BSTree(BSNode(2, 3))
        rightSubTestBSTree.setLeftTree(BSTree(BSNode(1, 7)))
        rightSubTestBSTree.setRightTree(BSTree(BSNode(3, 8)))

        testBSTree.setLeftTree(leftSubTestBSTree)
        testBSTree.setRightTree(rightSubTestBSTree)

        return testBSTree
    }

    fun createLeftRotatedBSTree(): BSTree<Int, Int> {
        val leftRotatedTestBSTree: BSTree<Int, Int> = BSTree(BSNode(-2, 2))

        leftRotatedTestBSTree.setLeftTree(BSTree(BSNode(-3, 5)))
        leftRotatedTestBSTree.setRightTree(BSTree(BSNode(0, 0)))

        val rightRotatedSubTestBSTree = BSTree(BSNode(2, 3))
        rightRotatedSubTestBSTree.setLeftTree(BSTree(BSNode(1, 7)))
        rightRotatedSubTestBSTree.setRightTree(BSTree(BSNode(3, 8)))

        leftRotatedTestBSTree.getRightTree()?.setLeftTree(BSTree(BSNode(-1, 6)))
        leftRotatedTestBSTree.getRightTree()?.setRightTree(rightRotatedSubTestBSTree)

        return leftRotatedTestBSTree
    }

    fun createRightRotatedBSTree(): BSTree<Int, Int> {
        val rightRotatedTestBSTree: BSTree<Int, Int> = BSTree(BSNode(2, 3))

        rightRotatedTestBSTree.setRightTree(BSTree(BSNode(3, 8)))
        rightRotatedTestBSTree.setLeftTree(BSTree(BSNode(0, 0)))

        val leftRotatedSubTestBSTree = BSTree(BSNode(-2, 2))
        leftRotatedSubTestBSTree.setLeftTree(BSTree(BSNode(-3, 5)))
        leftRotatedSubTestBSTree.setRightTree(BSTree(BSNode(-1, 6)))

        rightRotatedTestBSTree.getLeftTree()?.setRightTree(BSTree(BSNode(1, 7)))
        rightRotatedTestBSTree.getLeftTree()?.setLeftTree(leftRotatedSubTestBSTree)

        return rightRotatedTestBSTree
    }
}