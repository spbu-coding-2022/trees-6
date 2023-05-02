package app.view.assets

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import app.presenter.LayoutPresenter
import app.presenter.TreePresenter
import bstrees.model.dataBases.NodeData
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp


const val NODE_SIZE = 30
const val WINDOW_SIZE = 800


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

    TreeNode(node, nodeCoords)
}


@Composable
fun TreeNode(node: State<NodeData>, nodeCoords: MutableState<Pair<Float, Float>>) {
    node.value.posX = nodeCoords.value.first.toInt()
    node.value.posY = nodeCoords.value.second.toInt()
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .offset { IntOffset(nodeCoords.value.first.toInt(), nodeCoords.value.second.toInt()) }
            .background(Color.Red, CircleShape)
            .size(NODE_SIZE.dp)
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    nodeCoords.value = Pair(
                        nodeCoords.value.first + dragAmount.x,
                        nodeCoords.value.second + dragAmount.y
                    )
                    if (change.positionChange() != Offset.Zero) change.consume()
                }
            }
            .background(
                color = if (node.value.metadata == "RED") Color.Red
                else if (node.value.metadata == "BLACK") Color.Black
                else Color.Magenta,
                shape = CircleShape
            )
            .width(NODE_SIZE.dp)
            .height(NODE_SIZE.dp)
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
                fontSize = if (((NODE_SIZE / 4)) > 20) 20.sp else (NODE_SIZE / 4).sp
            )
            Text(
                text = "Value: ${node.value.value}",
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = if (((NODE_SIZE / 4)) > 20) 20.sp else (NODE_SIZE / 4).sp
            )
        }
    }
}


@Composable
fun Edge(
    node1: State<NodeData>,
    node2: State<NodeData>,
    modifier: Modifier = Modifier
) {
    Canvas(
        modifier = modifier.size(WINDOW_SIZE.dp),
        onDraw = {
            drawLine(
                start = Offset(x = node1.value.posX.toFloat() + NODE_SIZE, y = node1.value.posY.toFloat() + NODE_SIZE),
                end = Offset(x = node2.value.posX.toFloat() + NODE_SIZE, y = node2.value.posY.toFloat() + NODE_SIZE),
                color = Color.Black,
                strokeWidth = 3F
            )
        }
    )
}

@Composable
fun TreeActionButtons(
    treePresenter: TreePresenter,
    addNode: () -> Unit,
    deleteNode: () -> Unit,
) {

    Row {
        Button(onClick = {
            addNode()
        }) {
            Text("Add Node")
        }

        Spacer(modifier = Modifier.width(NODE_SIZE.dp))

        Button(onClick = {
            deleteNode()
        }) {
            Text(text = "Delete Node")
        }

        Spacer(modifier = Modifier.width(NODE_SIZE.dp))

        Button(onClick = {
            treePresenter.saveTree()
        }) {
            Text(text = "Save tree")
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
    LayoutPresenter.setTreeLayout(tree, WINDOW_SIZE, WINDOW_SIZE)
    Column {
        TreeActionButtons(treePresenter, addNode, deleteNode)

        Box(
            modifier = Modifier.height(WINDOW_SIZE.dp).width(WINDOW_SIZE.dp),

        ) {

            tree.root?.let { root ->
                Tree(
                    mutableStateOf(root)
                )
            }
        }
    }
}

