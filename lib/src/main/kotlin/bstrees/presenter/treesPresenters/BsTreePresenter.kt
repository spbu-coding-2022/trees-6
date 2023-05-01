package bstrees.presenter.treesPresenters

import bstrees.model.dataBases.serialize.strategies.BsStrategy
import bstrees.model.dataBases.serialize.strategies.intToString
import bstrees.model.dataBases.serialize.strategies.stringToInt
import bstrees.model.dataBases.serialize.strategies.stringToString
import bstrees.model.dataBases.serialize.types.SerializableTree

object BsTreePresenter {
    fun addNode(tree: SerializableTree, key: String, value: String): SerializableTree{
        return when(tree.keyType to tree.valueType){
            "String" to "String" -> {
                val strategy = BsStrategy(::stringToString, ::stringToString, ::stringToString, ::stringToString, tree.keyType, tree.valueType)
                val bTree = strategy.deserializeTree(tree)
                bTree.insert(key, value)
                strategy.serializeTree(bTree, tree.name)
            }
            "Int" to "Int" -> {
                val strategy = BsStrategy(::intToString, ::intToString, ::stringToInt, ::stringToInt, tree.keyType, tree.valueType)
                val bTree = strategy.deserializeTree(tree)
                bTree.insert(key.toInt(), value.toInt())
                strategy.serializeTree(bTree, tree.name)
            }
            "String" to "Int" -> {
                val strategy = BsStrategy(::stringToString, ::intToString, ::stringToString, ::stringToInt, tree.keyType, tree.valueType)
                val bTree = strategy.deserializeTree(tree)
                bTree.insert(key, value.toInt())
                strategy.serializeTree(bTree, tree.name)
            }
            "Int" to "String" -> {
                val strategy = BsStrategy(::intToString, ::stringToString, ::stringToInt, ::stringToString, tree.keyType, tree.valueType)
                val bTree = strategy.deserializeTree(tree)
                bTree.insert(key.toInt(), value)
                strategy.serializeTree(bTree, tree.name)
            }
            else -> { throw Exception("keyValue type error") }
        }
    }

    fun deleteNode(tree: SerializableTree, key: String): SerializableTree {
        return when(tree.keyType to tree.valueType){
            "String" to "String" -> {
                val strategy = BsStrategy(::stringToString, ::stringToString, ::stringToString, ::stringToString, tree.keyType, tree.valueType)
                val bTree = strategy.deserializeTree(tree)
                bTree.delete(key)
                strategy.serializeTree(bTree, tree.name)
            }
            "Int" to "Int" -> {
                val strategy = BsStrategy(::intToString, ::intToString, ::stringToInt, ::stringToInt, tree.keyType, tree.valueType)
                val bTree = strategy.deserializeTree(tree)
                bTree.delete(key.toInt())
                strategy.serializeTree(bTree, tree.name)
            }
            "String" to "Int" -> {
                val strategy = BsStrategy(::stringToString, ::intToString, ::stringToString, ::stringToInt, tree.keyType, tree.valueType)
                val bTree = strategy.deserializeTree(tree)
                bTree.delete(key)
                strategy.serializeTree(bTree, tree.name)
            }
            "Int" to "String" -> {
                val strategy = BsStrategy(::intToString, ::stringToString, ::stringToInt, ::stringToString, tree.keyType, tree.valueType)
                val bTree = strategy.deserializeTree(tree)
                bTree.delete(key.toInt())
                strategy.serializeTree(bTree, tree.name)
            }
            else -> { throw Exception("keyValue type error") }
        }
    }
}