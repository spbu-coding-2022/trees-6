package app.view.assets

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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

@Composable
fun Tree(
    node: State<NodeData>,
) {

    val nodeCoords = remember { mutableStateOf(Pair(node.value.posX.toFloat(), node.value.posY.toFloat())) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            node.value.leftNode?.let { leftNode->
                Tree(mutableStateOf(leftNode))
            }
            node.value.rightNode?.let { rightNode->
                Tree(mutableStateOf(rightNode))
            }
        }
        TreeNode(node, nodeCoords)
    }
}


@Composable
fun TreeNode(node: State<NodeData>, nodeCoords: MutableState<Pair<Float, Float>>) {
    node.value.posX = nodeCoords.value.first.toInt()
    node.value.posY = nodeCoords.value.second.toInt()
    Box(
        modifier = Modifier
            .offset { IntOffset(nodeCoords.value.first.toInt(), nodeCoords.value.second.toInt()) }
            .background(Color.Red, CircleShape)
            .size(50.dp)
            .pointerInput(Unit) {
                // Обработчик перемещения узла
                detectDragGestures { change, dragAmount ->
                    nodeCoords.value = Pair(
                        nodeCoords.value.first + dragAmount.x,
                        nodeCoords.value.second + dragAmount.y
                    )
                    if (change.positionChange() != Offset.Zero) change.consume()
                }
            }

    )
}


@Composable
fun Edge(
    node1: State<NodeData>,
    node2: State<NodeData>,
    modifier: Modifier = Modifier
) {
    Canvas(
        modifier = modifier.size(800.dp),
        onDraw = {
            drawLine(
                start = Offset(x = node1.value.posX.toFloat() + 800, y = node1.value.posY.toFloat() + 100),
                end = Offset(x = node2.value.posX.toFloat() + 800, y = node2.value.posY.toFloat() + 100),
                color = Color.Black,
                strokeWidth = 5F
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

        Spacer(modifier = Modifier.width(30.dp))

        Button(onClick = {
            deleteNode()
        }) {
            Text(text = "Delete Node")
        }

        Spacer(modifier = Modifier.width(30.dp))

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
    LayoutPresenter.setTreeLayout(tree, 800, 800)
    Column {
        TreeActionButtons(treePresenter, addNode, deleteNode)

        Box(
            modifier = Modifier.height(800.dp).width(800.dp),
            contentAlignment = Alignment.Center
        ) {
            tree.root?.let { root ->
                Tree(
                    mutableStateOf(root)
                )
            }
        }
    }
}


