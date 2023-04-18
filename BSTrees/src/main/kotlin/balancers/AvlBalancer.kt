package balancers

import avlTree.AvlNode


class AvlBalancer<K : Comparable<K>, V> : Balancer<K, V, AvlNode<K, V>>() {

    private fun balanceFactor(node: AvlNode<K, V>): Int {
        return (node.getRightNode()?.getHeight() ?: 0) - (node.getLeftNode()?.getHeight() ?: 0)
    }

    private fun avlRightRotate(node: AvlNode<K, V>): AvlNode<K, V> {
        val temp = rightRotate(node)
        temp.getLeftNode()?.updateHeight()
        temp.getRightNode()?.updateHeight()
        temp.updateHeight()
        return temp
    }

    private fun avlLeftRotate(node: AvlNode<K, V>): AvlNode<K, V> {
        val temp = leftRotate(node)
        temp.getLeftNode()?.updateHeight()
        temp.getRightNode()?.updateHeight()
        temp.updateHeight()
        return temp
    }

    override fun balance(node: AvlNode<K, V>): AvlNode<K, V> {

        node.updateHeight()
        val bf = balanceFactor(node)

        if (bf == 2) {

            val temp = node.getRightNode()
            if (temp != null) {
                if (balanceFactor(temp) < 0) {
                    node.setRightNode(avlRightRotate(temp))
                }
            }

            return avlLeftRotate(node)
        }

        if (bf == -2) {

            val temp = node.getLeftNode()
            if (temp != null) {
                if (balanceFactor(temp) > 0) {
                    node.setLeftNode(avlLeftRotate(temp))
                }
            }

            return avlRightRotate(node)
        }

        return node
    }

}
