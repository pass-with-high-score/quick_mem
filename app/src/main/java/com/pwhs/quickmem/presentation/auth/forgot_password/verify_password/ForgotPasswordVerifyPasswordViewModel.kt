package com.pwhs.quickmem.presentation.auth.forgot_password.verify_password

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ForgotPasswordVerifyPasswordViewModel : ViewModel() {
    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword: StateFlow<String> = _confirmPassword

    private val _passwordError = MutableStateFlow(false)
    val passwordError: StateFlow<Boolean> = _passwordError

    private val _confirmPasswordError = MutableStateFlow(false)
    val confirmPasswordError: StateFlow<Boolean> = _confirmPasswordError

    fun onPasswordChanged(newPassword: String) {
        _password.value = newPassword
        _passwordError.value = newPassword.isBlank()
    }

    fun onConfirmPasswordChanged(newConfirmPassword: String) {
        _confirmPassword.value = newConfirmPassword
        _confirmPasswordError.value = newConfirmPassword != _password.value
    }

    fun submit() {
        _passwordError.value = _password.value.isBlank()
        _confirmPasswordError.value = _confirmPassword.value != _password.value
        if (!_passwordError.value && !_confirmPasswordError.value) {
            //
            println("Password reset successful")
        }
    }
}
