package com.pwhs.quickmem.presentation.auth.update_fullname

sealed class UpdateFullNameUIEvent {
    data object UpdateSuccess : UpdateFullNameUIEvent()
    data class ShowError(val message: String) : UpdateFullNameUIEvent()
}