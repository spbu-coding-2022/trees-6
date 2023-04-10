abstract class BTree<K : Comparable<K>, V, NODE_TYPE : Node<K, V, NODE_TYPE>>{

    var root: NODE_TYPE? = null
        get() = field
        set(value){
            field = value
        }


    fun getHeight(): Int {
        return root?.getHeight() ?: 0
    }

    fun setHeight(newValue: Int) {
        this.root?.setHeight(newValue)
    }

    abstract fun insert(key: K, value: V)

    protected abstract fun add(node: NODE_TYPE)

    fun find(key: K): V? {
        var temp: NODE_TYPE? = root ?: return null
        while(temp != null){
            if(temp.getKey() == key)return temp.getValue()
            temp = if(temp.getKey() > key){
                temp.getLeftNode()
            } else{
                temp.getRightNode()
            }
        }
        return null
    }

    abstract fun delete(key: K)
}
