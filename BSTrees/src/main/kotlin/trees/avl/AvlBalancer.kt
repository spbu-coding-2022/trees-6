package trees.avl

import trees.Balancer

/**
 * A class representing an AVL binary search tree balancer.
 * Provides all the necessary functions for balancing the tree
 *
 * @generic <K> the type of key stored in the tree. It must be comparable
 * @generic <V> the type of value stored in the tree
 */
internal class AvlBalancer<K : Comparable<K>, V> : Balancer<K, V, AvlNode<K, V>>() {

    /**
     * Provides information about the need for balancing by calculating the imbalance of the tree
     *
     * @param node node that has been checked
     */
    private fun balanceFactor(node: AvlNode<K, V>): Int {
        return (node.rightNode?.height ?: 0) - (node.leftNode?.height ?: 0)
    }

    /**
     * Do a right rotate around this node with updating height
     *
     * @param node the node around which the rotation is done
     */
    private fun avlRightRotate(node: AvlNode<K, V>): AvlNode<K, V> {
        val temp = rightRotate(node)
        temp.leftNode?.updateHeight()
        temp.rightNode?.updateHeight()
        temp.updateHeight()
        return temp
    }

    /**
     * Do a left rotate around this node with updating height
     *
     * @param node the node around which the rotation is done
     */
    private fun avlLeftRotate(node: AvlNode<K, V>): AvlNode<K, V> {
        val temp = leftRotate(node)
        temp.leftNode?.updateHeight()
        temp.rightNode?.updateHeight()
        temp.updateHeight()
        return temp
    }

    /**
     * Balance current node
     *
     * @param node node that are balanced
     */
    internal fun balance(node: AvlNode<K, V>): AvlNode<K, V> {

        node.updateHeight()
        val bf = balanceFactor(node)

        if (bf == 2) {

            val temp = node.rightNode
            if (temp != null) {
                if (balanceFactor(temp) < 0) {
                    node.rightNode = avlRightRotate(temp)//big left rotate
                }
            }

            return avlLeftRotate(node)
        }

        if (bf == -2) {

            val temp = node.leftNode
            if (temp != null) {
                if (balanceFactor(temp) > 0) {
                    node.leftNode = avlLeftRotate(temp)//big right rotate
                }
            }

            return avlRightRotate(node)
        }

        return node
    }

}
