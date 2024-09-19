package com.pwhs.quickmem.presentation.auth.forgot_password.verify_email


import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ForgotPasswordVerifyEmailViewModel : ViewModel() {
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _emailError = MutableStateFlow(false)
    val emailError: StateFlow<Boolean> = _emailError

    fun onEmailChanged(newEmail: String) {
        _email.value = newEmail
        _emailError.value = !android.util.Patterns.EMAIL_ADDRESS.matcher(newEmail).matches()
    }

    fun resetPassword() {
        _emailError.value = _email.value.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(_email.value).matches()
        if (!_emailError.value) {
            //
            println("Email entered successfully")
        }
    }
}
