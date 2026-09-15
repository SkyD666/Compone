package com.skyd.compone.component.navigation

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class ResultStoreTest {
    @Test
    fun storesAndRemovesResultsByKey() {
        val store = ResultStore()

        store.setResult("count", 42)
        assertEquals(42, store.getResult<Int>("count"))

        store.removeResult<Int>("count")
        assertNull(store.resultStateMap["count"])
    }

    @Test
    fun replacingAResultUpdatesTheValue() {
        val store = ResultStore()

        store.setResult("message", "first")
        store.setResult("message", "second")

        assertEquals("second", store.getResult<String>("message"))
    }
}
