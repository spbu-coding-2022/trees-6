package app.view.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.presenter.TreePresenter
import app.view.assets.Selector

@Composable
fun AddNodeScreen(
    treePresenter: TreePresenter,
    key: State<String>,
    value: State<String>,
    ChangesKey: (String) -> Unit,
    ChangesValue: (String) -> Unit,
    back: () -> Unit,
    approve: () -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {

        OutlinedTextField(value = key.value, onValueChange = ChangesKey)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(value = value.value, onValueChange = ChangesValue)

        Spacer(modifier = Modifier.height(16.dp))

        if (
            key.value != "Enter key" &&
            value.value != "Enter value"
        ) {
            Button(onClick = {
                treePresenter.addNode(key.value, value.value)
                approve()
            }) {
                Text("add node")
            }
        }

        Button(onClick = back) {
            Text("back")
        }
    }
}