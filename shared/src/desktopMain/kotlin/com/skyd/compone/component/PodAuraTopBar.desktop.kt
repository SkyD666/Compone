package com.skyd.compone.component

import androidx.compose.runtime.Composable
import com.skyd.compone.local.LocalWindowController

@Composable
actual fun onEmptyPopBackStack(): () -> Unit {
    val controller = LocalWindowController.current
    return { controller.onClose() }
}