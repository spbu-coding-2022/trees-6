package app.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.presenter.LayoutPresenter
import app.presenter.TreePresenter
import app.view.treeView.Tree


const val NODE_SIZE = 30
const val WINDOW_SIZE = 800

@Composable
fun TreeActionButtons(
    treePresenter: TreePresenter,
    addNode: () -> Unit,
    deleteNode: () -> Unit,
) {

    Row(modifier = Modifier.background(Color.Cyan).fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Button(onClick = addNode) {
            Text("Add Node")
        }

        Spacer(modifier = Modifier.width(NODE_SIZE.dp))

        Button(onClick = deleteNode) {
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
