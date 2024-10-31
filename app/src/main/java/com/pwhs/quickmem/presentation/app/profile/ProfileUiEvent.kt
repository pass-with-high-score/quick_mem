package com.pwhs.quickmem.presentation.app.profile


sealed class ProfileUiEvent {
    data object Loading : ProfileUiEvent()
    data object LoadingSuccess : ProfileUiEvent()
    data class ShowError(val message: String) : ProfileUiEvent() {
        init {
            require(message.isNotBlank()) { "Error message cannot be blank" }
        }
    }
}