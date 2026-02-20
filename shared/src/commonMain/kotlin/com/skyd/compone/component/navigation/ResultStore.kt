package com.skyd.compone.component.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable

val LocalResultStore = compositionLocalOf<ResultStore> {
    error("LocalResultStore not initialized!")
}

/**
 * Provides a [ResultStore] that will be remembered across configuration changes.
 */
@Composable
fun rememberResultStore(): ResultStore {
    return rememberSaveable(saver = resultStoreSaver()) { ResultStore() }
}

/**
 * A store for passing results between multiple sets of screens.
 *
 * It provides a solution for state based results.
 */
class ResultStore {

    /**
     * Map from the result key to a mutable state of the result.
     */
    val resultStateMap = mutableStateMapOf<String, MutableState<Any?>>()

    /**
     * Retrieves the current result of the given resultKey.
     */
    inline fun <reified T> getResult(resultKey: String = T::class.toString()) =
        resultStateMap[resultKey]?.value as T

    /**
     * Sets the result for the given resultKey.
     */
    inline fun <reified T> setResult(resultKey: String = T::class.toString(), result: T) {
        resultStateMap[resultKey] = mutableStateOf(result)
    }

    /**
     * Removes all results associated with the given key from the store.
     */
    inline fun <reified T> removeResult(resultKey: String = T::class.toString()) {
        resultStateMap.remove(resultKey)
    }
}

/** Saver to save and restore the NavController across config change and process death. */
private fun resultStoreSaver(): Saver<ResultStore, *> = Saver(
    save = { store -> store.resultStateMap.mapValues { it.value.value } },
    restore = { saveable ->
        ResultStore().apply {
            resultStateMap.putAll(saveable.mapValues { mutableStateOf(it) })
        }
    },
)