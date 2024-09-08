package com.pwhs.quickmem.presentation.auth.signup

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val application: Application
) : AndroidViewModel(application) {
    suspend fun signupWithGoogle() {
        Timber.d("Signup with Google")
        authRepository.signupWithGoogle(application.applicationContext).collectLatest {
            when (it) {
                is Resources.Success -> {
                    Timber.d("User registered")
                }

                is Resources.Error -> {
                    Timber.e(it.message)
                }

                is Resources.Loading -> {
                    Timber.d("Loading")
                }
            }
        }
    }
}