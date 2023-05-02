package app.view.assets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.presenter.LayoutPresenter
import bstrees.model.dataBases.NodeData
import app.presenter.TreePresenter

@Composable
fun Tree(
    root: State<NodeData>,
    nodeSize: Double,
    xOffset: Double,
    yOffset: Double,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Node(root, nodeSize)

        val checkLeftSon = root.value.leftNode
        checkLeftSon?.let { leftSon ->
            val temp = remember { mutableStateOf(leftSon) }
            Tree(
                temp,
                nodeSize,
                xOffset - nodeSize / 2,
                yOffset + nodeSize,
                modifier = Modifier.absoluteOffset(x = (xOffset - nodeSize / 1.5).dp, y = (yOffset + nodeSize).dp)
            )
        }

        val checkRightSon = root.value.rightNode
        checkRightSon?.let { rightSon ->
            val temp = remember { mutableStateOf(rightSon) }
            Tree(
                temp,
                nodeSize,
                xOffset + nodeSize / 2,
                yOffset + nodeSize,
                modifier = Modifier.absoluteOffset(x = (xOffset + nodeSize / 1.5).dp, y = (yOffset + nodeSize).dp)
            )
        }
    }
}

@Composable
fun Node(
    node: State<NodeData>,
    nodeSize: Double,
) {

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(
                color = if (node.value.metadata == "RED") Color.Red
                else if (node.value.metadata == "BLACK") Color.Black
                else Color.Magenta,
                shape = CircleShape
            )
            .width(nodeSize.dp)
            .height(nodeSize.dp)
            .border(
                width = 1.dp,
                color = Color.Blue,
                shape = CircleShape
            )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Key: ${node.value.key}",
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = if (((nodeSize / 4).toInt()) > 20) 20.sp else (nodeSize / 4).toInt().sp
            )
            Text(
                text = "Value: ${node.value.value}",
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = if (((nodeSize / 4).toInt()) > 20) 20.sp else (nodeSize / 4).toInt().sp
            )
        }
    }

}

@Composable
fun TreeActionButtons(
    addNode: () -> Unit,
    deleteNode: () -> Unit,
) {

    Row {
        Button(onClick = {
            addNode()
        }) {
            Text("Add Node")
        }

        Spacer(modifier = Modifier.width(30.dp))

        Button(onClick = {
            deleteNode()
        }) {
            Text(text = "Delete Node")
        }
    }

}

@Composable
fun TreeView(
    treePresenter: TreePresenter,
    addNode: () -> Unit,
    deleteNode: () -> Unit,
) {
    val tree = treePresenter.tree
    LayoutPresenter.setTreeLayout(tree, 800, 800)
    Column {
        TreeActionButtons(addNode, deleteNode)

        Box(
            modifier = Modifier.height(800.dp).width(800.dp),
            contentAlignment = Alignment.Center
        ) {
            tree.root?.let { root ->
                Tree(
                    mutableStateOf(root),
                    50.0,
                    0.0,
                    0.0,
                )
            }
        }
    }
}
