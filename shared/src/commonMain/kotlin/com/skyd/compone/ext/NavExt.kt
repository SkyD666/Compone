package com.skyd.compone.ext

import androidx.compose.material3.adaptive.navigation3.ListDetailSceneStrategy

val ListDetailSceneStrategy<*>.isSinglePane: Boolean
    get() = directive.maxHorizontalPartitions == 1