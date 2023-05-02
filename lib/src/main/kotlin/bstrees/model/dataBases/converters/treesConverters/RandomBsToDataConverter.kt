package bstrees.model.dataBases.converters.treesConverters

import bstrees.model.dataBases.NodeData
import bstrees.model.dataBases.TreeData
import bstrees.model.dataBases.converters.TreeToDataConverter
import bstrees.model.dataBases.converters.utils.ComparableStringConverter
import bstrees.model.dataBases.converters.utils.StringConverter
import bstrees.model.trees.randomBinarySearch.RandomBSNode
import bstrees.model.trees.randomBinarySearch.RandomBSTree

class RandomBsToDataConverter<K : Comparable<K>, V>(
    keyStringConverter: ComparableStringConverter<K>,
    valueStringConverter: StringConverter<V>,
    keyType: String,
    valueType: String,
) : TreeToDataConverter<K, V, Int, RandomBSNode<K, V>, RandomBSTree<K, V>>(
    keyStringConverter,
    valueStringConverter,
    keyType,
    valueType
) {

    private fun serializeNode(node: RandomBSNode<K, V>): NodeData = NodeData(
        keyStringConverter.toString(node.key),
        valueStringConverter.toString(node.value),
        serializeMetadata(node.size),
        0,
        0,
        node.leftNode?.let { serializeNode(it) },
        node.rightNode?.let { serializeNode(it) }
    )

    private fun deserializeNode(node: NodeData): RandomBSNode<K, V> {
        val bsnode: RandomBSNode<K, V> = RandomBSNode(keyStringConverter.fromString(node.key), valueStringConverter.fromString(node.value))
        if (node.metadata[0] != 'S') throw Exception("Wrong metadata. Impossible to deserialize")
        bsnode.size = deserializeMetadata(node.metadata)
        node.leftNode?.let { deserializeNode(it) }
        node.rightNode?.let { deserializeNode(it) }
        linkParents(bsnode)
        return bsnode
    }

    override fun serializeTree(tree: RandomBSTree<K, V>, treeName: String) = TreeData(
        name = treeName,
        treeType = "BS",
        keyType = keyType,
        valueType = valueType,
        root = tree.root?.let { serializeNode(it) }
    )

    override fun deserializeTree(tree: TreeData): RandomBSTree<K, V> {
        if (tree.treeType != "BS") throw Exception("Wrong tree type. Impossible to deserialize")
        val bstree = RandomBSTree<K, V>()
        bstree.root = tree.root?.let { deserializeNode(it) }
        return bstree
    }

    private fun serializeMetadata(meta: Int) = "S$meta"

    private fun deserializeMetadata(meta: String) = meta.substring(1).toInt()
}
