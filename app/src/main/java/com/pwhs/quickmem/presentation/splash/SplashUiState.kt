package com.pwhs.quickmem.presentation.splash

sealed class SplashUiState {
    data object Loading : SplashUiState()
    data object FirstRun : SplashUiState()
    data object NotFirstRun : SplashUiState()
    data object IsLoggedIn : SplashUiState()
    data object NotLoggedIn : SplashUiState()
    data class Error(val message: String) : SplashUiState()
}