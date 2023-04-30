package bstrees.view.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import bstrees.presenter.TreePresenter
import bstrees.view.assets.Selector
import bstrees.view.assets.TreeView

@Composable
fun TreeActions(
    treePresenter: TreePresenter,
    treeName: State<String>,
    treeType: State<String>
) {

    Column {

        val isClickedAdd = remember { mutableStateOf(false) }
        Button(onClick = { isClickedAdd.value = true }) {
            Text("Create Tree")
        }

        val isClickedLoad = remember { mutableStateOf(false) }
        Button(onClick = { isClickedLoad.value = true }){
            Text("Load Tree")
        }

        if(isClickedAdd.value){
            treePresenter.createTree(treeName.value, treeType.value)
            TreeView(treePresenter)
            //isClickedAdd.value = false
        }

        if(isClickedLoad.value){
            treePresenter.loadTree(treeName.value, treeType.value)
            TreeView(treePresenter)
            //isClickedLoad.value = false
        }


    }
}

@Composable
fun TreeSelector(
    treeType: State<String>,
    onClickChanges: (String) -> Unit,
    treePresenter: TreePresenter,
    treeName: State<String>,
    treeNameChange: (String) -> Unit,
    back: () -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Selector(treeType, onClickChanges, listOf("BS", "AVL", "RB"))

        if (treeType.value != "Choose your tree") {
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(value = treeName.value, onValueChange = treeNameChange)
            TreeActions(treePresenter, treeName, treeType)
        }

        Button(onClick = back){
            Text("Back")
        }
    }
}