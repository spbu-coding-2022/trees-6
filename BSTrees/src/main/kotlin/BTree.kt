import kotlin.math.max

abstract class BTree<K : Comparable<K>, V, NODE_TYPE : Node<K, V, NODE_TYPE>, TREE_TYPE : BTree<K, V, NODE_TYPE, TREE_TYPE>>(
    private var root: NODE_TYPE?
) {

    private var leftBTree: TREE_TYPE? = null
    private var rightBTree: TREE_TYPE? = null

    fun getRoot() = this.root

    fun setRoot(newRoot: NODE_TYPE?) {
        this.root = newRoot
    }

    fun getLeftTree() = this.leftBTree

    fun setLeftTree(newValue: TREE_TYPE?) {
        this.leftBTree = newValue
    }

    fun getRightTree() = this.rightBTree

    fun setRightTree(newValue: TREE_TYPE?) {
        this.rightBTree = newValue
    }

    fun getHeight(): Int {
        return this.root?.getHeight() ?: 0
    }

    fun setHeight(newValue: Int) {
        this.root?.setHeight(newValue)
    }

    fun updateHeight() {
        val leftHeight = this.getLeftTree()?.getHeight() ?: 0
        val rightHeight = this.getRightTree()?.getHeight() ?: 0
        setHeight(max(leftHeight, rightHeight) + 1)
    }

    abstract fun add(node: NODE_TYPE)

    fun find(key: K): V? {
        val temp = this.root ?: return null
        if (temp.getKey() == key) return temp.getValue()
        return if (temp.getKey() > key) {
            this.getLeftTree()?.find(key)
        } else {
            this.getRightTree()?.find(key)
        }
    }

    abstract fun delete(key: K)
}
