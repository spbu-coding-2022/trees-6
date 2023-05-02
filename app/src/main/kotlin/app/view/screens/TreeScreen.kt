package app.view.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
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

        Button(onClick = {
            createTreeMenu()
        }
        ) {
            Text("Create Tree")
        }

        Button(onClick = {
            treePresenter.loadTree(treeName.value, treeType.value)
            treeView()
        }
        ) {
            Text("Load Tree")
        }
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
        Selector(treeType, onClickChanges, listOf("BS", "AVL", "RB"))

        if (treeType.value != "Choose your tree") {
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(value = treeName.value, onValueChange = treeNameChange)
            TreeActions(treePresenter, treeName, treeType, createTreeMenu, treeView)
        }

        Button(onClick = back) {
            Text("Back")
        }
    }
}