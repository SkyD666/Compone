package com.skyd.compone.component.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

val LocalNavBackStack = compositionLocalOf<NavBackStack<NavKey>> {
    error("LocalNavBackStack not initialized!")
}

val LocalCurrentNavBackStack = compositionLocalOf<CurrentNavBackStack> {
    error("LocalCurrentNavBackStack not initialized!")
}

@Composable
fun newCurrentNavBackStack(
    base: NavBackStack<NavKey>,
    parent: CurrentNavBackStack? = LocalCurrentNavBackStack.current,
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
