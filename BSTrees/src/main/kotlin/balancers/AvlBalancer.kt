package balancers

import avlTree.AvlNode


class AvlBalancer<K : Comparable<K>, V> : Balancer<K, V, AvlNode<K, V>>() {

    override fun balance(node: AvlNode<K, V>): AvlNode<K, V> {

        node.updateHeight()
        val bf = balanceFactor(node)

        if (bf == 2) {

            val temp = node.getRightNode()
            if (temp != null) {
                if (balanceFactor(temp) < 0) {
                    node.setRightNode(rightRotate(temp))
                }
            }

            return leftRotate(node)
        }

        if (bf == -2) {

            val temp = node.getLeftNode()
            if (temp != null) {
                if (balanceFactor(temp) > 0) {
                    node.setLeftNode(leftRotate(temp))
                }
            }

            return leftRotate(node)
        }

        return node
    }

}
