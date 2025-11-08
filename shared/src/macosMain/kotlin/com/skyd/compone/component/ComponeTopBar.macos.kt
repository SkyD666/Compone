package com.skyd.compone.component

import androidx.compose.runtime.Composable
import platform.AppKit.NSApplication

@Composable
actual fun onEmptyPopBackStack(): () -> Unit {
    return {
        val app = NSApplication.sharedApplication
        val window = app.keyWindow ?: app.mainWindow
        window?.performClose(null)
    }
}
