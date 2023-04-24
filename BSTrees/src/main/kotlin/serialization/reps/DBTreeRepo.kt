package serialization.reps

import serialization.SerializableTree

interface DBTreeRepo {
    fun getTree(treeType: String, treeName: String): SerializableTree?

    fun setTree(serializableTree: SerializableTree)

    fun deleteTree(treeType: String, treeName: String)
}