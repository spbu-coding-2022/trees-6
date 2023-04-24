package avlTree

import BTree

class AvlTree<K : Comparable<K>, V> : BTree<K, V, AvlNode<K, V>>() {

    private val balancer = AvlBalancer<K, V>()

    override fun insert(key: K, value: V) {
        add(AvlNode(key, value))
    }

    private fun add(node: AvlNode<K, V>) {
        val temp = this.root
        if (temp == null) this.root = node
        else if (temp.key == node.key) this.root?.value = node.value
        else {

            val subTree = AvlTree<K, V>()

            if (node.key < temp.key) {
                subTree.root = temp.leftNode
                subTree.add(node)
                subTree.root?.parent = this.root
                this.root?.leftNode = subTree.root
            } else if (node.key > temp.key) {
                subTree.root = temp.rightNode
                subTree.add(node)
                subTree.root?.parent = this.root 
                this.root?.rightNode = subTree.root
            }

        }

        this.root = this.root?.let { balancer.balance(it) }
    }

    private fun findMin(node: AvlNode<K, V>): AvlNode<K, V> {
        val temp = node.leftNode
        return if (temp != null) findMin(temp) else node
    }

    private fun removeMin(node: AvlNode<K, V>): AvlNode<K, V>? {
        val temp = node.leftNode
        if (temp == null) return node.rightNode
        val tempRemovedMin = removeMin(temp)
        node.leftNode = tempRemovedMin
        tempRemovedMin?.parent = node
        return balancer.balance(node)
    }

    override fun delete(key: K) {

        val temp = this.root

        if (temp != null) {

            val subTree = AvlTree<K, V>()

            if (key < temp.key) {

                subTree.root = temp.leftNode
                subTree.delete(key)
                subTree.root?.parent = this.root
                this.root?.leftNode = subTree.root

            } else if (key > temp.key) {

                subTree.root = temp.rightNode
                subTree.delete(key)
                subTree.root?.parent = this.root
                this.root?.rightNode = subTree.root

            } else {

                val leftNode = this.root?.leftNode
                val rightNode = this.root?.rightNode

                if (rightNode == null) {
                    leftNode?.parent = this.root?.parent
                    this.root = leftNode
                } else {
                    val tempMin: AvlNode<K, V> = findMin(rightNode)
                    tempMin.rightNode = removeMin(rightNode)
                    tempMin.rightNode?.parent = tempMin
                    tempMin.leftNode = leftNode
                    tempMin.leftNode?.parent = tempMin
                    if (this.root?.parent?.leftNode == this.root) this.root?.parent?.leftNode = tempMin
                    else this.root?.parent?.rightNode = tempMin
                    tempMin.parent = this.root?.parent
                    this.root = balancer.balance(tempMin)
                }

            }

            val nullCheck = this.root
            if (nullCheck != null) this.root = balancer.balance(nullCheck)

        }
    }
}
