package bstrees.dataBases.reps

import bstrees.dataBases.SerializableTree

interface DBTreeRepo {
    fun getTree(treeType: String, treeName: String): SerializableTree?

    fun setTree(serializableTree: SerializableTree)

    fun deleteTree(treeType: String, treeName: String)
}