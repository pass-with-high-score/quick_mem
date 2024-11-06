package com.pwhs.quickmem.presentation.app.settings.user_info.changepassword

data class ChangePasswordSettingArgs(
    val userId: String,
    val currentPassword: String = "",
    val newPassword: String = "",
    val confirmPassword: String = ""
)
