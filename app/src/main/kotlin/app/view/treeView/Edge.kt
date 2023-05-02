package app.view.treeView

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.view.screens.NODE_SIZE
import app.view.screens.WINDOW_SIZE
import bstrees.model.dataBases.NodeData

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