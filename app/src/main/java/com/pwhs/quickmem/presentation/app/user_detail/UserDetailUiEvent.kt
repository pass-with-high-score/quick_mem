package com.pwhs.quickmem.presentation.app.user_detail

sealed class UserDetailUiEvent {
    data object Refresh : UserDetailUiEvent()
    data class ShowError(val message: String) : UserDetailUiEvent()
}