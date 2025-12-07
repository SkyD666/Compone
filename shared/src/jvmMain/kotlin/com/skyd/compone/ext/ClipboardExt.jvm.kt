package com.skyd.compone.ext

import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.Clipboard
import androidx.compose.ui.platform.asAwtTransferable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runInterruptible
import java.awt.datatransfer.DataFlavor
import java.awt.datatransfer.StringSelection

actual suspend fun Clipboard.setText(text: CharSequence) {
    setClipEntry(ClipEntry(StringSelection(text.toString())))
}

actual suspend fun Clipboard.getText(): CharSequence? {
    val selection = getClipEntry()?.asAwtTransferable as? StringSelection ?: return null
    return runInterruptible(Dispatchers.IO) {
        selection.getTransferData(DataFlavor.stringFlavor).toString()
    }
}