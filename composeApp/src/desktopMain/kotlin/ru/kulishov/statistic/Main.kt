package ru.kulishov.statistic

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Stata",
    ) {

    }
}