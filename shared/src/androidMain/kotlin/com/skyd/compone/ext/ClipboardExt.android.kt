package com.skyd.compone.ext

import android.content.ClipData
import androidx.compose.ui.platform.Clipboard
import androidx.compose.ui.platform.toClipEntry

private const val PLAIN_TEXT_LABEL = "Plain text"

actual suspend fun Clipboard.setText(text: CharSequence) {
    val clipData = ClipData.newPlainText(PLAIN_TEXT_LABEL, text)
    setClipEntry(clipData.toClipEntry())
}

actual suspend fun Clipboard.getText(): CharSequence? {
    val clipData = getClipEntry()?.clipData ?: return null
    return if (clipData.itemCount > 0) {
        // note: text may be null, ensure this is null-safe
        clipData.getItemAt(0)?.text
    } else {
        null
    }
}