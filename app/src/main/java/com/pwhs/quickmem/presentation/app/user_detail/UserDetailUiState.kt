package com.pwhs.quickmem.presentation.app.user_detail

data class UserDetailUiState(
    val isLoading: Boolean = false,
    val userId: String = "",
    val userName: String = "",
    val userEmail: String = "",
    val avatarUrl: String = "",
    val errorMessage: String? = null
)
