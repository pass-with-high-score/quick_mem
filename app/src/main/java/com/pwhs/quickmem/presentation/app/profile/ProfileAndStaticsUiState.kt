package com.pwhs.quickmem.presentation.app.profile

data class ProfileAndStaticsUiState(
    val profileData: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)