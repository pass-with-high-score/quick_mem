package com.pwhs.quickmem.presentation.auth.update_fullname

sealed class UpdateFullNameUIAction{
    data class FullNameChanged(val fullname: String) : UpdateFullNameUIAction()
    data object Submit : UpdateFullNameUIAction()
}