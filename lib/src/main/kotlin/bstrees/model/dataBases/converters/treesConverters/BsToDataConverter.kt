package bstrees.model.dataBases.converters.treesConverters

import bstrees.model.dataBases.NodeData
import bstrees.model.dataBases.TreeData
import bstrees.model.dataBases.converters.TreeToDataConverter
import bstrees.model.dataBases.converters.utils.ComparableStringConverter
import bstrees.model.dataBases.converters.utils.StringConverter
import bstrees.model.trees.binarySearch.BSNode
import bstrees.model.trees.binarySearch.BSTree

class BsToDataConverter<K : Comparable<K>, V>(
    keyStringConverter: ComparableStringConverter<K>,
    valueStringConverter: StringConverter<V>,
    keyType: String,
    valueType: String,
) : TreeToDataConverter<K, V, Int, BSNode<K, V>, BSTree<K, V>>(
    keyStringConverter,
    valueStringConverter,
    keyType,
    valueType
) {

    private fun serializeNode(node: BSNode<K, V>): NodeData = NodeData(
        keyStringConverter.toString(node.key),
        valueStringConverter.toString(node.value),
        serializeMetadata(node.size),
        0,
        0,
        node.leftNode?.let { serializeNode(it) },
        node.rightNode?.let { serializeNode(it) }
    )

    private fun deserializeNode(node: NodeData): BSNode<K, V> {
        val bsnode: BSNode<K, V> = BSNode(keyStringConverter.fromString(node.key), valueStringConverter.fromString(node.value))
        if (node.metadata[0] != 'S') throw Exception("Wrong metadata. Impossible to deserialize")
        bsnode.size = deserializeMetadata(node.metadata)
        node.leftNode?.let { deserializeNode(it) }
        node.rightNode?.let { deserializeNode(it) }
        linkParents(bsnode)
        return bsnode
    }

    override fun serializeTree(tree: BSTree<K, V>, treeName: String) = TreeData(
        name = treeName,
        treeType = "BS",
        keyType = keyType,
        valueType = valueType,
        root = tree.root?.let { serializeNode(it) }
    )

    override fun deserializeTree(tree: TreeData): BSTree<K, V> {
        if (tree.treeType != "BS") throw Exception("Wrong tree type. Impossible to deserialize")
        val bstree = BSTree<K, V>()
        bstree.root = tree.root?.let { deserializeNode(it) }
        return bstree
    }

    private fun serializeMetadata(meta: Int) = "S$meta"

    private fun deserializeMetadata(meta: String) = meta.substring(1).toInt()
}
