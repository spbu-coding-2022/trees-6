package bstrees.model.dataBases.converters.treesConverters

import bstrees.model.dataBases.NodeData
import bstrees.model.dataBases.TreeData
import bstrees.model.dataBases.converters.TreeToDataConverter
import bstrees.model.dataBases.converters.utils.ComparableStringConverter
import bstrees.model.dataBases.converters.utils.StringConverter
import bstrees.model.trees.redBlack.RBNode
import bstrees.model.trees.redBlack.RBTree
import bstrees.model.trees.redBlack.RBNode.Color

class RbToDataConverter<K : Comparable<K>, V>(
    keyStringConverter: ComparableStringConverter<K>,
    valueStringConverter: StringConverter<V>,
    keyType: String,
    valueType: String,
) : TreeToDataConverter<K, V, Color, RBNode<K, V>, RBTree<K, V>>(
    keyStringConverter,
    valueStringConverter,
    keyType,
    valueType,
) {
    private fun serializeNode(node: RBNode<K, V>): NodeData = NodeData(
        keyStringConverter.toString(node.key),
        valueStringConverter.toString(node.value),
        serializeMetadata(node.color),
        0,
        0,
        node.leftNode?.let { serializeNode(it) },
        node.rightNode?.let { serializeNode(it) }
    )

    private fun deserializeNode(node: NodeData): RBNode<K, V> {
        val rbnode: RBNode<K, V> = RBNode(keyStringConverter.fromString(node.key), valueStringConverter.fromString(node.value))
        if (node.metadata != "RED" && node.metadata != "BLACK") throw Exception("Wrong metadata. Impossible to deserialize")
        rbnode.color = deserializeMetadata(node.metadata)
        rbnode.leftNode = node.leftNode?.let { deserializeNode(it) }
        rbnode.rightNode = node.rightNode?.let { deserializeNode(it) }
        linkParents(rbnode)
        return rbnode
    }

    override fun serializeTree(tree: RBTree<K, V>, treeName: String) = TreeData(
        name = treeName,
        treeType = "RB",
        keyType = keyType,
        valueType = valueType,
        root = tree.root?.let { serializeNode(it) }
    )

    override fun deserializeTree(tree: TreeData): RBTree<K, V> {
        if (tree.treeType != "RB") throw Exception("Wrong tree type. Impossible to deserialize")
        val rbtree = RBTree<K, V>()
        rbtree.root = tree.root?.let { deserializeNode(it) }
        return rbtree
    }

    private fun deserializeMetadata(meta: String): Color = if (meta == "RED") Color.RED else Color.BLACK

    private fun serializeMetadata(meta: Color): String = if (meta == Color.RED) "RED" else "BLACK"
}
