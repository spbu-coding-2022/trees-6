package bstrees.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.window.*
import bstrees.model.dataBases.serialize.types.SerializableNode
import bstrees.presenter.DataBasePresenter
import bstrees.presenter.TreePresenter
import bstrees.view.assets.ChildStack
import bstrees.view.assets.ProvideComponentContext
import bstrees.view.assets.Tree
import bstrees.view.assets.TreeView
import bstrees.view.screens.ChosingTypesScreen
import bstrees.view.screens.HomeScreen
import bstrees.view.screens.ScreenManager
import bstrees.view.screens.TreeSreen
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
                                { navigation.push(ScreenManager.TreeScreen) }
                            )

                        }

                        is ScreenManager.TreeScreen -> {

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

                            TreeSreen(
                                treeType,
                                { newHeader -> treeType.value = newHeader },
                                treePresenter,
                                treeName = treeName,
                                { newName -> treeName.value = newName },
                                back = navigation::pop,
                                { navigation.push(ScreenManager.ChosingTypesScreen) },
                                keyType,
                                valueType
                            )

                        }

                        is ScreenManager.ChosingTypesScreen -> {
                            ChosingTypesScreen(
                                keyType,
                                valueType,
                                { newKeyType -> keyType.value = newKeyType },
                                { newValueType -> valueType.value = newValueType },
                                approve = navigation::pop
                            )
                        }
                    }
                }
            }
        }
    }
}
