package trees.binarySearch

import trees.Balancer

/**
 * A class representing a randomized binary search tree balancer.
 * Provides all the necessary functions for rotating the tree
 *
 * @generic <K> the type of key stored in the tree. It must be comparable
 * @generic <V> the type of value stored in the tree
 */
internal class BSBalancer<K : Comparable<K>, V> : Balancer<K, V, BSNode<K, V>>() {

    /**
     * Do a right rotate around this node with updating size
     *
     * @param node the node around which the rotation is done
     */
    internal fun bsRightRotate(node: BSNode<K, V>): BSNode<K, V> {
        val temp = rightRotate(node)
        temp.leftNode?.updateSize()
        temp.rightNode?.updateSize()
        temp.updateSize()
        return temp
    }

    /**
     * Do a left rotate around this node with updating size
     *
     * @param node the node around which the rotation is done
     */
    internal fun bsLeftRotate(node: BSNode<K, V>): BSNode<K, V> {
        val temp = leftRotate(node)
        temp.leftNode?.updateSize()
        temp.rightNode?.updateSize()
        temp.updateSize()
        return temp
    }

}
