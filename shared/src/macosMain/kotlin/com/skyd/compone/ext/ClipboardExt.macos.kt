package com.skyd.compone.ext

import androidx.compose.ui.platform.Clipboard
import platform.AppKit.NSPasteboard
import platform.AppKit.NSPasteboardTypeString

actual suspend fun Clipboard.setText(text: CharSequence): Unit =
    with(NSPasteboard.generalPasteboard) {
        clearContents()
        setString(text.toString(), forType = NSPasteboardTypeString)
    }

actual suspend fun Clipboard.getText(): CharSequence? {
    return NSPasteboard.generalPasteboard.stringForType(NSPasteboardTypeString)
}
