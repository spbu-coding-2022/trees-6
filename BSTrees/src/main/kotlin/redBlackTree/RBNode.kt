package redBlackTree

import Node

class RBNode<K : Comparable<K>, V>(key: K, value: V) : Node<K, V, RBNode<K, V>>(key, value) {

    enum class Color {
        BLACK,
        RED
    }

    internal var color = Color.BLACK
}
