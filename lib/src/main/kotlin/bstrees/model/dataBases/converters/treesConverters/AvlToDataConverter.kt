package bstrees.model.dataBases.converters.treesConverters

import bstrees.model.dataBases.NodeData
import bstrees.model.dataBases.TreeData
import bstrees.model.dataBases.converters.TreeToDataConverter
import bstrees.model.dataBases.converters.utils.ComparableStringConverter
import bstrees.model.dataBases.converters.utils.StringConverter
import bstrees.model.trees.avl.AvlNode
import bstrees.model.trees.avl.AvlTree

class AvlToDataConverter<K : Comparable<K>, V>(
    keyStringConverter: ComparableStringConverter<K>,
    valueStringConverter: StringConverter<V>,
    keyType: String,
    valueType: String,
) : TreeToDataConverter<K, V, Int, AvlNode<K, V>, AvlTree<K, V>>(
    keyStringConverter,
    valueStringConverter,
    keyType,
    valueType,
) {
    private fun serializeNode(node: AvlNode<K, V>): NodeData = NodeData(
        keyStringConverter.toString(node.key),
        valueStringConverter.toString(node.value),
        serializeMetadata(node.height),
        0,
        0,
        node.leftNode?.let { serializeNode(it) },
        node.rightNode?.let { serializeNode(it) }
    )

    private fun deserializeNode(node: NodeData): AvlNode<K, V> {
        val avlnode: AvlNode<K, V> = AvlNode(keyStringConverter.fromString(node.key), valueStringConverter.fromString(node.value))
        if (node.metadata[0] != 'H') throw Exception("Wrong metadata. Impossible to deserialize")
        avlnode.height = deserializeMetadata(node.metadata)
        node.leftNode?.let { deserializeNode(it) }
        node.rightNode?.let { deserializeNode(it) }
        linkParents(avlnode)
        return avlnode
    }

    override fun serializeTree(tree: AvlTree<K, V>, treeName: String) = TreeData(
        name = treeName,
        treeType = "AVL",
        keyType = keyType,
        valueType = valueType,
        root = tree.root?.let { serializeNode(it) }
    )

    override fun deserializeTree(tree: TreeData): AvlTree<K, V> {
        if (tree.treeType != "AVL") throw Exception("Wrong tree type. Impossible to deserialize")
        val avltree = AvlTree<K, V>()
        avltree.root = tree.root?.let { deserializeNode(it) }
        return avltree
    }

    private fun serializeMetadata(meta: Int) = "H$meta"

    private fun deserializeMetadata(meta: String) = meta.substring(1).toInt()
}
