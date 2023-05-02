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
    host: State<String>,
    hostChange: (String) -> Unit,
    approveChange: () -> Unit
) {
    Column(modifier = Modifier.padding(vertical = 16.dp)) {

        OutlinedTextField(
            value = host.value,
            onValueChange = hostChange,
            colors = TextFieldDefaults.textFieldColors(
                textColor = if (host.value == "Enter the directory name") Color.Gray else Color.Black,
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
fun databaseConnectionSQL(
    host: State<String>,
    hostChange: (String) -> Unit,
    approveChange: () -> Unit
) {
    Column(modifier = Modifier.padding(vertical = 16.dp)) {

        OutlinedTextField(
            value = host.value,
            onValueChange = hostChange,
            colors = TextFieldDefaults.textFieldColors(
                textColor = if (host.value == "Enter the database name") Color.Gray else Color.Black,
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
    host: State<String>,
    username: State<String>,
    password: State<String>,
    hostChange: (String) -> Unit,
    usernameChange: (String) -> Unit,
    passwordChange: (String) -> Unit,
    approveChange: () -> Unit
) {
    Column(modifier = Modifier.padding(vertical = 16.dp)) {

        OutlinedTextField(
            value = host.value,
            onValueChange = hostChange,
            colors = TextFieldDefaults.textFieldColors(
                textColor = if (host.value == "Enter host") Color.Gray else Color.Black,
                backgroundColor = Color.White,
            )
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = username.value,
            onValueChange = usernameChange,
            colors = TextFieldDefaults.textFieldColors(
                textColor = if (username.value == "Enter username") Color.Gray else Color.Black,
                backgroundColor = Color.White,
            )
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = password.value,
            onValueChange = passwordChange,
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
