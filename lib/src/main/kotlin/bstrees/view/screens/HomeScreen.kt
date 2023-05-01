package bstrees.view.screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import bstrees.view.assets.Selector
import bstrees.view.assets.databaseConnectionJson
import bstrees.view.assets.databaseConnectionNeo4j
import bstrees.view.assets.databaseConnectionSQL

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
    approveChange: () -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Selector(header, onClickChanges, listOf("Neo4j", "Json", "SQLite"))
        when (header.value) {
            "Neo4j" -> databaseConnectionNeo4j(
                host,
                username,
                password,
                hostChange,
                usernameChange,
                passwordChange,
                approveChange
            )

            "Json" -> databaseConnectionJson(
                host,
                hostChange,
                approveChange
            )

            "SQLite" -> databaseConnectionSQL(
                host,
                hostChange,
                approveChange
            )
        }
    }
}



