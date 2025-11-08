package com.skyd.compone.component

import androidx.compose.runtime.Composable
import platform.Foundation.NSSelectorFromString
import platform.UIKit.UIApplication
import platform.UIKit.UIControl

@Composable
actual fun onEmptyPopBackStack(): () -> Unit {
    return {
        UIControl().sendAction(
            action = NSSelectorFromString("suspend"),
            to = UIApplication.sharedApplication,
            forEvent = null
        )
    }
}
