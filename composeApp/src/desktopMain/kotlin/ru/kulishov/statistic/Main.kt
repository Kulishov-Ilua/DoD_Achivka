package ru.kulishov.statistic

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Stata",
    ) {
        when(state){
            -1 -> authScreenDesktop(
                darkTheme.background, darkTheme.primary, darkTheme.secondary,
                Color(32,32,32))
            1 -> ExpandedProfile(key)
            11 -> {
                updateTop=1
                topScreenDesktop(
                    topActive, darkTheme.background, darkTheme.primary, darkTheme.secondary,
                    Color(32,32,32),
                    darkTheme.onPrimary)
            }
            21 ->{
                awardsScreen(awardActive, 1,darkTheme.background, darkTheme.primary, darkTheme.secondary,
                    Color(32,32,32),
                    darkTheme.onPrimary)
            }

        }

    }
}