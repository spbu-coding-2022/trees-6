package bstrees.model.dataBases.reps

import bstrees.model.dataBases.serialize.types.SerializableTree

interface DBTreeRepo {
    fun getTree(treeName: String, treeType: String): SerializableTree?

    fun setTree(serializableTree: SerializableTree)

    fun deleteTree(treeName: String, treeType: String)
}