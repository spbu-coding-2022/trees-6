abstract class Node<K : Comparable<K>, V, NODE_TYPE : Node<K, V, NODE_TYPE>>(val key: K, var value: V) {
    internal var parent: NODE_TYPE? = null
    internal var leftNode: NODE_TYPE? = null
    internal var rightNode: NODE_TYPE? = null
}
