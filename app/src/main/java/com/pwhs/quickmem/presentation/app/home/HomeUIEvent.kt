package com.pwhs.quickmem.presentation.app.home

sealed class HomeUIEvent {
    data object NavigateToSearch : HomeUIEvent()
    data object NavigateFreeTrial : HomeUIEvent()
    data object NavigateShowMoreSets : HomeUIEvent()
    data object NavigateShowMoreFolder : HomeUIEvent()
    data object NavigateShowMoreClasses : HomeUIEvent()
    data object NavigateShowMoreCategories : HomeUIEvent()
}