package com.pwhs.quickmem.presentation.auth.social

sealed class AuthSocialUiEvent {
    data object SignUpSuccess : AuthSocialUiEvent()
    data object SignUpFailure : AuthSocialUiEvent()
}