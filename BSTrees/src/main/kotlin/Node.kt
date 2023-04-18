abstract class Node<K : Comparable<K>, V, NODE_TYPE : Node<K, V, NODE_TYPE>>(private val key: K, private var value: V) {

    private var parent: NODE_TYPE? = null
    private var leftNode: NODE_TYPE? = null
    private var rightNode: NODE_TYPE? = null

    fun getKey() = this.key

    fun getValue() = this.value

    fun setValue(newValue: V) {
        this.value = newValue
    }

    fun getParent() = parent

    fun setParent(newNode: NODE_TYPE?) {
        parent = newNode
    }

    fun getLeftNode() = leftNode

    fun setLeftNode(newNode: NODE_TYPE?) {
        leftNode = newNode
    }

    fun getRightNode() = rightNode

    fun setRightNode(newNode: NODE_TYPE?) {
        rightNode = newNode
    }

}
