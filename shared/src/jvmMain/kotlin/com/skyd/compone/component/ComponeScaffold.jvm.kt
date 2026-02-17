package com.skyd.compone.component

import androidx.compose.foundation.PointerMatcher
import androidx.compose.foundation.onClick
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.input.pointer.PointerButton

actual fun Modifier.platformOperation(): Modifier = composed {
    val backInvoker = BackInvoker()
    onClick(
        matcher = PointerMatcher.mouse(PointerButton.Back),
        onClick = backInvoker,
    )
}