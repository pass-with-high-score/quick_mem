package com.pwhs.quickmem.core.datastore

import androidx.datastore.preferences.core.stringPreferencesKey

object DataStoreKeys {
    val ACCESS_TOKEN = stringPreferencesKey("ACCESS_TOKEN")
    val REFRESH_TOKEN = stringPreferencesKey("REFRESH_TOKEN")
}