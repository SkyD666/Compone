package com.skyd.compone.ext

import androidx.compose.ui.platform.Clipboard

expect suspend fun Clipboard.setText(text: CharSequence)

expect suspend fun Clipboard.getText(): CharSequence?