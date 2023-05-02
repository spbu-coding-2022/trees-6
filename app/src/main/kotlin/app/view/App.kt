package app.view

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.window.*
import app.presenter.DataBasePresenter
import app.presenter.TreePresenter
import app.view.assets.ChildStack
import app.view.utils.Databases
import app.view.assets.ProvideComponentContext
import app.view.assets.TreeView
import app.view.screens.*
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import java.awt.Dimension

fun main() {

    val lifecycle = LifecycleRegistry()
    val rootComponentContext = DefaultComponentContext(lifecycle = lifecycle)

    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "graph",
            state = rememberWindowState(
                position = WindowPosition(alignment = Alignment.Center),
            ),

            ) {
            window.minimumSize = Dimension(800, 800)
            window.maximumSize = Dimension(800, 800)

            ProvideComponentContext(rootComponentContext) {

                val databaseChoice = remember { mutableStateOf("▾") }
                val databaseMetadata = remember { mutableStateOf("") }
                val username = remember { mutableStateOf("") }
                val password = remember { mutableStateOf("") }
                val navigation = remember { StackNavigation<ScreenManager>() }
                val treeType = remember { mutableStateOf("▾") }
                val treeName = remember { mutableStateOf("Enter tree name") }
                val keyType = remember { mutableStateOf("▾") }
                val valueType = remember { mutableStateOf("▾") }
                var isDirectoryNameWritten = true
                var treePresenter: TreePresenter? = null

                // for tree adding and deleting
                val key = remember { mutableStateOf("Enter key") }
                val value = remember { mutableStateOf("Enter value") }

                ChildStack(
                    source = navigation,
                    initialStack = { listOf(ScreenManager.HomeScreen) },
                    handleBackButton = true,
                    animation = stackAnimation(fade() + scale()),
                ) { screen ->
                    when (screen) {

                        is ScreenManager.HomeScreen -> {

                            HomeScreen(
                                databaseChoice,
                                { newHeader -> databaseChoice.value = newHeader },
                                databaseMetadata,
                                username,
                                password,
                                { newMeta ->
                                    databaseMetadata.value =
                                        if (databaseMetadata.value == "" && databaseChoice.value == "Json" && !isDirectoryNameWritten) "JsonDir"
                                        else newMeta
                                },
                                { newUsername -> username.value = newUsername },
                                { newPassword -> password.value = newPassword },
                                {
                                    navigation.push(ScreenManager.TreeScreen)
                                    if (databaseMetadata.value == "") isDirectoryNameWritten = false
                                }
                            )

                        }

                        is ScreenManager.TreeScreen -> {

                            treePresenter = when (databaseChoice.value) {

                                Databases.Neo4j.toString() -> DataBasePresenter.connectNeo4j(
                                    databaseMetadata.value,
                                    username.value,
                                    password.value
                                )

                                Databases.Json.toString() -> DataBasePresenter.connectJson(databaseMetadata.value)

                                Databases.SQLite.toString() -> DataBasePresenter.connectSQL(databaseMetadata.value)

                                else -> throw Exception("Incorrect database")

                            }

                            treePresenter?.let { treePresenter ->
                                TreeChoosingScreen(
                                    treeType,
                                    { newHeader -> treeType.value = newHeader },
                                    treePresenter,
                                    treeName = treeName,
                                    { newName -> treeName.value = newName },
                                    back = navigation::pop,
                                    { navigation.push(ScreenManager.ChosingTypesScreen) },
                                    { navigation.push(ScreenManager.TreeView) }
                                )
                            }

                        }

                        is ScreenManager.ChosingTypesScreen -> {
                            treePresenter?.let { treePresenter ->
                                ChosingTypesScreen(
                                    treePresenter,
                                    treeName,
                                    treeType,
                                    keyType,
                                    valueType,
                                    { newKeyType -> keyType.value = newKeyType },
                                    { newValueType -> valueType.value = newValueType },
                                    back = navigation::pop,
                                    approve = { navigation.push(ScreenManager.TreeView) }
                                )
                            }
                        }

                        is ScreenManager.TreeView -> {
                            treePresenter?.let { treePresenter ->
                                TreeView(
                                    treePresenter,
                                    addNode = { navigation.push(ScreenManager.AddNodeScreen) },
                                    deleteNode = { navigation.push(ScreenManager.DeleteNodeScreen) },
                                )
                            }
                        }

                        is ScreenManager.AddNodeScreen -> {
                            key.value = "Enter key"
                            value.value = "Enter value"
                            treePresenter?.let { treePresenter ->
                                AddNodeScreen(
                                    treePresenter,
                                    key,
                                    value,
                                    { newKey -> key.value = newKey },
                                    { newValue -> value.value = newValue },
                                    back = { navigation.pop() },
                                    approve = { navigation.pop() }
                                )
                            }
                        }

                        is ScreenManager.DeleteNodeScreen -> {
                            key.value = "Enter key"
                            treePresenter?.let { treePresenter ->
                                DeleteNodeScreen(
                                    treePresenter,
                                    key,
                                    { newKey -> key.value = newKey },
                                    back = { navigation.pop() },
                                    approve = { navigation.pop() }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
