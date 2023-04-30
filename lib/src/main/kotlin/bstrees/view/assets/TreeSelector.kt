package bstrees.view.assets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bstrees.presenter.TreePresenter

@Composable
fun DropdownTree(header: State<String>, onClickChanges: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val items = listOf("BS", "AVL", "RB")
    var selectedIndex by remember { mutableStateOf(0) }
    Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {

        Text(
            header.value,
            fontSize = 30.sp,
            modifier = Modifier
                .clickable(onClick = { expanded = true })
                .background(Color.White)
                .border(width = 1.dp, shape = RectangleShape, color = Color.Blue)
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            items.forEachIndexed { index, s ->
                DropdownMenuItem(onClick = {
                    selectedIndex = index
                    onClickChanges(items[selectedIndex])
                    expanded = false
                }) {
                    Text(text = s)
                }
            }
        }
    }
}

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
    treeNameChange: (String) -> Unit
) {
    Column {
        DropdownTree(treeType, onClickChanges)
        if (treeType.value != "Choose your tree") {
            OutlinedTextField(value = treeName.value, onValueChange = treeNameChange)
            TreeActions(treePresenter, treeName, treeType)
        }
    }
}