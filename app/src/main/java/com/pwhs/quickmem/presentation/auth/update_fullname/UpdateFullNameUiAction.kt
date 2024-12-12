package com.pwhs.quickmem.presentation.auth.update_fullname

sealed class UpdateFullNameUiAction{
    data class FullNameChanged(val fullname: String) : UpdateFullNameUiAction()
    data object Submit : UpdateFullNameUiAction()
}