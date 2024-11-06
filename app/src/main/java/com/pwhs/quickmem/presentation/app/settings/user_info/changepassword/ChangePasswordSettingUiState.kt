package com.pwhs.quickmem.presentation.app.settings.user_info.changepassword

data class ChangePasswordSettingUiState(
    val id: String ="",
    val currentPassword: String = "",
    val newPassword: String = "",
    val confirmPassword: String = "",
    val errorMessage: String = "",
    val isLoading: Boolean = false
)
