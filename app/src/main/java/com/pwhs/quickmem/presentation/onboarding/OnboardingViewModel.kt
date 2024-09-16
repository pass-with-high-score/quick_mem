package com.pwhs.quickmem.presentation.onboarding

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.core.datastore.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val tokenManager: TokenManager,
    private val application: Application
) : AndroidViewModel(application) {
    init {
        checkAuth()
    }

    private fun checkAuth() {
        Timber.d("Checking auth")
        viewModelScope.launch {
            tokenManager.accessToken.collect {
                Timber.d("Access token: ${it?.length}")
            }
        }
    }
}