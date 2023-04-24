package trees.avl

import trees.BTree

/**
 * A class representing an AVL binary search tree.
 * It maintains balance by checking heights of right and left sons. It has to differ by no more than one
 *
 * @generic <K> the type of key stored in the tree. It must be comparable
 * @generic <V> the type of value stored in the tree
 */
class AvlTree<K : Comparable<K>, V> : BTree<K, V, AvlNode<K, V>>() {

    /**
     * A balancer class providing balancing of the current node of the tree
     */
    private val balancer = AvlBalancer<K, V>()

    /**
     * Insert a node to the tree.
     * It is actually a wrapper for the add function. Necessary to implement recursion
     *
     * @param value the value to add
     * @param key the key under which the value is stored
     */
    override fun insert(key: K, value: V) {
        add(AvlNode(key, value))
    }

    /**
     * Add a node to the tree
     *
     * @param node the node to add
     */
    private fun add(node: AvlNode<K, V>) {
        //temp is needed to avoid possibility of changing the root
        val temp = this.root
        if (temp == null) this.root = node
        else if (temp.key == node.key) this.root?.value = node.value
        else {

            /**
             * Some kind of recursive implementation
             * Creating subtree to execute add function again
             * Then we return the node to the desired son
             */
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

         //Balancing this node before leaving recursion
        this.root = this.root?.let { balancer.balance(it) }
    }

    /**
     * Find a node with a minimum key in a tree
     * It is an auxiliary function for the delete function
     *
     * @param node the root of the tree where to find the minimum node
     */
    private fun findMin(node: AvlNode<K, V>): AvlNode<K, V> {
        //temp is needed to avoid possibility of changing the root
        val temp = node.leftNode
        return if (temp != null) findMin(temp) else node
    }

    /**
     * Find a node with a minimum key in a tree and delete it.
     * It is an auxiliary function for the delete function
     *
     * @param node the root of the tree where to remove the minimum node
     */
    private fun removeMin(node: AvlNode<K, V>): AvlNode<K, V>? {
        //temp is needed to avoid possibility of changing the root
        val temp = node.leftNode
        if (temp == null) return node.rightNode

        val tempRemovedMin = removeMin(temp)
        node.leftNode = tempRemovedMin
        tempRemovedMin?.parent = node
        return balancer.balance(node)
    }

    /**
     * Delete a value from the tree.
     *
     * @param key the key under which the value is stored
     */
    override fun delete(key: K) {
        //temp is needed to avoid possibility of changing the root
        val temp = this.root

        if (temp != null) {
            /**
             * Some kind of recursive implementation
             * Creating subtree to execute delete function again
             * Then we return the node to the desired son
             */
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
                //The left and right sons of the node being deleted
                val left = this.root?.leftNode
                val right = this.root?.rightNode

                if (right == null) {
                    left?.parent = this.root?.parent
                    this.root = left
                } else {
                    /**
                     * Temp is needed to avoid possibility of changing the root
                     *
                     * Finding the minimum node and giving her the correct links to the sons and the parent
                     */
                    val tempMin = findMin(right)
                    tempMin.rightNode = removeMin(right)
                    tempMin.rightNode?.parent = tempMin
                    tempMin.leftNode = left
                    tempMin.leftNode?.parent = tempMin

                    /**
                     * Giving correct links to the new node for the parent
                     * The son recognizes the parent, but the parent of the son does not
                     */
                    if (this.root?.parent?.leftNode == this.root) this.root?.parent?.leftNode = tempMin
                    else this.root?.parent?.rightNode = tempMin

                    tempMin.parent = this.root?.parent
                    /**
                     * Balancing th new node
                     *
                     * Everything at the bottom is already balanced,
                     * because it was done coming out of recursion
                     */
                    this.root = balancer.balance(tempMin)
                }

            }

            //Balancing this node before leaving recursion
            val nullCheck = this.root
            if (nullCheck != null) this.root = balancer.balance(nullCheck)

        }
    }
}
