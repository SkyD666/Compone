package com.skyd.compone.component.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey


val LocalNavBackStack = compositionLocalOf<CurrentNavBackStack> {
    error("LocalNavBackStack not initialized!")
}

val LocalGlobalNavBackStack = compositionLocalOf<CurrentNavBackStack> {
    error("LocalGlobalNavBackStack not initialized!")
}

@Composable
fun newNavBackStack(
    base: NavBackStack<NavKey>,
    parent: CurrentNavBackStack? = LocalNavBackStack.current,
): CurrentNavBackStack {
    return CurrentNavBackStack(
        base = base,
        parent = parent,
    )
}

class CurrentNavBackStack(
    val base: NavBackStack<NavKey>,
    val parent: CurrentNavBackStack?,
) : MutableList<NavKey> by base {
    fun removeLastOrNull(): NavKey? {
        return if (base.size <= 1) {
            parent?.removeLastOrNull()
        } else {
            base.removeLastOrNull()
        }
    }
}
