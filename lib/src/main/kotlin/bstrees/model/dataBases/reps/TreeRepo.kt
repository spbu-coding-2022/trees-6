package bstrees.model.dataBases.reps

import bstrees.model.dataBases.TreeData

interface TreeRepo {
    fun getTree(treeName: String, treeType: String): TreeData?

    fun setTree(treeData: TreeData)

    fun deleteTree(treeName: String, treeType: String)
}