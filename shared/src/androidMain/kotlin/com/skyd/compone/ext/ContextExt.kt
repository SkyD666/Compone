package com.skyd.compone.ext

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper

val Context.activity: Activity
    get() {
        return tryActivity ?: error("Can't find activity: $this")
    }

@get:JvmName("tryActivity")
val Context.tryActivity: Activity?
    get() {
        var ctx = this
        while (ctx is ContextWrapper) {
            if (ctx is Activity) {
                return ctx
            }
            ctx = ctx.baseContext
        }
        return null
    }