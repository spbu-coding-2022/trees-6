abstract class BTree<K : Comparable<K>, V, NODE_TYPE : Node<K, V, NODE_TYPE>> {

    var root: NODE_TYPE? = null

    abstract fun insert(key: K, value: V)

    protected abstract fun add(node: NODE_TYPE)

    fun find(key: K): V? {
        var temp: NODE_TYPE? = root ?: return null
        while (temp != null) {
            if (temp.getKey() == key) return temp.getValue()
            temp = if (temp.getKey() > key) {
                temp.getLeftNode()
            } else {
                temp.getRightNode()
            }
        }
        return null
    }

    abstract fun delete(key: K)
}
