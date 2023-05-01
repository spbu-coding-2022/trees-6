package app.view.assets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun databaseConnectionJson(
    host: State<String>,
    hostChange: (String) -> Unit,
    approveChange: () -> Unit
) {
    Column(modifier = Modifier.padding(vertical = 16.dp)) {
        OutlinedTextField(value = host.value, onValueChange = hostChange)
        Button(onClick = approveChange){
            Text("Approve")
        }
    }
}

@Composable
fun databaseConnectionSQL(
    host: State<String>,
    hostChange: (String) -> Unit,
    approveChange: () -> Unit
) {
    Column(modifier = Modifier.padding(vertical = 16.dp)) {
        OutlinedTextField(value = host.value, onValueChange = hostChange)
        Button(onClick = approveChange){
            Text("Approve")
        }
    }
}

@Composable
fun databaseConnectionNeo4j(
    host: State<String>,
    username: State<String>,
    password: State<String>,
    hostChange: (String) -> Unit,
    usernameChange: (String) -> Unit,
    passwordChange: (String) -> Unit,
    approveChange: () -> Unit
) {
    Column(modifier = Modifier.padding(vertical = 16.dp)) {

        OutlinedTextField(value = host.value, onValueChange = hostChange)

        OutlinedTextField(value = username.value, onValueChange = usernameChange)

        OutlinedTextField(value = password.value, onValueChange = passwordChange)

        Button(onClick = approveChange){
            Text("Approve")
        }

    }
}
