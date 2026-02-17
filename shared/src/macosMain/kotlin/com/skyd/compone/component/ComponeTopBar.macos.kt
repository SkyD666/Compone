package com.skyd.compone.component

import androidx.compose.foundation.PointerMatcher
import androidx.compose.foundation.onClick
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.input.pointer.PointerButton
import platform.AppKit.NSApplication

@Composable
actual fun onEmptyPopBackStack(): () -> Unit {
    return {
        val app = NSApplication.sharedApplication
        val window = app.keyWindow ?: app.mainWindow
        window?.performClose(null)
    }
}

actual fun Modifier.pointerOnBack(onBack: (() -> Unit)?): Modifier = composed {
    val backInvoker = if (onBack == DefaultBackInvoker) {
        BackInvoker()
    } else {
        onBack
    }
    onClick(
        matcher = PointerMatcher.mouse(PointerButton.Back),
        onClick = { backInvoker?.invoke() },
    )
}