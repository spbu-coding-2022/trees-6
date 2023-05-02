package app.presenter

import bstrees.model.dataBases.reps.TreeRepo
import bstrees.model.dataBases.TreeData
import bstrees.model.dataBases.converters.TreeToDataConverter
import bstrees.model.dataBases.converters.treesConverters.AvlToDataConverter
import bstrees.model.dataBases.converters.treesConverters.BsToDataConverter
import bstrees.model.dataBases.converters.treesConverters.RbToDataConverter
import bstrees.model.dataBases.converters.utils.createStringConverter
import bstrees.model.trees.BTree


class TreePresenter(private val db: TreeRepo) {
    lateinit var tree: TreeData
        private set

    fun loadTree(treeName: String, treeType: String) {
        tree = db.getTree(treeName, treeType) ?: throw IllegalArgumentException("There is no tree with that name: $treeName")
    }

    fun createTree(treeName: String, treeType: String, keyType: String, valueType: String) {
        tree = TreeData(treeName, treeType, keyType, valueType, null)
        db.setTree(tree)
    }

    private fun createStrategy(): TreeToDataConverter<*, *, *, *, *> = when (tree.treeType) {
        "AVL" -> AvlToDataConverter(
            keyStringConverter = createStringConverter(tree.keyType),
            valueStringConverter = createStringConverter(tree.valueType),
            keyType = tree.keyType,
            valueType = tree.valueType
        )
        "RB" -> RbToDataConverter(
            keyStringConverter = createStringConverter(tree.keyType),
            valueStringConverter = createStringConverter(tree.valueType),
            keyType = tree.keyType,
            valueType = tree.valueType
        )
        "BS" -> BsToDataConverter(
            keyStringConverter = createStringConverter(tree.keyType),
            valueStringConverter = createStringConverter(tree.valueType),
            keyType = tree.keyType,
            valueType = tree.valueType
        )
        else -> throw IllegalArgumentException("Unknown tree type ${tree.treeType}")
    }


    fun addNode(key: String, value: String) {
        fun <K : Comparable<K>, V, TREE_TYPE : BTree<K, V, *>> helper(strategy: TreeToDataConverter<K, V, *, *, TREE_TYPE>) {
            val bTree = strategy.deserializeTree(tree)
            bTree.insert(
                strategy.keyStringConverter.fromString(key),
                strategy.valueStringConverter.fromString(value)
            )
            strategy.serializeTree(bTree, tree.name)
        }
        helper(createStrategy())
    }


    fun deleteNode(key: String) {
        fun <K : Comparable<K>, V, TREE_TYPE : BTree<K, V, *>> helper(strategy: TreeToDataConverter<K, V, *, *, TREE_TYPE>) {
            val bTree = strategy.deserializeTree(tree)
            bTree.delete(
                strategy.keyStringConverter.fromString(key),
            )
            strategy.serializeTree(bTree, tree.name)
        }
        helper(createStrategy())
    }
}