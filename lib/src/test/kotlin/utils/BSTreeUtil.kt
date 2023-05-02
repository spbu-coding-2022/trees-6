package utils

import bstrees.model.dataBases.converters.utils.ComparableStringConverter
import bstrees.model.dataBases.converters.utils.StringConverter
import bstrees.model.trees.BSNode
import bstrees.model.trees.BSTree
import bstrees.model.trees.avl.AvlNode
import bstrees.model.trees.avl.AvlTree
import bstrees.model.trees.randomBinarySearch.RandomBSNode
import bstrees.model.trees.randomBinarySearch.RandomBSTree
import bstrees.model.trees.redBlack.RBNode
import bstrees.model.trees.redBlack.RBTree

/**
 * BSTreeUtil performs the BSTree handler function for test classes
 */
@Suppress("UNCHECKED_CAST")
object BSTreeUtil {

    fun <K : Comparable<K>, V, NODE_TYPE : BSNode<K, V, NODE_TYPE>> checkNodeEquals(
        root1: NODE_TYPE?,
        root2: NODE_TYPE?
    ): Boolean {
        if (root1 == null || root2 == null) {
            return root1 == null && root2 == null
        }
        return root1.key == root2.key && root1.value == root2.value &&
                checkNodeEquals(root1.leftNode, root2.leftNode) &&
                checkNodeEquals(root1.rightNode, root2.rightNode)
    }

    private fun <K : Comparable<K>, V, TREE_TYPE : BSTree<K, V, *>> getTreeInstance(treeType: String): TREE_TYPE {
        return when (treeType) {
            "AVL" -> AvlTree<K, V>() as TREE_TYPE
            "RB" -> RBTree<K, V>() as TREE_TYPE
            "BS" -> RandomBSTree<K, V>() as TREE_TYPE
            else -> throw IllegalArgumentException("Unknown tree type $treeType")
        }
    }

    private fun <K : Comparable<K>, V, NODE_TYPE> getNodeInstance(treeType: String, key: K, value: V): NODE_TYPE {
        return when (treeType) {
            "AVL" -> AvlNode(key, value) as NODE_TYPE
            "RB" -> RBNode(key, value) as NODE_TYPE
            "BS" -> RandomBSNode(key, value) as NODE_TYPE
            else -> throw IllegalArgumentException("Unknown tree type $treeType")
        }
    }

    fun <K : Comparable<K>, V, NODE_TYPE : BSNode<K, V, NODE_TYPE>, TREE_TYPE : BSTree<K, V, NODE_TYPE>> createTree(
        keyStringConverter: ComparableStringConverter<K>,
        valueStringConverter: StringConverter<V>,
        treeType: String
    ): TREE_TYPE {
        val tree: TREE_TYPE = getTreeInstance(treeType)
        tree.root = getNodeInstance(
            treeType,
            keyStringConverter.fromString("0"),
            valueStringConverter.fromString("0")
        )
        val testRBTreeRoot = tree.root!!

        val leftSubTestRBTree: TREE_TYPE = getTreeInstance(treeType)
        leftSubTestRBTree.root = getNodeInstance(
            treeType,
            keyStringConverter.fromString("-2"),
            valueStringConverter.fromString("2")
        )
        val leftSubTestRBTreeRoot = leftSubTestRBTree.root!!
        leftSubTestRBTreeRoot.leftNode = getNodeInstance(
            treeType,
            keyStringConverter.fromString("-3"),
            valueStringConverter.fromString("5")
        )
        leftSubTestRBTreeRoot.rightNode = getNodeInstance(
            treeType,
            keyStringConverter.fromString("-1"),
            valueStringConverter.fromString("6")
        )

        val rightSubTestRBTree: TREE_TYPE = getTreeInstance(treeType)
        rightSubTestRBTree.root = getNodeInstance(
            treeType,
            keyStringConverter.fromString("2"),
            valueStringConverter.fromString("3")
        )
        val rightSubTestRBTreeRoot = rightSubTestRBTree.root!!
        rightSubTestRBTreeRoot.leftNode = getNodeInstance(
            treeType,
            keyStringConverter.fromString("1"),
            valueStringConverter.fromString("7")
        )
        rightSubTestRBTreeRoot.rightNode = getNodeInstance(
            treeType,
            keyStringConverter.fromString("3"),
            valueStringConverter.fromString("8")
        )

        testRBTreeRoot.leftNode = leftSubTestRBTreeRoot
        testRBTreeRoot.rightNode = rightSubTestRBTreeRoot

        return tree
    }

