package com.pwhs.quickmem.presentation.auth.forgot_password.verify_code

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.pwhs.quickmem.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordVerifyCodeViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    application: Application
) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(ForgotPasswordVerifyCodeUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<ForgotPasswordVerifyCodeUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: ForgotPasswordVerifyCodeUiAction) {
        when (event) {
            is ForgotPasswordVerifyCodeUiAction.CodeChanged -> {
                if (event.code.isBlank()) {
                    _uiState.update {
                        it.copy(
                            code = event.code,
                            codeError = "Code cannot be empty"
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            code = event.code,
                            codeError = ""
                        )
                    }
                }
            }

            is ForgotPasswordVerifyCodeUiAction.VerifyCode -> {
                if (validateInput()) {
                    verifyCode()
                } else {
                    Toast.makeText(getApplication(), "Invalid input", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun verifyCode() {

    }

    private fun validateInput(): Boolean {
        var isValid = true

        if (uiState.value.code.isBlank()) {
            _uiState.update { it.copy(codeError = "Code cannot be empty") }
            isValid = false
        } else {
            _uiState.update { it.copy(codeError = "") }
        }

        return isValid
    }

    override fun onCleared() {
        super.onCleared()
        _uiEvent.close()
    }
}
