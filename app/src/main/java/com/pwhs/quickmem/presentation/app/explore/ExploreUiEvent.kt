package com.pwhs.quickmem.presentation.app.explore

sealed class ExploreUiEvent {
    data class Error(val message: String) : ExploreUiEvent()
}