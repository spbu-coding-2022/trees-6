package balancers

import Node

abstract class Balancer<K : Comparable<K>, V, NODE_TYPE : Node<K, V, NODE_TYPE>>{
    fun leftRotate(node: NODE_TYPE): NODE_TYPE {
        val nodeParent = node.getParent()
        val temp = node.getRightNode()
        temp?.setParent(nodeParent)
        if(nodeParent?.getLeftNode() == node)nodeParent.setLeftNode(temp) else nodeParent?.setRightNode(temp)

        node.setRightNode(temp?.getLeftNode())
        node.getRightNode()?.setParent(node)

        temp?.setLeftNode(node)
        node.setParent(temp)

        node.updateHeight()
        node.updateSize()
        temp?.updateHeight()
        temp?.updateSize()

        return temp ?: node
    }

    fun rightRotate(node: NODE_TYPE): NODE_TYPE{
        val nodeParent = node.getParent()
        val temp = node.getLeftNode()
        temp?.setParent(nodeParent)
        if(nodeParent?.getLeftNode() == node)nodeParent.setLeftNode(temp) else nodeParent?.setRightNode(temp)

        node.setLeftNode(temp?.getRightNode())
        node.getLeftNode()?.setParent(node)

        temp?.setRightNode(node)
        node.setParent(temp)

        node.updateHeight()
        node.updateSize()
        temp?.updateHeight()
        temp?.updateSize()

        return temp ?: node
    }

    fun balanceFactory(node: NODE_TYPE): Int {
        return (node.getRightNode()?.getHeight() ?: 0) - (node.getLeftNode()?.getHeight() ?: 0)
    }

    abstract fun balance(node: NODE_TYPE): NODE_TYPE
}
