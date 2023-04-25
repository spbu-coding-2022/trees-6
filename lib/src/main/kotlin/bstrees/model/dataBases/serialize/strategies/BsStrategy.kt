package bstrees.model.dataBases.serialize.strategies

import bstrees.model.dataBases.serialize.types.SerializableNode
import bstrees.model.dataBases.serialize.types.SerializableTree
import bstrees.model.dataBases.serialize.SerializeStrategy
import bstrees.model.trees.binarySearch.BSNode
import bstrees.model.trees.binarySearch.BSTree

class BsStrategy<K : Comparable<K>, V>(
    serializeKey: (K) -> String,
    serializeValue: (V) -> String,
    deserializeKey: (String) -> K,
    deserializeValue: (String) -> V
) : SerializeStrategy<K, V, BSNode<K, V>, BSTree<K, V>>(
    serializeKey,
    serializeValue,
    deserializeKey,
    deserializeValue
) {

    override fun serializeNode(node: BSNode<K, V>): SerializableNode = SerializableNode(
        serializeKey(node.key),
        serializeValue(node.value),
        "S${node.size}",
        node.leftNode?.let { serializeNode(it) },
        node.rightNode?.let { serializeNode(it) }
    )

    override fun deserializeNode(node: SerializableNode): BSNode<K, V> {
        val bsnode: BSNode<K, V> = BSNode(deserializeKey(node.key), deserializeValue(node.value))
        if (node.metadata[0] != 'S') throw Exception("Wrong metadata. Impossible to deserialize")
        bsnode.size = deserializeMetadata(node.metadata)
        node.leftNode?.let { deserializeNode(it) }
        node.rightNode?.let { deserializeNode(it) }
        linkParents(bsnode)
        return bsnode
    }

    override fun deserializeTree(tree: SerializableTree): BSTree<K, V>{
        if (tree.treeType != "BS") throw Exception("Wrong tree type. Impossible to deserialize")
        val bstree = BSTree<K, V>()
        bstree.root = tree.root?.let { deserializeNode(it) }
        return bstree
    }

    override fun serializeTree(tree: BSTree<K, V>, treeName: String) = SerializableTree(
        name = treeName,
        treeType = "BS",
        root = tree.root?.let { serializeNode(it) }
    )

    private fun deserializeMetadata(meta: String) = meta.substring(1).toInt()

}