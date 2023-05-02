package app.view.treeView

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import bstrees.model.dataBases.NodeData

@Composable
fun Tree(
    node: State<NodeData>,
) {

    val nodeCoords = remember { mutableStateOf(Pair(
        node.value.posX.toFloat(), node.value.posY.toFloat())) }

    node.value.leftNode?.let { leftNode ->
        Edge(node, mutableStateOf(leftNode))
    }
    node.value.rightNode?.let { rightNode ->
        Edge(node, mutableStateOf(rightNode))
    }
    node.value.leftNode?.let { leftNode ->
        Tree(mutableStateOf(leftNode))
    }
    node.value.rightNode?.let { rightNode ->
        Tree(mutableStateOf(rightNode))
    }

    Node(node, nodeCoords)
}
