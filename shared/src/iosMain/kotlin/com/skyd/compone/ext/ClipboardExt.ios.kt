package com.skyd.compone.ext

import androidx.compose.ui.platform.Clipboard
import platform.UIKit.UIPasteboard

actual suspend fun Clipboard.setText(text: CharSequence) {
    UIPasteboard.generalPasteboard.string = text.toString()
}

actual suspend fun Clipboard.getText(): CharSequence? {
    return UIPasteboard.generalPasteboard.string
}
