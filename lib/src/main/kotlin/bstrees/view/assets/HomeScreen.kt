package bstrees.view.assets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DatabaseSelector(header: State<String>, onClickChanges: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val items = listOf("Neo4j", "Json", "SQLite")
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
fun dataBaseConnectionNeo4j(
    host: State<String>,
    username: State<String>,
    password: State<String>,
    hostChange: (String) -> Unit,
    usernameChange: (String) -> Unit,
    passwordChange: (String) -> Unit,
    approve: State<Boolean>,
    approveChange: (Boolean) -> Unit
) {
    Row {
        Column {
            OutlinedTextField(value = host.value, onValueChange = hostChange)
            OutlinedTextField(value = username.value, onValueChange = usernameChange)
            OutlinedTextField(value = password.value, onValueChange = passwordChange)
        }
        Checkbox(checked = approve.value, onCheckedChange = approveChange)
    }
}

@Composable
fun HomeScreen(
    header: State<String>,
    onClickChanges: (String) -> Unit,
    host: State<String>,
    username: State<String>,
    password: State<String>,
    hostChange: (String) -> Unit,
    usernameChange: (String) -> Unit,
    passwordChange: (String) -> Unit,
    approve: State<Boolean>,
    approveChange: (Boolean) -> Unit
) {
    Column(modifier = Modifier) {
        DatabaseSelector(header, onClickChanges)
        if (header.value == "Neo4j") {
            dataBaseConnectionNeo4j(
                host,
                username,
                password,
                hostChange,
                usernameChange,
                passwordChange,
                approve,
                approveChange
            )
        }
    }
}
