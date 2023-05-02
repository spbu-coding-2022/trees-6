package app.view.screens

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

sealed class ScreenManager : Parcelable {

    @Parcelize
    object HomeScreen : ScreenManager()

    @Parcelize
    object TreeScreen : ScreenManager()

    @Parcelize
    object ChosingTypesScreen: ScreenManager()

    @Parcelize
    object TreeView: ScreenManager()

    @Parcelize
    object AddNodeScreen: ScreenManager()
}
