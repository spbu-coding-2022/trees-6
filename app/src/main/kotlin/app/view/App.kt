package app.view

import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.window.*
import app.presenter.DataBasePresenter
import app.presenter.TreePresenter
import app.view.assets.ChildStack
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
                val keyType =
                    remember { mutableStateOf("Choose the key type from the following options") }
                val valueType =
                    remember { mutableStateOf("Choose the value type from the following options") }
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
                                { navigation.push(ScreenManager.TreeScreen) }
                            )

                        }

                        is ScreenManager.TreeScreen -> {

                            treePresenter = when (header.value) {

                                "Neo4j" -> DataBasePresenter.connectNeo4j(
                                    databaseMetadata.value,
                                    username.value,
                                    password.value
                                )

                                "Json" -> DataBasePresenter.connectJson(databaseMetadata.value)

                                "SQLite" -> DataBasePresenter.connectSQL(databaseMetadata.value)

                                else -> throw Exception("Incorrect database")

                            }

                            treePresenter?.let { treePresenter ->
                                TreeSreen(
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
                            treePresenter?.let {treePresenter ->
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
