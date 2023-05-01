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
import androidx.compose.ui.unit.dp
import bstrees.model.dataBases.serialize.types.SerializableNode
import bstrees.presenter.TreePresenter

@Composable
fun Tree(root: State<SerializableNode>) {

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Node(root)
        Row {

            val checkLeftSon = root.value.leftNode
            checkLeftSon?.let { leftSon ->
                val temp = remember { mutableStateOf(leftSon) }
                Tree(temp)
            }

            val checkRightSon = root.value.rightNode
            checkRightSon?.let { rightSon ->
                val temp = remember { mutableStateOf(rightSon) }
                Tree(temp)
            }

        }
    }

}

@Composable
fun Node(node: State<SerializableNode>) {

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(
                color = if (node.value.metadata == "RED") Color.Red
                else if (node.value.metadata == "BLACK") Color.Black
                else Color.Magenta,
                shape = CircleShape
            )
            .width(100.dp)
            .height(100.dp)
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
                color = Color.White
            )
            Text(
                text = "Value: ${node.value.value}",
                textAlign = TextAlign.Center,
                color = Color.White
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
fun TreeView(treePresenter: TreePresenter) {
    val tree = treePresenter.tree ?: return
    Row {
        TreeActionButtons(treePresenter)
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            tree.root?.let {
                Tree(
                    mutableStateOf(tree.root)
                )
            }
        }
    }
}
