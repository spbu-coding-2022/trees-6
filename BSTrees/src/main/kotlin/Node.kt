import kotlin.math.max

abstract class Node<K : Comparable<K>, V, NODE_TYPE : Node<K, V, NODE_TYPE>>(private val key: K, private var value: V) {

    private var parent: NODE_TYPE? = null
    private var leftNode: NODE_TYPE? = null
    private var rightNode: NODE_TYPE? = null
    private var height = 1
    private var size = 1

    fun getSize() = size

    fun setSize(newSize: Int){
        this.size = newSize
    }

    fun getKey() = this.key

    fun getValue() = this.value

    fun setValue(newValue: V) {
        this.value = newValue
    }

    fun getParent() = parent

    fun setParent(newNode: NODE_TYPE?){
        parent = newNode
    }

    fun getLeftNode() = leftNode

    fun setLeftNode(newNode: NODE_TYPE?){
        leftNode = newNode
    }

    fun getRightNode() = rightNode

    fun setRightNode(newNode: NODE_TYPE?){
        rightNode = newNode
    }

    fun getHeight() = this.height

    fun setHeight(newValue: Int) {
        this.height = newValue
    }

    fun updateHeight() {
        val leftHeight = leftNode?.getHeight() ?: 0
        val rightHeight = rightNode?.getHeight() ?: 0
        setHeight(max(leftHeight, rightHeight) + 1)
    }

    fun updateSize(){
        val leftSize = getLeftNode()?.getHeight() ?: 0
        val rightSize = getRightNode()?.getHeight() ?: 0
        setHeight(leftSize + rightSize + 1)
    }

}
