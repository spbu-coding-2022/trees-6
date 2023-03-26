abstract class Node<K: Comparable<K>, V, NODE_TYPE: Node<K, V, NODE_TYPE>>(private val key: K, private var value: V){
    private var height = 1

    fun getKey() = this.key

    fun getValue() = this.value

    fun setValue(newValue: V){
        this.value = newValue
    }

    fun getHeight() = this.height

    fun setHeight(newValue: Int){
        this.height = newValue
    }
}
