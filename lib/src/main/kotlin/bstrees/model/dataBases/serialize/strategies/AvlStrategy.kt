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
    deserializeValue: (String) -> V
) : SerializeStrategy<K, V, AvlNode<K, V>, AvlTree<K, V>>(
    serializeKey,
    serializeValue,
    deserializeKey,
    deserializeValue
) {
    override fun serializeNode(node: AvlNode<K, V>): SerializableNode {
        TODO("Not yet implemented")
    }

    override fun deserializeNode(node: SerializableNode): AvlNode<K, V> {
        TODO("Not yet implemented")
    }

    override fun serializeTree(tree: AvlTree<K, V>, treeName: String): SerializableTree {
        TODO("Not yet implemented")
    }

    override fun deserializeTree(tree: SerializableTree): AvlTree<K, V> {
        TODO("Not yet implemented")
    }

}