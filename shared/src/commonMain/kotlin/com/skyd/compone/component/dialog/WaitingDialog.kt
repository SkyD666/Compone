package com.skyd.compone.component.dialog

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.HourglassEmpty
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearWavyProgressIndicator
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import compone.shared.generated.resources.Res
import compone.shared.generated.resources.cancel
import compone.shared.generated.resources.waiting
import compone.shared.generated.resources.warning
import org.jetbrains.compose.resources.stringResource

@Composable
fun WaitingDialog(
    visible: Boolean,
    currentValue: Int? = null,
    totalValue: Int? = null,
    msg: String? = null,
    title: String = stringResource(Res.string.warning),
    onCancel: (() -> Unit)? = null,
    cancelEnabled: Boolean = true,
) {
    if (currentValue == null || totalValue == null) {
        WaitingDialog(
            visible = visible,
            title = title,
            onCancel = onCancel,
            cancelEnabled = cancelEnabled,
            text = if (msg == null) null else {
                {
                    Text(
                        text = msg,
                        style = MaterialTheme.typography.labelLarge,
                        textAlign = TextAlign.Center,
                    )
                }
            },
        )
    } else {
        WaitingDialog(
            visible = visible,
            title = title,
            onCancel = onCancel,
            cancelEnabled = cancelEnabled,
            icon = { Icon(imageVector = Icons.Outlined.HourglassEmpty, contentDescription = null) },
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val animatedProgress by animateFloatAsState(
                    targetValue = currentValue.toFloat() / totalValue,
                    animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec,
                    label = "waitingDialogAnimatedProgress"
                )
                LinearWavyProgressIndicator(
                    progress = { animatedProgress },
                    modifier = Modifier.semantics(mergeDescendants = true) {},
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "$currentValue / $totalValue",
                    style = MaterialTheme.typography.labelLarge
                )
                if (msg != null) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = msg,
                        style = MaterialTheme.typography.labelLarge,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}

/**
 * Providing [onCancel] shows a cancel button and handles back presses. The caller owns
 * task cancellation and visibility. [cancelEnabled] can protect a final saving phase.
 * Outside clicks never cancel the operation.
 */
@Composable
fun WaitingDialog(
    visible: Boolean,
    title: String = stringResource(Res.string.waiting),
    icon: @Composable (() -> Unit)? = { LoadingIndicator() },
    onCancel: (() -> Unit)? = null,
    cancelEnabled: Boolean = true,
    text: @Composable (() -> Unit)? = null,
) {
    ComponeDialog(
        visible = visible,
        properties = DialogProperties(
            // Consume back even when cancellation is disabled so it cannot navigate
            // the underlying screen. The callback below decides whether to cancel.
            dismissOnBackPress = true,
            dismissOnClickOutside = false,
        ),
        onDismissRequest = { if (cancelEnabled) onCancel?.invoke() },
        icon = icon,
        title = { Text(text = title) },
        text = text,
        confirmButton = {},
        dismissButton = onCancel?.let { cancel ->
            {
                TextButton(onClick = cancel, enabled = cancelEnabled) {
                    Text(stringResource(Res.string.cancel))
                }
            }
        },
    )
}
