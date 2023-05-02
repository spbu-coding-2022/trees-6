package app.view.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.presenter.TreePresenter
import app.view.assets.Selector

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
fun TreeSreen(
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
            Selector(treeType, onClickChanges, listOf("BS", "AVL", "RB"))
        }

        if (treeType.value != "▾") {
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = treeName.value,
                onValueChange = treeNameChange,
                colors = TextFieldDefaults.textFieldColors(
                    textColor = if (treeName.value == "Enter tree name") Color.Gray else Color.Black,
                    backgroundColor = Color.White,
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            TreeActions(treePresenter, treeName, treeType, createTreeMenu, treeView)
        }

        Button(onClick = back, modifier = Modifier.width(150.dp)) {
            Text("Back")
        }
    }
}