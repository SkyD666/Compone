package com.skyd.compone.component.menu

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material3.DropdownMenuGroup
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.DropdownMenuPopup
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.skyd.compone.component.suspendString

@Composable
fun <T> ListMenu(
    expanded: Boolean,
    values: Collection<T>,
    displayName: (T) -> String,
    leadingIcon: @Composable ((T) -> Unit)? = null,
    onClick: (T) -> Unit,
    onDismissRequest: () -> Unit,
) {
    DropdownMenuPopup(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
    ) {
        DropdownMenuGroup(shapes = MenuDefaults.groupShape(0, 1)) {
            values.forEachIndexed { index, v ->
                DropdownMenuItem(
                    text = { Text(text = displayName(v)) },
                    shape = MenuDefaults.itemShape(index, values.size).shape,
                    leadingIcon = if (leadingIcon == null) null else {
                        { leadingIcon.invoke(v) }
                    },
                    onClick = {
                        onClick(v)
                        onDismissRequest()
                    },
                )
            }
        }
    }
}

@Composable
fun <T> CheckableListMenu(
    expanded: Boolean,
    current: T,
    values: Collection<T>,
    displayName: suspend (T) -> String,
    onChecked: (T) -> Unit,
    onDismissRequest: () -> Unit,
) {
    DropdownMenuPopup(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
    ) {
        DropdownMenuGroup(shapes = MenuDefaults.groupShape(0, 1)) {
            values.forEachIndexed { index, v ->
                DropdownMenuItem(
                    checked = current == v,
                    onCheckedChange = {
                        onChecked(v)
                        onDismissRequest()
                    },
                    text = { Text(text = suspendString { displayName(v) }) },
                    shapes = MenuDefaults.itemShape(index, values.size),
                    checkedLeadingIcon = {
                        Icon(imageVector = Icons.Outlined.Done, contentDescription = null)
                    },
                )
            }
        }
    }
}