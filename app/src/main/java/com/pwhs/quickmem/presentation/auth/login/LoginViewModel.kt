package com.pwhs.quickmem.presentation.auth.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val application: Application
) : AndroidViewModel(application) {
    fun loginWithGoogle() {
        viewModelScope.launch {
           Timber.d("Login with Google")
        }
    }

    fun loginWithFacebook() {
        viewModelScope.launch {
            Timber.d("Login with Facebook")
        }
    }
}