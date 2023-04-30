package bstrees.view

import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.window.*
import bstrees.view.assets.HomeScreen
import bstrees.view.assets.TreeSelector
import bstrees.presenter.DataBasePresenter
import bstrees.presenter.TreePresenter

fun main() {
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "graph",
            state = rememberWindowState(
                position = WindowPosition(alignment = Alignment.Center),
                size = DpSize(800.dp, 800.dp)
            ),
        ) {

            val header = remember { mutableStateOf("Choose your database") }
            val host = remember { mutableStateOf("Write host") }
            val username = remember { mutableStateOf("Write username") }
            val password = remember { mutableStateOf("Write password") }
            val approve = remember { mutableStateOf(false) }
            val homeScreenFlag = remember { mutableStateOf(true) }

            if (homeScreenFlag.value) {
                HomeScreen(
                    header,
                    { newHeader -> header.value = newHeader },
                    host,
                    username,
                    password,
                    { newHost -> host.value = newHost },
                    { newUsername -> username.value = newUsername },
                    { newPassword -> password.value = newPassword },
                    approve,
                    { newApprovement -> approve.value = true }
                )
            }
            /**
             * SQL -Название базы
             * Json - название папки, в которой хранится база данных
             */

            var treePresenter: TreePresenter? = null

            if (approve.value == true) {
                if (header.value == "Neo4j") {
                    treePresenter = DataBasePresenter.connectNeo4j(host.value, username.value, password.value)
                    homeScreenFlag.value = false
                }
                if (header.value == "Json"){
                    treePresenter = DataBasePresenter.connectJson(host.value)
                    homeScreenFlag.value = false
                }
            }

            val treeType = remember { mutableStateOf("Choose your tree") }
            val treeName = remember { mutableStateOf("Enter tree name") }

            if (!homeScreenFlag.value && treePresenter != null) {
                TreeSelector(
                    treeType,
                    { newHeader -> treeType.value = newHeader },
                    treePresenter,
                    treeName = treeName,
                    { newName -> treeName.value = newName }
                )
            }
        }
    }
}
