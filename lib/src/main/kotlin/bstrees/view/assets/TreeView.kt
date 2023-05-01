package bstrees.view.assets

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
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bstrees.model.dataBases.serialize.types.SerializableNode
import bstrees.presenter.TreePresenter

@Composable
fun Tree(
    root: State<SerializableNode>,
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
    node: State<SerializableNode>,
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
fun TreeActionButtons(treePresenter: TreePresenter) {

    Column {
        Button(onClick = { treePresenter.addNode() }) {
            Text("Add Node")
        }

        Button(onClick = { treePresenter.deleteNode() }) {
            Text(text = "Delete Node")
        }
    }

}

@Composable
fun TreeView(
    treePresenter: TreePresenter,
    treeWidth: Double,
    treeHeight: Double,
    nodeSize: Double,
    xOffset: Double = 0.0,
    yOffset: Double = 0.0
) {
    val tree = treePresenter.tree ?: return
    Row {
        TreeActionButtons(treePresenter)
        Box(
            modifier = Modifier.height(treeHeight.dp).width(treeWidth.dp),
            contentAlignment = Alignment.Center
        ) {
            tree.root?.let {
                Tree(
                    mutableStateOf(tree.root),
                    nodeSize,
                    xOffset,
                    yOffset
                )
            }
        }
    }
}
