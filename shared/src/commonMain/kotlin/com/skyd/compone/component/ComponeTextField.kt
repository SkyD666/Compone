package com.skyd.compone.component

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.ContentPaste
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.platform.WindowInfo
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.skyd.compone.ext.getText
import com.skyd.compone.ext.thenIf
import compone.shared.generated.resources.Res
import compone.shared.generated.resources.clear_input_text
import compone.shared.generated.resources.password_visibility_off
import compone.shared.generated.resources.password_visibility_on
import compone.shared.generated.resources.paste
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource


// https://issuetracker.google.com/issues/204502668#comment15
suspend fun FocusRequester.safeRequestFocus(windowInfo: WindowInfo) {
    snapshotFlow { windowInfo.isWindowFocused }.collect { isWindowFocused ->
        if (isWindowFocused) {
            requestFocus()
        }
    }
}

enum class ComponeTextFieldStyle(val value: String) {
    Normal("Normal"),
    Outlined("Outlined");

    companion object {
        fun toEnum(value: String): ComponeTextFieldStyle {
            return if (value == Normal.value) Normal else Outlined
        }
    }
}

val DefaultTrailingIcon: @Composable () -> Unit = {}

@Composable
fun ComponeTextField(
    modifier: Modifier = Modifier,
    value: String,
    label: String = "",
    enabled: Boolean = true,
    readOnly: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    singleLine: Boolean = maxLines == 1,
    style: ComponeTextFieldStyle = ComponeTextFieldStyle.Outlined,
    autoRequestFocus: Boolean = true,
    onValueChange: (String) -> Unit,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    isPassword: Boolean = false,
    placeholder: String = "",
    trailingIcon: @Composable (() -> Unit)? = DefaultTrailingIcon,
    errorMessage: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
    colors: TextFieldColors =
        if (style == ComponeTextFieldStyle.Normal) TextFieldDefaults.colors()
        else OutlinedTextFieldDefaults.colors(),
) {
    val scope = rememberCoroutineScope()
    var showPassword by rememberSaveable { mutableStateOf(false) }
    val clipboard = LocalClipboard.current
    val focusRequester = remember { FocusRequester() }

    val windowInfo = LocalWindowInfo.current
    LaunchedEffect(windowInfo, autoRequestFocus) {
        if (autoRequestFocus) {
            focusRequester.safeRequestFocus(windowInfo)
        }
    }

    val newModifier = modifier.thenIf(autoRequestFocus) { focusRequester(focusRequester) }
    val newLabel: @Composable (() -> Unit)? =
        if (label.isBlank()) null else {
            { Text(label) }
        }
    val newOnValueChange: (String) -> Unit = { if (!readOnly) onValueChange(it) }
    val newVisualTransformation =
        if (isPassword && !showPassword) PasswordVisualTransformation() else visualTransformation
    val newPlaceholder: @Composable () -> Unit = {
        Text(
            text = placeholder,
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.7f),
            style = MaterialTheme.typography.bodyMedium
        )
    }
    val newTrailingIcon: (@Composable () -> Unit)? = if (trailingIcon == DefaultTrailingIcon) {
        {
            if (value.isNotEmpty()) {
                ComponeIconButton(
                    imageVector = if (isPassword) {
                        if (showPassword) Icons.Rounded.Visibility
                        else Icons.Rounded.VisibilityOff
                    } else Icons.Rounded.Close,
                    contentDescription = if (isPassword) {
                        if (showPassword) stringResource(Res.string.password_visibility_off)
                        else stringResource(Res.string.password_visibility_on)
                    } else stringResource(Res.string.clear_input_text),
                    onClick = {
                        if (isPassword) {
                            showPassword = !showPassword
                        } else if (!readOnly) {
                            onValueChange("")
                        }
                    }
                )
            } else {
                ComponeIconButton(
                    imageVector = Icons.Rounded.ContentPaste,
                    contentDescription = stringResource(Res.string.paste),
                    tint = MaterialTheme.colorScheme.primary,
                    onClick = {
                        scope.launch { onValueChange(clipboard.getText()?.toString().orEmpty()) }
                    }
                )
            }
        }
    } else {
        trailingIcon
    }

    when (style) {
        ComponeTextFieldStyle.Normal -> TextField(
            modifier = newModifier,
            maxLines = maxLines,
            readOnly = readOnly,
            enabled = enabled,
            value = value,
            label = newLabel,
            onValueChange = newOnValueChange,
            visualTransformation = newVisualTransformation,
            placeholder = newPlaceholder,
            isError = errorMessage.isNotEmpty(),
            singleLine = singleLine,
            trailingIcon = newTrailingIcon,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            colors = colors,
        )

        ComponeTextFieldStyle.Outlined -> OutlinedTextField(
            modifier = newModifier,
            maxLines = maxLines,
            readOnly = readOnly,
            enabled = enabled,
            value = value,
            label = newLabel,
            onValueChange = newOnValueChange,
            visualTransformation = newVisualTransformation,
            placeholder = newPlaceholder,
            isError = errorMessage.isNotEmpty(),
            singleLine = singleLine,
            trailingIcon = newTrailingIcon,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            colors = colors,
        )
    }
}
