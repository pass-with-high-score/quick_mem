package com.pwhs.quickmem.presentation.app.user_detail

sealed class UserDetailUiEvent {
    data class ShowError(val message: String) : UserDetailUiEvent()
    data object Refresh : UserDetailUiEvent()
}