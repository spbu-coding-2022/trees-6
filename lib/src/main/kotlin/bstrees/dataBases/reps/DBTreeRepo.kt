package bstrees.dataBases.reps

import bstrees.dataBases.SerializableTree

interface DBTreeRepo {
    fun getTree(typeTree: String, treeName: String): SerializableTree?

    fun setTree(serializableTree: SerializableTree)

    fun deleteTree(typeTree: String, treeName: String)
}