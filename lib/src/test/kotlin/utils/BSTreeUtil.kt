package utils

import bstrees.trees.binarySearch.BSNode
import bstrees.trees.binarySearch.BSTree


/**
 * BSTreeUtil performs the BSTree handler function for test classes
 */
object BSTreeUtil {

    fun checkNodeEquals(root1: BSNode<*, *>?, root2: BSNode<*, *>?): Boolean {
        if (root1 == null || root2 == null) {
            return root1 == null && root2 == null
        }
        return root1.key == root2.key && root1.value == root2.value &&
                checkNodeEquals(root1.leftNode, root2.leftNode) &&
                checkNodeEquals(root1.rightNode, root2.rightNode)
    }


    // This will be rewritten when the BSTree implementation is added
    fun createBSTree(): BSTree<Int, Int> {
        val testBSTree: BSTree<Int, Int> = BSTree()
        testBSTree.root = BSNode(0, 0)
        val testBSTreeRoot = testBSTree.root!!

        val leftSubTestBSTree: BSTree<Int, Int> = BSTree()
        leftSubTestBSTree.root = BSNode(-2, 2)
        val leftSubTestBSTreeRoot = leftSubTestBSTree.root!!
        leftSubTestBSTreeRoot.leftNode = BSNode(-3, 5)
        leftSubTestBSTreeRoot.rightNode = BSNode(-1, 6)

        val rightSubTestBSTree: BSTree<Int, Int> = BSTree()
        rightSubTestBSTree.root = BSNode(2, 3)
        val rightSubTestBSTreeRoot = rightSubTestBSTree.root!!
        rightSubTestBSTreeRoot.leftNode = BSNode(1, 7)
        rightSubTestBSTreeRoot.rightNode = BSNode(3, 8)


        testBSTreeRoot.leftNode = leftSubTestBSTreeRoot
        testBSTreeRoot.rightNode = rightSubTestBSTreeRoot

        return testBSTree
    }

    fun createLeftRotatedBSTree(): BSTree<Int, Int> {
        val leftRotatedTestBSTree: BSTree<Int, Int> = BSTree()
        leftRotatedTestBSTree.root = BSNode(2, 3)
        val leftRotatedTestBSTreeRoot = leftRotatedTestBSTree.root!!

        leftRotatedTestBSTreeRoot.rightNode = BSNode(3, 8)
        leftRotatedTestBSTreeRoot.leftNode = BSNode(0, 0)

        val leftRotatedSubTestBSTree: BSTree<Int, Int> = BSTree()
        leftRotatedSubTestBSTree.root = BSNode(-2, 2)
        val leftRotatedSubTestBSTreeRoot = leftRotatedSubTestBSTree.root!!
        leftRotatedSubTestBSTreeRoot.leftNode = BSNode(-3, 5)
        leftRotatedSubTestBSTreeRoot.rightNode = BSNode(-1, 6)

        leftRotatedTestBSTreeRoot.leftNode?.rightNode = BSNode(1, 7)
        leftRotatedTestBSTreeRoot.leftNode?.leftNode = leftRotatedSubTestBSTreeRoot

        return leftRotatedTestBSTree
    }

    fun createRightRotatedBSTree(): BSTree<Int, Int> {
        val rightRotatedTestBSTree: BSTree<Int, Int> = BSTree()
        rightRotatedTestBSTree.root = BSNode(-2, 2)
        val rightRotatedTestBSTreeRoot = rightRotatedTestBSTree.root!!

        rightRotatedTestBSTreeRoot.leftNode = BSNode(-3, 5)
        rightRotatedTestBSTreeRoot.rightNode = BSNode(0, 0)

        val rightRotatedSubTestBSTree: BSTree<Int, Int> = BSTree()
        rightRotatedSubTestBSTree.root = BSNode(2, 3)
        val rightRotatedSubTestBSTreeRoot = rightRotatedSubTestBSTree.root!!
        rightRotatedSubTestBSTreeRoot.leftNode = BSNode(1, 7)
        rightRotatedSubTestBSTreeRoot.rightNode = BSNode(3, 8)

        rightRotatedTestBSTreeRoot.rightNode?.leftNode = BSNode(-1, 6)
        rightRotatedTestBSTreeRoot.rightNode?.rightNode = rightRotatedSubTestBSTreeRoot

        return rightRotatedTestBSTree
    }
}