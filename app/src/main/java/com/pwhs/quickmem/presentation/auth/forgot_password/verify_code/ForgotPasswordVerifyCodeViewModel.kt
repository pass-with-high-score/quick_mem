package com.pwhs.quickmem.presentation.auth.forgot_password.verify_code

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ForgotPasswordVerifyCodeViewModel : ViewModel() {
    private val _code = MutableStateFlow("")
    val code: StateFlow<String> = _code

    private val _codeError = MutableStateFlow(false)
    val codeError: StateFlow<Boolean> = _codeError

    fun onCodeChanged(newCode: String) {
        _code.value = newCode
        _codeError.value = newCode.isBlank()
    }

    fun verifyCode() {
        _codeError.value = _code.value.isBlank()
        if (!_codeError.value) {
            println("Code entered successfully")
            //
        }
    }
}
