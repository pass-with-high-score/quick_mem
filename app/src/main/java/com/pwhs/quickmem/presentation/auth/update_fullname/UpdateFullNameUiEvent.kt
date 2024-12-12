package com.pwhs.quickmem.presentation.auth.update_fullname

sealed class UpdateFullNameUiEvent {
    data object UpdateSuccess : UpdateFullNameUiEvent()
    data class ShowError(val message: String) : UpdateFullNameUiEvent()
}