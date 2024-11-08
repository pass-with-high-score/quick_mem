package com.pwhs.quickmem.presentation.app.user_detail

sealed class UserDetailUiAction {
    data object Refresh : UserDetailUiAction()
}
