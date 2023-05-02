package app.view.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.presenter.TreePresenter
import app.view.assets.Selector
import app.view.utils.Trees

@Composable
fun TreeActions(
    treePresenter: TreePresenter,
    treeName: State<String>,
    treeType: State<String>,
    createTreeMenu: () -> Unit,
    treeView: () -> Unit,
) {

    Column {

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { createTreeMenu() },
            modifier = Modifier.width(150.dp)
        ) {
            Text("Create Tree")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                treePresenter.loadTree(treeName.value, treeType.value)
                treeView()
            },
            modifier = Modifier.width(150.dp)
        ) {
            Text("Load Tree")
        }

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun TreeChoosingScreen(
    treeType: State<String>,
    onClickChanges: (String) -> Unit,
    treePresenter: TreePresenter,
    treeName: State<String>,
    treeNameChange: (String) -> Unit,
    back: () -> Unit,
    createTreeMenu: () -> Unit,
    treeView: () -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Choose your tree:")
            Spacer(modifier = Modifier.width(10.dp))
            Selector(treeType, onClickChanges, listOf(Trees.BS.toString(), Trees.RB.toString(), Trees.AVL.toString()))
        }

        if (treeType.value != "▾") {
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = treeName.value,
                onValueChange = treeNameChange,
                label = { Text("Enter the tree name") },
            )
            Spacer(modifier = Modifier.height(8.dp))
            TreeActions(treePresenter, treeName, treeType, createTreeMenu, treeView)
        }

        Button(onClick = back, modifier = Modifier.width(150.dp)) {
            Text("Back")
        }
    }
}