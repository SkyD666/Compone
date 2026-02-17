package com.skyd.compone.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.LargeFlexibleTopAppBar
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.skyd.compone.ext.popBackStackWithLifecycle
import com.skyd.compone.local.LocalGlobalNavController
import com.skyd.compone.local.LocalNavController
import compone.shared.generated.resources.Res
import compone.shared.generated.resources.back
import org.jetbrains.compose.resources.stringResource

enum class ComponeTopBarStyle {
    Small, LargeFlexible, CenterAligned
}

@Composable
fun ComponeTopBar(
    style: ComponeTopBarStyle = ComponeTopBarStyle.Small,
    title: @Composable () -> Unit,
    contentPadding: @Composable () -> PaddingValues = { PaddingValues() },
    navigationIcon: @Composable () -> Unit = { BackIcon() },
    windowInsets: WindowInsets = WindowInsets.safeDrawing.only(
        WindowInsetsSides.Horizontal + WindowInsetsSides.Top
    ),
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(),
    actions: @Composable RowScope.() -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    val topBarModifier = Modifier.padding(contentPadding())
    when (style) {
        ComponeTopBarStyle.Small -> {
            TopAppBar(
                title = title,
                modifier = topBarModifier,
                navigationIcon = navigationIcon,
                actions = actions,
                windowInsets = windowInsets,
                colors = colors,
                scrollBehavior = scrollBehavior
            )
        }

        ComponeTopBarStyle.LargeFlexible -> {
            LargeFlexibleTopAppBar(
                modifier = topBarModifier,
                title = title,
                navigationIcon = navigationIcon,
                actions = actions,
                windowInsets = windowInsets,
                colors = colors,
                scrollBehavior = scrollBehavior
            )
        }

        ComponeTopBarStyle.CenterAligned -> {
            CenterAlignedTopAppBar(
                modifier = topBarModifier,
                title = title,
                navigationIcon = navigationIcon,
                actions = actions,
                windowInsets = windowInsets,
                colors = colors,
                scrollBehavior = scrollBehavior
            )
        }
    }
}

@Composable
expect fun onEmptyPopBackStack(): () -> Unit

@Composable
fun BackInvoker(): () -> Unit {
    val navController = LocalNavController.current
    val globalNavController = LocalGlobalNavController.current
    val onEmptyPopBackStack = onEmptyPopBackStack()
    return {
        if (!navController.popBackStackWithLifecycle() && globalNavController == navController) {
            onEmptyPopBackStack.invoke()
        }
    }
}

@Composable
fun BackIcon() {
    val backInvoker = BackInvoker()
    BackIcon { backInvoker.invoke() }
}

val DefaultBackClick = { }

@Composable
fun BackIcon(onClick: () -> Unit = {}) {
    ComponeIconButton(
        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
        contentDescription = stringResource(Res.string.back),
        onClick = onClick
    )
}

expect fun Modifier.pointerOnBack(onBack: (() -> Unit)? = null): Modifier