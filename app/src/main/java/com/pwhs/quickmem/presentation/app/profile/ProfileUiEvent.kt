package com.pwhs.quickmem.presentation.app.profile

sealed class ProfileUiEvent {
    data class LoadProfile(val userId: String) : ProfileUiEvent()
    data object Logout : ProfileUiEvent()
}