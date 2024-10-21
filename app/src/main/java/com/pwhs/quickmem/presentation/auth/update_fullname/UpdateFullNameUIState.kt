package com.pwhs.quickmem.presentation.auth.update_fullname

data class UpdateFullNameUIState(
    val fullName: String = "",
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null
)