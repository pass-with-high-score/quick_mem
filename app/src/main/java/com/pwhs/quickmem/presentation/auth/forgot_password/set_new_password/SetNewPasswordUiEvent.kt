package com.pwhs.quickmem.presentation.auth.forgot_password.set_new_password

sealed class SetNewPasswordUiEvent {
    data object None : SetNewPasswordUiEvent()
    data object ResetSuccess : SetNewPasswordUiEvent()
    data object ResetFailure : SetNewPasswordUiEvent()
}
