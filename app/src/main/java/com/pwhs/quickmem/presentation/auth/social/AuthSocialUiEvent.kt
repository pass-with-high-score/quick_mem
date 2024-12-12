package com.pwhs.quickmem.presentation.auth.social

import androidx.annotation.StringRes

sealed class AuthSocialUiEvent {
    data object SignUpSuccess : AuthSocialUiEvent()
    data class SignUpFailure(@StringRes val message: Int) : AuthSocialUiEvent()
}