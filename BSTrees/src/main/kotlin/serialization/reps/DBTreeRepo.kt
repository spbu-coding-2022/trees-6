package serialization.reps

import serialization.SerializableTree

interface DBTreeRepo {
    fun getTree(typeTree: String, treeName: String): SerializableTree?

    fun setTree(serializableTree: SerializableTree)

    fun deleteTree(serializableTree: SerializableTree)
}