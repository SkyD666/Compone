package com.skyd.compone.component.dialog

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import compone.shared.generated.resources.Res
import compone.shared.generated.resources.cancel
import compone.shared.generated.resources.delete
import compone.shared.generated.resources.warning
import org.jetbrains.compose.resources.stringResource

@Composable
fun DeleteWarningDialog(
    visible: Boolean = true,
    title: String = stringResource(Res.string.warning),
    text: String? = null,
    confirmText: String = stringResource(Res.string.delete),
    dismissText: String = stringResource(Res.string.cancel),
    onDismissRequest: () -> Unit,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    ComponeDialog(
        visible = visible,
        onDismissRequest = onDismissRequest,
        icon = { Icon(imageVector = Icons.Outlined.Warning, contentDescription = null) },
        title = { Text(text = title) },
        text = if (text == null) null else {
            { Text(text = text) }
        },
        confirmButton = {
            TextButton(onClick = {
                onConfirm()
                onDismiss()
            }) { Text(confirmText) }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text(dismissText) } },
    )
}