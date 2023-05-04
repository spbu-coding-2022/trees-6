package app.view.assets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Selector(header: State<String>, onClickChanges: (String) -> Unit, items: List<String>) {
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(0) }
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {

        Row(
            horizontalArrangement = if (header.value != "▾") Arrangement.Center else Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable(onClick = { expanded = true })
                .background(Color.White)
                .border(width = 1.dp, shape = AbsoluteRoundedCornerShape(10.dp), color = Color.Blue)
                .width(300.dp)
                .height(40.dp)
                .absoluteOffset(if (header.value == "▾") -12.dp else 0.dp)
        ) {
            Text(
                header.value,
                fontSize = 25.sp,
            )
        }

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