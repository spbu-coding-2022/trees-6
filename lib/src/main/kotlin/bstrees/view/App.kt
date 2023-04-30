package bstrees.view

import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.window.*
import bstrees.presenter.DataBasePresenter
import bstrees.presenter.TreePresenter
import bstrees.view.assets.ChildStack
import bstrees.view.assets.ProvideComponentContext
import bstrees.view.screens.HomeScreen
import bstrees.view.screens.ScreenManager
import bstrees.view.screens.TreeSelector
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.essenty.lifecycle.LifecycleRegistry

fun main() {

    val lifecycle = LifecycleRegistry()
    val rootComponentContext = DefaultComponentContext(lifecycle = lifecycle)

    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "graph",
            state = rememberWindowState(
                position = WindowPosition(alignment = Alignment.Center),
                size = DpSize(800.dp, 800.dp)
            ),
        ) {

            ProvideComponentContext(rootComponentContext) {

                val header = remember { mutableStateOf("Choose your database") }
                val databaseMetadata = remember { mutableStateOf("") }
                val username = remember { mutableStateOf("Enter username") }
                val password = remember { mutableStateOf("Enter password") }
                val navigation = remember { StackNavigation<ScreenManager>() }
                val treeType = remember { mutableStateOf("Choose your tree") }
                val treeName = remember { mutableStateOf("Enter tree name") }
                var treePresenter: TreePresenter

                ChildStack(
                    source = navigation,
                    initialStack = { listOf(ScreenManager.HomeScreen) },
                    handleBackButton = true,
                    animation = stackAnimation(fade() + scale()),
                ) { screen ->
                    when (screen) {

                        is ScreenManager.HomeScreen -> {

                            when (header.value) {

                                "Neo4j" -> databaseMetadata.value = "Enter host"

                                "Json" -> databaseMetadata.value = "Enter the directory name"

                                "SQLite" -> databaseMetadata.value = "Enter the database name"

                            }

                            HomeScreen(
                                header,
                                { newHeader -> header.value = newHeader },
                                databaseMetadata,
                                username,
                                password,
                                { newMeta -> databaseMetadata.value = newMeta },
                                { newUsername -> username.value = newUsername },
                                { newPassword -> password.value = newPassword },
                                { navigation.push(ScreenManager.TreeSelector) }
                            )

                        }

                        is ScreenManager.TreeSelector -> {

                            when (header.value) {

                                "Neo4j" -> treePresenter = DataBasePresenter.connectNeo4j(
                                    databaseMetadata.value,
                                    username.value,
                                    password.value
                                )

                                "Json" -> treePresenter = DataBasePresenter.connectJson(databaseMetadata.value)

                                "SQLite" -> treePresenter = DataBasePresenter.connectSQL(databaseMetadata.value)

                                else -> throw Exception("Incorrect database")

                            }

                            TreeSelector(
                                treeType,
                                { newHeader -> treeType.value = newHeader },
                                treePresenter,
                                treeName = treeName,
                                { newName -> treeName.value = newName },
                                back = navigation::pop
                            )

                        }
                    }
                }
            }
        }
    }
}
