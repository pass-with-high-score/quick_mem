package com.pwhs.quickmem.core.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.pwhs.quickmem.util.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber

class AppManager(private val context: Context) {
    companion object {
        val IS_FIRST_RUN = booleanPreferencesKey("IS_FIRST_RUN")
        val IS_LOGGED_IN = booleanPreferencesKey("IS_LOGGED_IN")
        val USER_ID = stringPreferencesKey("USER_ID")
        val USER_FULL_NAME = stringPreferencesKey("USER_FULL_NAME")
        val USER_AVATAR = stringPreferencesKey("USER_AVATAR")
        val USER_NAME = stringPreferencesKey("USER_NAME")
        val RESET_EMAIL = stringPreferencesKey("RESET_EMAIL")
        val RESET_TOKEN = stringPreferencesKey("RESET_TOKEN")
    }

    val isFirstRun: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[IS_FIRST_RUN] ?: true
        }
    val isLoggedIn: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[IS_LOGGED_IN] ?: false
        }
    val userId: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[USER_ID] ?: ""
        }
    val userFullName: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[USER_FULL_NAME] ?: ""
        }
    val userAvatar: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[USER_AVATAR] ?: ""
        }
    val userName: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[USER_NAME] ?: ""
        }

    val resetEmail: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[RESET_EMAIL] ?: ""
        }

    val resetToken: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[RESET_TOKEN] ?: ""
        }

    suspend fun saveIsFirstRun(isFirstRun: Boolean) {
        Timber.d("Saving is first run: $isFirstRun")
        context.dataStore.edit { preferences ->
            preferences[IS_FIRST_RUN] = isFirstRun
        }
    }

    suspend fun saveIsLoggedIn(isLoggedIn: Boolean) {
        Timber.d("Saving is logged in: $isLoggedIn")
        context.dataStore.edit { preferences ->
            preferences[IS_LOGGED_IN] = isLoggedIn
        }
    }

    suspend fun saveUserId(userId: String) {
        Timber.d("Saving user id: $userId")
        context.dataStore.edit { preferences ->
            preferences[USER_ID] = userId
        }
    }

    suspend fun saveUserFullName(userFullName: String) {
        Timber.d("Saving user full name: $userFullName")
        context.dataStore.edit { preferences ->
            preferences[USER_FULL_NAME] = userFullName
        }
    }

    suspend fun saveUserAvatar(userAvatar: String) {
        Timber.d("Saving user avatar: $userAvatar")
        context.dataStore.edit { preferences ->
            preferences[USER_AVATAR] = userAvatar
        }
    }

    suspend fun saveUserName(userName: String) {
        Timber.d("Saving user name: $userName")
        context.dataStore.edit { preferences ->
            preferences[USER_NAME] = userName
        }
    }

    suspend fun saveResetEmail(email: String) {
        Timber.d("Saving reset email: $email")
        context.dataStore.edit { preferences ->
            preferences[RESET_EMAIL] = email
        }
    }

    suspend fun saveResetToken(token: String) {
        Timber.d("Saving reset token: $token")
        context.dataStore.edit { preferences ->
            preferences[RESET_TOKEN] = token
        }
    }

    suspend fun clearAllData() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}