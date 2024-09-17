package com.pwhs.quickmem.core.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.pwhs.quickmem.util.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber

class AppManager(private val context: Context) {
    companion object {
        val IS_FIRST_RUN = booleanPreferencesKey("IS_FIRST_RUN")
    }

    val isFirstRun: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[IS_FIRST_RUN] ?: true
        }

    suspend fun saveIsFirstRun(isFirstRun: Boolean) {
        Timber.d("Saving is first run: $isFirstRun")
        context.dataStore.edit { preferences ->
            preferences[IS_FIRST_RUN] = isFirstRun
        }
    }

    suspend fun clearAllData() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}