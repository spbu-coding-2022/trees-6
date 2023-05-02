package bstrees.model.dataBases.converters

import bstrees.model.dataBases.TreeData
import bstrees.model.dataBases.converters.utils.ComparableStringConverter
import bstrees.model.dataBases.converters.utils.StringConverter
import bstrees.model.trees.BTree
import bstrees.model.trees.Node

abstract class TreeToDataConverter<
        K : Comparable<K>, V, M,
        NODE_TYPE : Node<K, V, NODE_TYPE>,
        TREE_TYPE : BTree<K, V, NODE_TYPE>>(
    val keyStringConverter: ComparableStringConverter<K>,
    val valueStringConverter: StringConverter<V>,
    val keyType: String,
    val valueType: String,
) {
    abstract fun serializeTree(tree: TREE_TYPE, treeName: String): TreeData

    abstract fun deserializeTree(tree: TreeData): TREE_TYPE

    protected fun linkParents(node: NODE_TYPE) {
        node.leftNode?.parent = node
        node.rightNode?.parent = node
        node.leftNode?.let { linkParents(it) }
        node.rightNode?.let { linkParents(it) }
    }
}
