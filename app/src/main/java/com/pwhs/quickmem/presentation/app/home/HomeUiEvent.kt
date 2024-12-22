package com.pwhs.quickmem.presentation.app.home

sealed class HomeUiEvent {
    data object UnAuthorized : HomeUiEvent()
}