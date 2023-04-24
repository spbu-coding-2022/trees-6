package serialization.reps

import serialization.SerializableTree

interface DBTreeRepo {
    fun getTree(treeName: String, treeType: String): SerializableTree?

    fun setTree(serializableTree: SerializableTree)

    fun deleteTree(treeName: String, treeType: String)
}