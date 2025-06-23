package com.skyd.compone.local

import androidx.compose.runtime.compositionLocalOf

data class WindowController(
    val onClose: () -> Unit,
)

val LocalWindowController = compositionLocalOf<WindowController> {
    error("LocalWindowController not initialized!")
}