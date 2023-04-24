abstract class BTree<K : Comparable<K>, V, NODE_TYPE : Node<K, V, NODE_TYPE>> {

    internal var root: NODE_TYPE? = null
    abstract fun insert(key: K, value: V)

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

    abstract fun delete(key: K)
}
