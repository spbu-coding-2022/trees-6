package app.view.assets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun databaseConnectionJson(
    directory: State<String>,
    hostChange: (String) -> Unit,
    approveChange: () -> Unit
) {
    Column(modifier = Modifier.padding(vertical = 16.dp)) {

        OutlinedTextField(
            value = directory.value,
            onValueChange = hostChange,
            label = { Text("Enter the directory name") },
            placeholder = { Text("JsonDir") }
        )

        Spacer(modifier = Modifier.height(15.dp))

        Button(onClick = approveChange) {
            Text("Approve")
        }
    }
}

@Composable
fun databaseConnectionSQL(
    name: State<String>,
    hostChange: (String) -> Unit,
    approveChange: () -> Unit
) {
    Column(modifier = Modifier.padding(vertical = 16.dp)) {

        OutlinedTextField(
            value = name.value,
            onValueChange = hostChange,
            label = { Text("Enter the database name") },
            colors = TextFieldDefaults.textFieldColors(
                textColor = if (name.value == "Enter the database name") Color.Gray else Color.Black,
                backgroundColor = Color.White,
            )
        )

        Spacer(modifier = Modifier.height(15.dp))

        Button(onClick = approveChange) {
            Text("Approve")
        }
    }
}

@Composable
fun databaseConnectionNeo4j(
    databaseMetadata: State<String>,
    username: State<String>,
    password: State<String>,
    hostChange: (String) -> Unit,
    usernameChange: (String) -> Unit,
    passwordChange: (String) -> Unit,
    approveChange: () -> Unit
) {
    Column(modifier = Modifier.padding(vertical = 16.dp)) {

        OutlinedTextField(
            value = databaseMetadata.value,
            onValueChange = hostChange,
            label = { Text("Enter host") },
            colors = TextFieldDefaults.textFieldColors(
                textColor = if (databaseMetadata.value == "Enter host") Color.Gray else Color.Black,
                backgroundColor = Color.White,
            )
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = username.value,
            onValueChange = usernameChange,
            label = { Text("Enter username") },
            colors = TextFieldDefaults.textFieldColors(
                textColor = if (username.value == "Enter username") Color.Gray else Color.Black,
                backgroundColor = Color.White,
            )
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = password.value,
            onValueChange = passwordChange,
            label = { Text("Enter password") },
            colors = TextFieldDefaults.textFieldColors(
                textColor = if (password.value == "Enter password") Color.Gray else Color.Black,
                backgroundColor = Color.White,
            )
        )

        Spacer(modifier = Modifier.height(15.dp))

        Button(onClick = approveChange) {
            Text("Approve")
        }

    }
}
