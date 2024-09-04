package com.pwhs.quickmem.core.utils

import android.content.Context
import android.content.SharedPreferences

object SharedPrefUtils {
    private const val PREF_NAME = "com.pwhs.quickmem.pref"

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveString(context: Context, key: String, value: String) {
        val editor = getSharedPreferences(context).edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getString(context: Context, key: String, defaultValue: String? = null): String? {
        return getSharedPreferences(context).getString(key, defaultValue)
    }

    fun saveInt(context: Context, key: String, value: Int) {
        val editor = getSharedPreferences(context).edit()
        editor.putInt(key, value)
        editor.apply()
    }

    fun getInt(context: Context, key: String, defaultValue: Int = 0): Int {
        return getSharedPreferences(context).getInt(key, defaultValue)
    }

    fun saveBoolean(context: Context, key: String, value: Boolean) {
        val editor = getSharedPreferences(context).edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getBoolean(context: Context, key: String, defaultValue: Boolean = false): Boolean {
        return getSharedPreferences(context).getBoolean(key, defaultValue)
    }

    fun clear(context: Context) {
        val editor = getSharedPreferences(context).edit()
        editor.clear()
        editor.apply()
    }

}