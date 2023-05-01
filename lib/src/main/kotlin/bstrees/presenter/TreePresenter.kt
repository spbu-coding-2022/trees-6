package bstrees.presenter

import bstrees.model.dataBases.reps.DBTreeRepo
import bstrees.model.dataBases.serialize.types.SerializableTree
import bstrees.presenter.treesPresenters.AvlTreePresenter
import bstrees.presenter.treesPresenters.BsTreePresenter
import bstrees.presenter.treesPresenters.RbTreePresenter


class TreePresenter(private val db: DBTreeRepo) {
    lateinit var tree: SerializableTree
        private set

    fun loadTree(treeName: String, treeType: String) {
        tree = db.getTree(treeName, treeType) ?: throw Exception("There is no tree with that name: $treeName")
    }

    fun createTree(treeName: String, treeType: String, keyType: String, valueType: String) {
        tree = SerializableTree(treeName, treeType, keyType, valueType, null)
        db.setTree(tree)
    }

    fun addNode(key: String, value: String) {
        tree = when (tree.treeType) {
            "AVL" -> {
                AvlTreePresenter.addNode(tree, key, value)
            }
            "BS" -> {
                BsTreePresenter.addNode(tree, key, value)
            }
            "RB" -> {
                RbTreePresenter.addNode(tree, key, value)
            }
            else -> {
                throw Exception("treeType error")
            }
        }
    }


    fun deleteNode(key: String) {
        tree = when (tree.treeType) {
            "AVL" -> {
                AvlTreePresenter.deleteNode(tree, key)
            }
            "BS" -> {
                BsTreePresenter.deleteNode(tree, key)
            }
            "RB" -> {
                RbTreePresenter.deleteNode(tree, key)
            }
            else -> {
                throw Exception("treeType error")
            }
        }
    }
}