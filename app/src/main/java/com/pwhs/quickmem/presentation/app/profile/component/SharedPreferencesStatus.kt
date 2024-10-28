package com.pwhs.quickmem.presentation.app.profile.component

import android.content.Context
import android.content.SharedPreferences
import com.pwhs.quickmem.domain.model.status.StatusModel

fun saveSelectedStatusId(context: Context, id: Int) {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("profile_prefs", Context.MODE_PRIVATE)
    sharedPreferences.edit().putInt("selected_status_id", id).apply()
}

fun getSelectedStatusId(context: Context): Int {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("profile_prefs", Context.MODE_PRIVATE)
    return sharedPreferences.getInt("selected_status_id", StatusModel.defaultStatuses.first().id)
}