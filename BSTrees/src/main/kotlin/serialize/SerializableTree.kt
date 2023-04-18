package serialize

import kotlinx.serialization.Serializable
import redBlackTree.RBNode.Color

@Serializable
class SerializableTree<T>(
    val name: String,
    val nodes: Collection<SerializableNode<T>>
)

@Serializable
class SerializableAvlTree(
    val name: String,
    val nodes: List<AvlNodeMetaData>
)


@Serializable
class SerializableNode<T>(
    val key: Int,
    val value: Int,
    val metaData: T,
    val leftSonKey: Int? = null,
    val rightSonKey: Int? = null,
    val parentKey: Int? = null
)

@Serializable
class RBNodeMetaData(
    val key: Int,
    val value: Int,
    val height: Int,
    val size: Int,
    val color: Color,
    val leftSonKey: Int? = null,
    val rightSonKey: Int? = null,
    val parentKey: Int? = null
)

@Serializable
class AvlNodeMetaData(
    val key: String,
    val value: String,
    val height: Int,
    val size: Int,
    val leftSonKey: String? = null,
    val rightSonKey: String? = null,
    val parentKey: String? = null
)

@Serializable
class BSNodeMetaData(
    val height: Int,
    val size: Int
)