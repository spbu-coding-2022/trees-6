package utils

import bstrees.model.trees.randomBinarySearch.RandomBSNode
import bstrees.model.trees.randomBinarySearch.RandomBSTree


/**
 * BSTreeUtil performs the BSTree handler function for test classes
 */
object BSTreeUtil {

    fun checkNodeEquals(root1: RandomBSNode<*, *>?, root2: RandomBSNode<*, *>?): Boolean {
        if (root1 == null || root2 == null) {
            return root1 == null && root2 == null
        }
        return root1.key == root2.key && root1.value == root2.value &&
                checkNodeEquals(root1.leftNode, root2.leftNode) &&
                checkNodeEquals(root1.rightNode, root2.rightNode)
    }


    // This will be rewritten when the BSTree implementation is added
    fun createBSTree(): RandomBSTree<Int, Int> {
        val testRandomBSTree: RandomBSTree<Int, Int> = RandomBSTree()
        testRandomBSTree.root = RandomBSNode(0, 0)
        val testBSTreeRoot = testRandomBSTree.root!!

        val leftSubTestRandomBSTree: RandomBSTree<Int, Int> = RandomBSTree()
        leftSubTestRandomBSTree.root = RandomBSNode(-2, 2)
        val leftSubTestBSTreeRoot = leftSubTestRandomBSTree.root!!
        leftSubTestBSTreeRoot.leftNode = RandomBSNode(-3, 5)
        leftSubTestBSTreeRoot.rightNode = RandomBSNode(-1, 6)

        val rightSubTestRandomBSTree: RandomBSTree<Int, Int> = RandomBSTree()
        rightSubTestRandomBSTree.root = RandomBSNode(2, 3)
        val rightSubTestBSTreeRoot = rightSubTestRandomBSTree.root!!
        rightSubTestBSTreeRoot.leftNode = RandomBSNode(1, 7)
        rightSubTestBSTreeRoot.rightNode = RandomBSNode(3, 8)


        testBSTreeRoot.leftNode = leftSubTestBSTreeRoot
        testBSTreeRoot.rightNode = rightSubTestBSTreeRoot

        return testRandomBSTree
    }

    fun createLeftRotatedBSTree(): RandomBSTree<Int, Int> {
        val leftRotatedTestRandomBSTree: RandomBSTree<Int, Int> = RandomBSTree()
        leftRotatedTestRandomBSTree.root = RandomBSNode(2, 3)
        val leftRotatedTestBSTreeRoot = leftRotatedTestRandomBSTree.root!!

        leftRotatedTestBSTreeRoot.rightNode = RandomBSNode(3, 8)
        leftRotatedTestBSTreeRoot.leftNode = RandomBSNode(0, 0)

        val leftRotatedSubTestRandomBSTree: RandomBSTree<Int, Int> = RandomBSTree()
        leftRotatedSubTestRandomBSTree.root = RandomBSNode(-2, 2)
        val leftRotatedSubTestBSTreeRoot = leftRotatedSubTestRandomBSTree.root!!
        leftRotatedSubTestBSTreeRoot.leftNode = RandomBSNode(-3, 5)
        leftRotatedSubTestBSTreeRoot.rightNode = RandomBSNode(-1, 6)

        leftRotatedTestBSTreeRoot.leftNode?.rightNode = RandomBSNode(1, 7)
        leftRotatedTestBSTreeRoot.leftNode?.leftNode = leftRotatedSubTestBSTreeRoot

        return leftRotatedTestRandomBSTree
    }

    fun createRightRotatedBSTree(): RandomBSTree<Int, Int> {
        val rightRotatedTestRandomBSTree: RandomBSTree<Int, Int> = RandomBSTree()
        rightRotatedTestRandomBSTree.root = RandomBSNode(-2, 2)
        val rightRotatedTestBSTreeRoot = rightRotatedTestRandomBSTree.root!!

        rightRotatedTestBSTreeRoot.leftNode = RandomBSNode(-3, 5)
        rightRotatedTestBSTreeRoot.rightNode = RandomBSNode(0, 0)

        val rightRotatedSubTestRandomBSTree: RandomBSTree<Int, Int> = RandomBSTree()
        rightRotatedSubTestRandomBSTree.root = RandomBSNode(2, 3)
        val rightRotatedSubTestBSTreeRoot = rightRotatedSubTestRandomBSTree.root!!
        rightRotatedSubTestBSTreeRoot.leftNode = RandomBSNode(1, 7)
        rightRotatedSubTestBSTreeRoot.rightNode = RandomBSNode(3, 8)

        rightRotatedTestBSTreeRoot.rightNode?.leftNode = RandomBSNode(-1, 6)
        rightRotatedTestBSTreeRoot.rightNode?.rightNode = rightRotatedSubTestBSTreeRoot

        return rightRotatedTestRandomBSTree
    }
}