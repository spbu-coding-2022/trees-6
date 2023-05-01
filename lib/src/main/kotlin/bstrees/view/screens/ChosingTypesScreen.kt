package bstrees.view.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import bstrees.view.assets.Selector

@Composable
fun ChosingTypesScreen(
    keyType: State<String>,
    valueType: State<String>,
    onClickChangesKey: (String) -> Unit,
    onClickChangesValue: (String) -> Unit,
    approve: () -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {

        Selector(
            keyType,
            onClickChangesKey,
            listOf("Int", "String")
        )

        Spacer(modifier = Modifier.height(16.dp))

        Selector(
            valueType,
            onClickChangesValue,
            listOf("Int", "String")
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (
            keyType.value != "Choose the key type from the following options" &&
            valueType.value != "Choose the value type from the following options"
        ) {
            Button(onClick = approve) {
                Text("Approve and create")
            }
        }

    }
}