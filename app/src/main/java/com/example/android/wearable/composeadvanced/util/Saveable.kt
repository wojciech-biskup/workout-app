package com.example.android.wearable.composeadvanced.util

import android.os.Bundle
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import androidx.compose.runtime.saveable.autoSaver
import androidx.core.os.bundleOf
import androidx.lifecycle.SavedStateHandle

fun <T : Any> SavedStateHandle.saveable(
    key: String,
    saver: Saver<T, out Any> = autoSaver(),
    init: () -> T
): T {
    @Suppress("UNCHECKED_CAST")
    saver as Saver<T, Any>
    val value = get<Bundle?>(key)?.get("value")?.let(saver::restore) ?: init()

    setSavedStateProvider(key) {
        bundleOf("value" to with(saver) { SaverScope { true }.save(value) })
    }
    return value
}
