package app.view.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.presenter.TreePresenter
import app.view.assets.Selector

@Composable
fun TypesChoosingScreen(
    treePresenter: TreePresenter,
    treeName: State<String>,
    treeType: State<String>,
    keyType: State<String>,
    valueType: State<String>,
    onClickChangesKey: (String) -> Unit,
    onClickChangesValue: (String) -> Unit,
    back: () -> Unit,
    approve: () -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Choose the key type from the following options:")
            Spacer(modifier = Modifier.width(10.dp))
            Selector(
                keyType,
                onClickChangesKey,
                listOf("Int", "String")
            )
        }


        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Choose the value type from the following options:")
            Spacer(modifier = Modifier.width(10.dp))
            Selector(
                valueType,
                onClickChangesValue,
                listOf("Int", "String")
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (
            keyType.value != "▾" &&
            valueType.value != "▾"
        ) {
            Button(onClick = {
                treePresenter.createTree(treeName.value, treeType.value, keyType.value, valueType.value)
                approve()
            }) {
                Text("Approve and create")
            }
        }

        Button(onClick = back) {
            Text("back")
        }
    }
}