    fun <K : Comparable<K>, V, NODE_TYPE : BSNode<K, V, NODE_TYPE>, TREE_TYPE : BSTree<K, V, NODE_TYPE>> createLeftRotatedTree(
        keyStringConverter: ComparableStringConverter<K>,
        valueStringConverter: StringConverter<V>,
        treeType: String
    ): TREE_TYPE {
        val leftRotatedTestRandomBSTree: TREE_TYPE = getTreeInstance(treeType)
        leftRotatedTestRandomBSTree.root = getNodeInstance(
            treeType,
            keyStringConverter.fromString("2"),
            valueStringConverter.fromString("3")
        )
        val leftRotatedTestBSTreeRoot = leftRotatedTestRandomBSTree.root!!

        leftRotatedTestBSTreeRoot.rightNode = getNodeInstance(
            treeType,
            keyStringConverter.fromString("3"),
            valueStringConverter.fromString("8")
        )
        leftRotatedTestBSTreeRoot.leftNode = getNodeInstance(
            treeType,
            keyStringConverter.fromString("0"),
            valueStringConverter.fromString("0")
        )

        val leftRotatedSubTestRandomBSTree: TREE_TYPE = getTreeInstance(treeType)
        leftRotatedSubTestRandomBSTree.root = getNodeInstance(
            treeType,
            keyStringConverter.fromString("-2"),
            valueStringConverter.fromString("2")
        )
        val leftRotatedSubTestBSTreeRoot = leftRotatedSubTestRandomBSTree.root!!
        leftRotatedSubTestBSTreeRoot.leftNode = getNodeInstance(
            treeType,
            keyStringConverter.fromString("-3"),
            valueStringConverter.fromString("5")
        )
        leftRotatedSubTestBSTreeRoot.rightNode = getNodeInstance(
            treeType,
            keyStringConverter.fromString("-1"),
            valueStringConverter.fromString("6")
        )

        leftRotatedTestBSTreeRoot.leftNode?.rightNode = getNodeInstance(
            treeType,
            keyStringConverter.fromString("1"),
            valueStringConverter.fromString("7")
        )
        leftRotatedTestBSTreeRoot.leftNode?.leftNode = leftRotatedSubTestBSTreeRoot

        return leftRotatedTestRandomBSTree
    }

    fun <K : Comparable<K>, V, NODE_TYPE : BSNode<K, V, NODE_TYPE>, TREE_TYPE : BSTree<K, V, NODE_TYPE>> createRightRotatedTree(
        keyStringConverter: ComparableStringConverter<K>,
        valueStringConverter: StringConverter<V>,
        treeType: String
    ): TREE_TYPE {
        val rightRotatedTestRandomBSTree: TREE_TYPE = getTreeInstance(treeType)
        rightRotatedTestRandomBSTree.root = getNodeInstance(
            treeType,
            keyStringConverter.fromString("-2"),
            valueStringConverter.fromString("2")
        )
        val rightRotatedTestBSTreeRoot = rightRotatedTestRandomBSTree.root!!

        rightRotatedTestBSTreeRoot.leftNode = getNodeInstance(
            treeType,
            keyStringConverter.fromString("-3"),
            valueStringConverter.fromString("5")
        )
        rightRotatedTestBSTreeRoot.rightNode = getNodeInstance(
            treeType,
            keyStringConverter.fromString("0"),
            valueStringConverter.fromString("0")
        )

        val rightRotatedSubTestRandomBSTree: TREE_TYPE = getTreeInstance(treeType)
        rightRotatedSubTestRandomBSTree.root = getNodeInstance(
            treeType,
            keyStringConverter.fromString("2"),
            valueStringConverter.fromString("3")
        )
        val rightRotatedSubTestBSTreeRoot = rightRotatedSubTestRandomBSTree.root!!
        rightRotatedSubTestBSTreeRoot.leftNode = getNodeInstance(
            treeType,
            keyStringConverter.fromString("1"),
            valueStringConverter.fromString("7")
        )
        rightRotatedSubTestBSTreeRoot.rightNode = getNodeInstance(
            treeType,
            keyStringConverter.fromString("3"),
            valueStringConverter.fromString("8")
        )

        rightRotatedTestBSTreeRoot.rightNode?.leftNode = getNodeInstance(
            treeType,
            keyStringConverter.fromString("-1"),
            valueStringConverter.fromString("6")
        )
        rightRotatedTestBSTreeRoot.rightNode?.rightNode = rightRotatedSubTestBSTreeRoot

        return rightRotatedTestRandomBSTree
    }
}