package com.pwhs.quickmem.presentation.onboarding

sealed class OnboardingUiState {
    data object Loading : OnboardingUiState()
    data object FirstRun : OnboardingUiState()
    data object NotFirstRun : OnboardingUiState()
    data object IsLoggedIn : OnboardingUiState()
    data object NotLoggedIn : OnboardingUiState()
    data class Error(val message: String) : OnboardingUiState()
}