package bstrees.view.Assets

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

@Composable
fun DropdownTree(header: State<String>, onClickChanges: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val items = listOf("Randomized binary search", "AVL", "Red black")
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
    add: () -> Unit,
    load: () -> Unit
){
    Column {

        Button(onClick = add) {
            Text("Create Tree")
        }
        Button(onClick = load){
            Text(text = "Load Tree")
        }
    }
}

@Composable
fun TreeSelector(
    header: State<String>,
    onClickChanges: (String) -> Unit,
    add: () -> Unit,
    load: () -> Unit,
    treeName: State<String>,
    treeNameChange: (String) -> Unit
){
    Column{
        DropdownTree(header, onClickChanges)
        if (header.value != "Choose your tree") {
            OutlinedTextField(value = treeName.value, onValueChange = treeNameChange)
            TreeActions(add, load)
        }
    }
}