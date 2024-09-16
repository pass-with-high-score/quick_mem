package com.pwhs.quickmem.presentation.auth.signup

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    application: Application
) : AndroidViewModel(application) {
    fun signupWithGoogle() {
        Timber.d("Signup with Google")
        viewModelScope.launch {
            Timber.d("Signup with Google")
        }
    }

    fun signupWithFacebook() {
        viewModelScope.launch {
            Timber.d("Signup with Facebook")
        }
    }
}