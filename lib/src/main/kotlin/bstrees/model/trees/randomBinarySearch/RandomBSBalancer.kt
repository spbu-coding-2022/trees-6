package bstrees.model.trees.randomBinarySearch

import bstrees.model.trees.Balancer

/**
 * A class representing a randomized binary search tree balancer.
 * Provides all the necessary functions for rotating the tree
 *
 * @generic <K> the type of key stored in the tree. It must be comparable
 * @generic <V> the type of value stored in the tree
 */
internal class RandomBSBalancer<K : Comparable<K>, V> : Balancer<K, V, RandomBSNode<K, V>>() {

    /**
     * Do a right rotate around this node with updating size
     *
     * @param node the node around which the rotation is done
     */
    internal fun bsRightRotate(node: RandomBSNode<K, V>): RandomBSNode<K, V> {
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
    internal fun bsLeftRotate(node: RandomBSNode<K, V>): RandomBSNode<K, V> {
        val temp = leftRotate(node)
        temp.leftNode?.updateSize()
        temp.rightNode?.updateSize()
        temp.updateSize()
        return temp
    }

}
