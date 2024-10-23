package com.pwhs.quickmem.domain.model.status

import android.content.Context
import android.content.SharedPreferences

fun saveUserName(context: Context, name: String) {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    sharedPreferences.edit().putString("user_name", name).apply()
}

fun getUserName(context: Context): String? {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    return sharedPreferences.getString("user_name", "")
}
