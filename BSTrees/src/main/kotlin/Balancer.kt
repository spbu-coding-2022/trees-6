abstract class Balancer<K : Comparable<K>, V, NODE_TYPE : Node<K, V, NODE_TYPE>> {
     protected fun leftRotate(node: NODE_TYPE): NODE_TYPE {
        val nodeParent = node.parent
        val temp = node.rightNode
        temp?.parent = nodeParent
        if (nodeParent?.leftNode == node) nodeParent.leftNode = temp else nodeParent?.rightNode = temp

        node.rightNode = temp?.leftNode
        node.rightNode?.parent = node

        temp?.leftNode = node
        node.parent = temp

        return temp ?: node
    }

    protected fun rightRotate(node: NODE_TYPE): NODE_TYPE {
        val nodeParent = node.parent
        val temp = node.leftNode
        temp?.parent = nodeParent
        if (nodeParent?.leftNode == node) nodeParent.leftNode = temp else nodeParent?.rightNode = temp

        node.leftNode = temp?.rightNode
        node.leftNode?.parent = node

        temp?.rightNode = node
        node.parent = temp

        return temp ?: node
    }
}
