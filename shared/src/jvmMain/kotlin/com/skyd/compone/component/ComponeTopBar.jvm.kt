package com.skyd.compone.component

import androidx.compose.foundation.PointerMatcher
import androidx.compose.foundation.onClick
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.input.pointer.PointerButton

actual fun Modifier.pointerOnBack(onBack: (() -> Unit)?): Modifier = composed {
    val backInvoker = onBack ?: BackInvoker()
    onClick(
        matcher = PointerMatcher.mouse(PointerButton.Back),
        onClick = backInvoker,
    )
}