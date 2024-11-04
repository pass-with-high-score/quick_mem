package com.pwhs.quickmem.presentation.app.classes.detail

data class ClassDetailUiState(
    val joinClassCode: String = "",
    val isLogin: Boolean = false,
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val isLoading: Boolean = false,
)