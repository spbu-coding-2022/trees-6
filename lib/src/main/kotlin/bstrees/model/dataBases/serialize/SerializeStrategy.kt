package bstrees.model.dataBases.serialize

import bstrees.model.dataBases.serialize.types.SerializableNode
import bstrees.model.dataBases.serialize.types.SerializableTree
import bstrees.model.trees.Node

abstract class SerializeStrategy<K : Comparable<K>, V, NODE_TYPE: Node<K, V, NODE_TYPE>, TREE_TYPE>(
    val serializeKey: (K) -> String,
    val serializeValue: (V) -> String,
    val deserializeKey: (String) -> K,
    val deserializeValue: (String) -> V
) {

    abstract fun serializeNode(node: NODE_TYPE): SerializableNode

    abstract fun deserializeNode(node: SerializableNode): NODE_TYPE

    abstract fun serializeTree(tree: TREE_TYPE, treeName: String): SerializableTree

    abstract fun deserializeTree(tree: SerializableTree): TREE_TYPE

    protected fun linkParents(node: NODE_TYPE){
        node.leftNode?.parent = node
        node.rightNode?.parent = node
        node.leftNode?.let { linkParents(it) }
        node.rightNode?.let { linkParents(it) }
    }

}