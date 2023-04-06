package balancers

import Node

abstract class Balancer<K : Comparable<K>, V, NODE_TYPE : Node<K, V, NODE_TYPE>>{
    protected fun leftRotate(node: NODE_TYPE?): NODE_TYPE? {
        val temp = node?.getLeftNode()
        node?.setLeftNode(temp?.getRightNode())
        temp?.setRightNode(node)
        node?.updateHeight()
        temp?.updateHeight()
        return temp
    }

    protected fun rightRotate(node: NODE_TYPE?): NODE_TYPE? {
        val temp = node?.getRightNode()
        node?.setRightNode(temp?.getLeftNode())
        temp?.setLeftNode(node)
        node?.updateHeight()
        temp?.updateHeight()
        return temp
    }

    protected fun balanceFactory(node: NODE_TYPE?): Int {
        return (node?.getRightNode()?.getHeight() ?: 0) - (node?.getLeftNode()?.getHeight() ?: 0)
    }

    abstract fun balance(node: NODE_TYPE?)
}
