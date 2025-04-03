package org.test.kotlin.project

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "TestKotlinProject",
    ) {
        App()
    }
}