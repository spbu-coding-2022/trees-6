package bstrees.model.trees

/**
 * An abstract class representing a binary search tree.
 *
 * @generic <K> the type of key stored in the tree. It must be comparable
 * @generic <V> the type of value stored in the tree
 * @generic <NODE_TYPE> the type of node used in the tree
 */
abstract class BTree<K : Comparable<K>, V, NODE_TYPE : Node<K, V, NODE_TYPE>> {
    /**
     * The root node of the tree.
     */
    var root: NODE_TYPE? = null
        internal set

    /**
     * Insert a node to the tree.
     *
     * @param value the value to add
     * @param key the key under which the value is stored
     */
    abstract fun insert(key: K, value: V)

    /**
     * Looking for a value in the tree by its key.
     *
     * @param key the key by which the search is performed
     * @return value if it is in the tree, false otherwise
     */
    fun find(key: K): V? {
        var temp: NODE_TYPE? = root ?: return null
        while (temp != null) {
            if (temp.key == key) return temp.value
            temp = if (temp.key > key) {
                temp.leftNode
            } else {
                temp.rightNode
            }
        }
        return null
    }

    /**
     * Delete a value from the tree.
     *
     * @param key the key under which the value is stored
     */
    abstract fun delete(key: K)
}
