package bstrees.view.Assets

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
import bstrees.model.dataBases.serialize.types.SerializableNode

@Composable
fun Tree(root: State<SerializableNode>) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
        Column {
            Node(root)
            Row {

                val checkLeftSon = root.value.leftNode
                if (checkLeftSon != null) {
                    val temp = remember { mutableStateOf(checkLeftSon) }
                    Tree(temp)
                }

                val checkRightSon = root.value.rightNode
                if (checkRightSon != null) {
                    val temp = remember { mutableStateOf(checkRightSon) }
                    Tree(temp)
                }

            }
        }
    }
}

@Composable
fun Node(node: State<SerializableNode>) {

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(color = Color.Cyan, shape = CircleShape)
            .width(100.dp)
            .height(100.dp)
            .border(
                width = 1.dp,
                color = Color.Black,
                shape = CircleShape
            )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Key: ${node.value.key}",
                textAlign = TextAlign.Center,
                color = Color.Black
            )
            Text(
                text = "Value: ${node.value.value}",
                textAlign = TextAlign.Center,
                color = Color.Black
            )
        }
    }

}

@Composable
fun ActionButtons(
    add: () -> Unit,
    delete: () -> Unit
) {

    Button(onClick = add) {
        Text("Add Node")
    }

    Button(onClick = delete) {
        Text(text = "Delete Node")
    }

}

@Composable
fun TreeView() {
    Row {
        //OptionTools()
        //Tree()
    }
}