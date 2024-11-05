package com.pwhs.quickmem.presentation.app.settings.user_info.email

data class UpdateEmailSettingUiState(
    val id: String = "",
    val email: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String = ""
)
