package trees.redBlack

import trees.Node

/**
 * A class representing an AVL binary search tree node.
 * The difference from the abstract node class is in the presence of a color which can be black of red
 *
 * @generic <K> the type of key stored in the tree. It must be comparable
 * @generic <V> the type of value stored in the tree
 */
class RBNode<K : Comparable<K>, V>(key: K, value: V) : Node<K, V, RBNode<K, V>>(key, value) {

    /**
     * An auxiliary enum class that contains the color of the node
     * It can be only black or red
     */
    enum class Color {
        BLACK,
        RED
    }

    /**
     * A variable that displays the current node color.
     * It is always black by default
     */
    internal var color = Color.BLACK
}
