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
fun DeleteNodeScreen(
    treePresenter: TreePresenter,
    key: State<String>,
    ChangesKey: (String) -> Unit,
    back: () -> Unit,
    approve: () -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {

        OutlinedTextField(value = key.value, onValueChange = ChangesKey)

        Spacer(modifier = Modifier.height(16.dp))

        if (
            key.value != "Enter key"
        ) {
            Button(onClick = {
                treePresenter.deleteNode(key.value)
                approve()
            }) {
                Text("delete node")
            }
        }

        Button(onClick = back) {
            Text("back")
        }
    }
}