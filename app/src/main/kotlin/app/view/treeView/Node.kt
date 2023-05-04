package app.view.treeView

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.view.screens.nodeSize
import bstrees.model.dataBases.NodeData
import java.lang.Float.max
import java.lang.Float.min

@Composable
fun Node(node: State<NodeData>, nodeCoords: MutableState<Pair<Float, Float>>) {
    node.value.posX = nodeCoords.value.first.toInt()
    node.value.posY = nodeCoords.value.second.toInt()
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .offset { IntOffset(nodeCoords.value.first.toInt(), nodeCoords.value.second.toInt()) }
            .background(Color.Red, CircleShape)
            .size(nodeSize.dp)
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    val newXPos = max(0f, min(1550f, nodeCoords.value.first + dragAmount.x))
                    val newYPos = max(0f, min(1400f, nodeCoords.value.second + dragAmount.y))

                    nodeCoords.value = Pair(
                        newXPos,
                        newYPos
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
                fontSize = if (((nodeSize / 4)) > 20) 20.sp else (nodeSize / 4).sp
            )
            Text(
                text = "Value: ${node.value.value}",
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = if (((nodeSize / 4)) > 20) 20.sp else (nodeSize / 4).sp
            )
        }
    }
}