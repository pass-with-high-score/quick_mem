package com.pwhs.quickmem.presentation.auth.social

import com.pwhs.quickmem.core.data.AuthProvider
import com.pwhs.quickmem.core.data.UserRole

data class AuthSocialUiState(
    val isLoading: Boolean = false,
    val error: String = "",
    val provider: AuthProvider? = null,
    val email: String = "",
    val birthDay: String = "",
    val avatarUrl: String = "",
    val role: UserRole = UserRole.Student,
    val fullName: String = "",
    val token: String = "",
)