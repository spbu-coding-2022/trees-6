package utils

import bstrees.model.dataBases.NodeData
import bstrees.model.dataBases.TreeData

object TreeDataUtil {
    fun getTreeData(name: String, treeType: String): TreeData {
        return TreeData(
            name = name,
            treeType = treeType,
            keyType = "",
            valueType = "",
            NodeData(
                "1",
                "",
                "",
                0,
                0,
                NodeData(
                    "2",
                    "",
                    "",
                    0,
                    0,
                     NodeData(
                         "3",
                         "",
                         "",
                         0,
                         0,
                         null,
                         null
                     ),
                    NodeData(
                        "4",
                        "",
                        "",
                        0,
                        0,
                        null,
                        null
                    )
                ),
                NodeData(
                    "5",
                    "",
                    "",
                    0,
                    0,
                    NodeData(
                        "6",
                        "",
                        "",
                        0,
                        0,
                        null,
                        null
                    ),
                    NodeData(
                        "7",
                        "",
                        "",
                        0,
                        0,
                        null,
                        null
                    )
                )
            )
        )
    }

    fun isEqualsTrees(tree1: TreeData?, tree2: TreeData?): Boolean{
        if(tree1 == null && tree2 == null)return true
        if(tree1 == null || tree2 == null)return false
        return tree1.name == tree2.name && tree1.treeType == tree2.treeType &&
                tree1.keyType == tree2.keyType && tree1.valueType == tree2.valueType &&
                isEqualsNodes(tree1.root, tree2.root)
    }

    private fun isEqualsNodes(node1: NodeData?, node2: NodeData?): Boolean{
        if(node1 == null && node2 == null)return true
        if(node1 == null || node2 == null)return false
        return node1.key == node2.key && node1.value == node2.value &&
                node1.metadata == node2.metadata &&
                node1.posX == node2.posX && node1.posY == node2.posY &&
                isEqualsNodes(node1.leftNode, node2.leftNode) &&
                isEqualsNodes(node1.rightNode, node2.rightNode)
    }
}