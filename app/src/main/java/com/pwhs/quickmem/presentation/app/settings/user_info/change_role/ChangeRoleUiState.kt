package com.pwhs.quickmem.presentation.app.settings.user_info.change_role

data class ChangeRoleUiState(
    val isLoading: Boolean = false,
    val role: String = "",
    val errorMessage: String? = null,
    val userId: String = "",
    val birthday: String = ""
)
