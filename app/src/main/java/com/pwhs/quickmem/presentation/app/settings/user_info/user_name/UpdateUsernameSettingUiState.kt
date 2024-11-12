package com.pwhs.quickmem.presentation.app.settings.user_info.user_name

data class UpdateUsernameSettingUiState(
    val id: String = "",
    val username: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String = ""
)