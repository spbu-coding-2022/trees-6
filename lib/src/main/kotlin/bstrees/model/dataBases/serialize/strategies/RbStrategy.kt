package bstrees.model.dataBases.serialize.strategies

import bstrees.model.dataBases.serialize.SerializeStrategy
import bstrees.model.dataBases.serialize.types.SerializableNode
import bstrees.model.dataBases.serialize.types.SerializableTree
import bstrees.model.trees.redBlack.RBNode
import bstrees.model.trees.redBlack.RBTree
import bstrees.model.trees.redBlack.RBNode.Color

class RbStrategy<K : Comparable<K>, V>(
    serializeKey: (K) -> String,
    serializeValue: (V) -> String,
    deserializeKey: (String) -> K,
    deserializeValue: (String) -> V
) : SerializeStrategy<K, V, Color, RBNode<K, V>, RBTree<K, V>>(
    serializeKey,
    serializeValue,
    deserializeKey,
    deserializeValue
) {
    override fun serializeNode(node: RBNode<K, V>): SerializableNode = SerializableNode(
        serializeKey(node.key),
        serializeValue(node.value),
        serializeMetadata(node.color),
        node.leftNode?.let { serializeNode(it) },
        node.rightNode?.let { serializeNode(it) }
    )

    override fun deserializeNode(node: SerializableNode): RBNode<K, V> {
        val rbnode: RBNode<K, V> = RBNode(deserializeKey(node.key), deserializeValue(node.value))
        if (node.metadata != "RED" && node.metadata != "BLACK") throw Exception("Wrong metadata. Impossible to deserialize")
        rbnode.color = deserializeMetadata(node.metadata)
        node.leftNode?.let { deserializeNode(it) }
        node.rightNode?.let { deserializeNode(it) }
        linkParents(rbnode)
        return rbnode
    }

    override fun serializeTree(tree: RBTree<K, V>, treeName: String) = SerializableTree(
        name = treeName,
        treeType = "RB",
        root = tree.root?.let { serializeNode(it) }
    )

    override fun deserializeTree(tree: SerializableTree): RBTree<K, V> {
        if (tree.treeType != "RB") throw Exception("Wrong tree type. Impossible to deserialize")
        val rbtree = RBTree<K, V>()
        rbtree.root = tree.root?.let { deserializeNode(it) }
        return rbtree
    }

    override fun deserializeMetadata(meta: String): Color = if (meta == "RED") Color.RED else Color.BLACK

    override fun serializeMetadata(meta: Color): String = if (meta == Color.RED) "RED" else "BLACK"

}
