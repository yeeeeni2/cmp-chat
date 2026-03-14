package com.yeeeeni.chat.data

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.yeeeeni.chat.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "kmp-chat",
    ) {
        App()
    }
}