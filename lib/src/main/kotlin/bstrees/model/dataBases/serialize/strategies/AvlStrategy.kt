package bstrees.model.dataBases.serialize.strategies

import bstrees.model.dataBases.serialize.SerializeStrategy
import bstrees.model.dataBases.serialize.types.SerializableNode
import bstrees.model.dataBases.serialize.types.SerializableTree
import bstrees.model.trees.avl.AvlNode
import bstrees.model.trees.avl.AvlTree

class AvlStrategy<K : Comparable<K>, V>(
    serializeKey: (K) -> String,
    serializeValue: (V) -> String,
    deserializeKey: (String) -> K,
    deserializeValue: (String) -> V,
    keyType: String,
    valueType: String,
) : SerializeStrategy<K, V, Int, AvlNode<K, V>, AvlTree<K, V>>(
    serializeKey,
    serializeValue,
    deserializeKey,
    deserializeValue,
    keyType,
    valueType,
) {
    override fun serializeNode(node: AvlNode<K, V>): SerializableNode = SerializableNode(
        serializeKey(node.key),
        serializeValue(node.value),
        serializeMetadata(node.height),
        0,
        0,
        node.leftNode?.let { serializeNode(it) },
        node.rightNode?.let { serializeNode(it) }
    )

    override fun deserializeNode(node: SerializableNode): AvlNode<K, V> {
        val avlnode: AvlNode<K, V> = AvlNode(deserializeKey(node.key), deserializeValue(node.value))
        if (node.metadata[0] != 'H') throw Exception("Wrong metadata. Impossible to deserialize")
        avlnode.height = deserializeMetadata(node.metadata)
        node.leftNode?.let { deserializeNode(it) }
        node.rightNode?.let { deserializeNode(it) }
        linkParents(avlnode)
        return avlnode
    }

    override fun serializeTree(tree: AvlTree<K, V>, treeName: String) = SerializableTree(
        name = treeName,
        treeType = "AVL",
        keyType = keyType,
        valueType = valueType,
        root = tree.root?.let { serializeNode(it) }
    )

    override fun deserializeTree(tree: SerializableTree): AvlTree<K, V> {
        if (tree.treeType != "AVL") throw Exception("Wrong tree type. Impossible to deserialize")
        val avltree = AvlTree<K, V>()
        avltree.root = tree.root?.let { deserializeNode(it) }
        return avltree
    }

    override fun serializeMetadata(meta: Int) = "H$meta"

    override fun deserializeMetadata(meta: String) = meta.substring(1).toInt()
}
