package app.view.treeView

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.view.screens.nodeSize
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
                start = Offset(x = node1.value.posX.toFloat() + nodeSize, y = node1.value.posY.toFloat() + nodeSize),
                end = Offset(x = node2.value.posX.toFloat() + nodeSize, y = node2.value.posY.toFloat() + nodeSize),
                color = Color.Black,
                strokeWidth = 3F
            )
        }
    )
}