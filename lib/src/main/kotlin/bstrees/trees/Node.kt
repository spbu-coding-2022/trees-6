package bstrees.trees

/**
 * An abstract class representing a binary search tree node.
 * It's constructed with key and value and contains parent, left and right sons
 *
 * @generic <K> the type of key stored in the tree. It must be comparable
 * @generic <V> the type of value stored in the tree
 * @generic <NODE_TYPE> the type of node used in the tree
 */
abstract class Node<K : Comparable<K>, V, NODE_TYPE : Node<K, V, NODE_TYPE>>(val key: K, var value: V) {
    /**
     * The parent of the node
     */
    var parent: NODE_TYPE? = null
        internal set

    /**
     * The left son of the node
     */
    var leftNode: NODE_TYPE? = null
        internal set

    /**
     * The right son of the node
     */
    var rightNode: NODE_TYPE? = null
        internal set
}
