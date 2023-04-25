package bstrees.model.dataBases.serialize.strategies

import bstrees.model.dataBases.serialize.SerializeStrategy
import bstrees.model.dataBases.serialize.types.SerializableNode
import bstrees.model.dataBases.serialize.types.SerializableTree
import bstrees.model.trees.redBlack.RBNode
import bstrees.model.trees.redBlack.RBTree

class RbStrategy<K : Comparable<K>, V>(
    serializeKey: (K) -> String,
    serializeValue: (V) -> String,
    deserializeKey: (String) -> K,
    deserializeValue: (String) -> V
) : SerializeStrategy<K, V, RBNode<K, V>, RBTree<K, V>>(
    serializeKey,
    serializeValue,
    deserializeKey,
    deserializeValue
){
    override fun serializeNode(node: RBNode<K, V>): SerializableNode {
        TODO("Not yet implemented")
    }

    override fun deserializeNode(node: SerializableNode): RBNode<K, V> {
        TODO("Not yet implemented")
    }

    override fun serializeTree(tree: RBTree<K, V>, treeName: String): SerializableTree {
        TODO("Not yet implemented")
    }

    override fun deserializeTree(tree: SerializableTree): RBTree<K, V> {
        TODO("Not yet implemented")
    }

}