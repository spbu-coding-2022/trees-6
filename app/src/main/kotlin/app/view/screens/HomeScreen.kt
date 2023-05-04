package app.view.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.view.assets.*
import app.view.utils.Databases

@Composable
fun HomeScreen(
    databaseName: State<String>,
    onClickChanges: (String) -> Unit,
    host: State<String>,
    username: State<String>,
    password: State<String>,
    hostChange: (String) -> Unit,
    usernameChange: (String) -> Unit,
    passwordChange: (String) -> Unit,
    approveChange: () -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Choose your database:")
            Spacer(modifier = Modifier.width(10.dp))
            Selector(
                databaseName,
                onClickChanges,
                listOf(Databases.Neo4j.toString(), Databases.Json.toString(), Databases.SQLite.toString())
            )
        }

        when (databaseName.value) {

            Databases.Neo4j.toString() -> databaseConnectionNeo4j(
                host,
                username,
                password,
                hostChange,
                usernameChange,
                passwordChange,
                approveChange
            )

            Databases.Json.toString() -> databaseConnectionJson(
                host,
                hostChange,
                approveChange
            )

            Databases.SQLite.toString() -> databaseConnectionSQL(
                host,
                hostChange,
                approveChange
            )
        }
    }
}



