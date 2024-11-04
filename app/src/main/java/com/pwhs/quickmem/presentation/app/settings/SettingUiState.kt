package com.pwhs.quickmem.presentation.app.settings

data class SettingUiState(
    val canChangeInfo: Boolean = false,
    val password: String = "",
    val errorMessage: String = "",
    val isLoading: Boolean = false,
    val showBottomSheet: Boolean = false,
    val userId: String = "",
    val fullName: String = "",
    val username: String = "",
    val email: String = "",
    val changeType: SettingChangeValueEnum = SettingChangeValueEnum.NONE,
)
