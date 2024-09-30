package com.pwhs.quickmem.presentation.app.home

sealed class HomeUIAction {
    data object NavigateToSearch : HomeUIAction()
    data object NavigateFreeTrial : HomeUIAction()
    data object NavigateShowMoreSets : HomeUIAction()
    data object NavigateShowMoreFolder : HomeUIAction()
    data object NavigateShowMoreClasses : HomeUIAction()
    data object NavigateShowMoreCategories : HomeUIAction()
}