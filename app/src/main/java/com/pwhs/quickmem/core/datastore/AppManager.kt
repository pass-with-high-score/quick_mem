package com.pwhs.quickmem.core.datastore

import android.content.Context
import android.util.Patterns
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.pwhs.quickmem.core.data.LanguageCode
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
        val USER_EMAIL = stringPreferencesKey("USER_EMAIL")
        val PUSH_NOTIFICATIONS = booleanPreferencesKey("PUSH_NOTIFICATIONS")
        val APP_PUSH_NOTIFICATIONS = booleanPreferencesKey("APP_PUSH_NOTIFICATIONS")
        val LANGUAGE_CODE = stringPreferencesKey("LANGUAGE_CODE")
        val USER_USERNAME = stringPreferencesKey("USER_USERNAME")
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
    val userEmail: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[USER_EMAIL] ?: ""
        }
    val userName: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[USER_NAME] ?: ""
        }
    val pushNotifications: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[PUSH_NOTIFICATIONS] ?: false
        }
    val appPushNotifications: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[APP_PUSH_NOTIFICATIONS] ?: false
        }
    val languageCode: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[LANGUAGE_CODE] ?: LanguageCode.EN.name.lowercase()
        }
    val usernameFlow: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[USER_USERNAME]
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

    suspend fun saveUsername(username: String) {
        Timber.d("Saving username: $username")
        context.dataStore.edit { preferences ->
            preferences[USER_USERNAME] = username
        }
    }

    suspend fun clearAllData() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    suspend fun saveUserEmail(email: String) {
        require(email.isNotEmpty()) { "Email cannot be empty" }
        require(Patterns.EMAIL_ADDRESS.matcher(email).matches()) { "Invalid email address" }
        context.dataStore.edit { preferences ->
            preferences[USER_EMAIL] = email
        }
    }

    suspend fun savePushNotifications(pushNotifications: Boolean) {
        Timber.d("Saving push notifications: $pushNotifications")
        context.dataStore.edit { preferences ->
            preferences[PUSH_NOTIFICATIONS] = pushNotifications
        }
    }

    suspend fun saveAppPushNotifications(appPushNotifications: Boolean) {
        Timber.d("Saving app push notifications: $appPushNotifications")
        context.dataStore.edit { preferences ->
            preferences[APP_PUSH_NOTIFICATIONS] = appPushNotifications
        }
    }

    suspend fun saveLanguageCode(languageCode: String) {
        Timber.d("Saving language code: $languageCode")
        context.dataStore.edit { preferences ->
            preferences[LANGUAGE_CODE] = languageCode
        }
    }
}