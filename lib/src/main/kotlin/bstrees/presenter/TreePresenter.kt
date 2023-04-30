package bstrees.presenter

import bstrees.model.dataBases.reps.DBTreeRepo
import bstrees.model.dataBases.serialize.types.SerializableTree

class TreePresenter(private val db: DBTreeRepo) {
    var tree: SerializableTree? = null
        private set

    fun loadTree(treeName: String, treeType: String) {
        tree = db.getTree(treeName, treeType)
    }

    fun createTree(treeName: String, treeType: String) {
        tree = SerializableTree(treeName, treeType, null)
        tree?.let { db.setTree(it) }
    }

    fun addNode() {
        TODO()
    }

    fun deleteNode() {
        TODO()
    }
}