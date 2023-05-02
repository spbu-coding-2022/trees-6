package app.view.screens

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

sealed class ScreenManager : Parcelable {

    @Parcelize
    object HomeScreen : ScreenManager()

    @Parcelize
    object TreeChoosingScreen : ScreenManager()

    @Parcelize
    object TypesChoosingScreen: ScreenManager()

    @Parcelize
    object TreeScreen: ScreenManager()

    @Parcelize
    object AddNodeScreen: ScreenManager()

    @Parcelize
    object DeleteNodeScreen: ScreenManager()
}
