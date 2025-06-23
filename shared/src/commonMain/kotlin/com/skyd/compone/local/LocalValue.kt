package com.skyd.compone.local

import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController

val LocalNavController = compositionLocalOf<NavHostController> {
    error("LocalNavController not initialized!")
}

val LocalGlobalNavController = compositionLocalOf<NavHostController> {
    error("LocalGlobalNavController not initialized!")
}