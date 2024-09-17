package com.pwhs.quickmem.presentation.onboarding

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.core.datastore.AppManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val appManager: AppManager,
    application: Application
) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow<OnboardingUiState>(OnboardingUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        checkAuth()
    }

    private fun checkAuth() {
        Timber.d("Checking auth")
        viewModelScope.launch {
            appManager.isLoggedIn.collect { isLoggedIn ->
                if (isLoggedIn) {
                    _uiState.value = OnboardingUiState.IsLoggedIn
                } else {
                    _uiState.value = OnboardingUiState.NotLoggedIn
                    checkFirstRun()
                }
            }
        }
    }

    private fun checkFirstRun() {
        Timber.d("Checking first run")
        viewModelScope.launch {
            appManager.isFirstRun.collect { isFirstRun ->
                if (isFirstRun) {
                    _uiState.value = OnboardingUiState.FirstRun
                } else {
                    _uiState.value = OnboardingUiState.NotFirstRun
                }
            }
        }
    }

    fun saveIsFirstRun(isFirstRun: Boolean) {
        viewModelScope.launch {
            appManager.saveIsFirstRun(isFirstRun)
        }
    }
}