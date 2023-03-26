package avlTree

import Node

class AvlNode<K: Comparable<K>, V>(key: K, value: V): Node<K, V, AvlNode<K, V>>(key, value)
