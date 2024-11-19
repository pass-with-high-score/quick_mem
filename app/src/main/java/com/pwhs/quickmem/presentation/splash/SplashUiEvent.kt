package com.pwhs.quickmem.presentation.splash

sealed class SplashUiEvent {
    data object FirstRun : SplashUiEvent()
    data object NotFirstRun : SplashUiEvent()
    data object IsLoggedIn : SplashUiEvent()
    data object NotLoggedIn : SplashUiEvent()
    data object NoInternet : SplashUiEvent()
}