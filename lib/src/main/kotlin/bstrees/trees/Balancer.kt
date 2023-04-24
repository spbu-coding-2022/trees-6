package bstrees.trees

/**
 * An abstract class representing a binary search tree.
 *
 * @generic <K> the type of key stored in the tree. It must be comparable
 * @generic <V> the type of value stored in the tree
 * @generic <NODE_TYPE> the type of node used in the tree
 */
abstract class Balancer<K : Comparable<K>, V, NODE_TYPE : Node<K, V, NODE_TYPE>> {
    /**
     * Do a left rotate around this node
     *
     * @param node the node around which the rotation is done
     */
     protected fun leftRotate(node: NODE_TYPE): NODE_TYPE {

        val nodeParent = node.parent
        val temp = node.rightNode

        temp?.parent = nodeParent
        //We should change parent's son. It depends on what son is our node
        if (nodeParent?.leftNode == node) nodeParent.leftNode = temp else nodeParent?.rightNode = temp

        node.rightNode = temp?.leftNode
        node.rightNode?.parent = node

        temp?.leftNode = node
        node.parent = temp

        return temp ?: node
    }

    /**
     * Do a right rotate around this node
     *
     * @param node the node around which the rotation is done
     */
    protected fun rightRotate(node: NODE_TYPE): NODE_TYPE {

        val nodeParent = node.parent
        val temp = node.leftNode

        temp?.parent = nodeParent
        //We should change parent's son. It depends on what son is our node
        if (nodeParent?.leftNode == node) nodeParent.leftNode = temp else nodeParent?.rightNode = temp

        node.leftNode = temp?.rightNode
        node.leftNode?.parent = node

        temp?.rightNode = node
        node.parent = temp

        return temp ?: node
    }
}
