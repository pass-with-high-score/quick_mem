package com.pwhs.quickmem.presentation.auth.social

sealed class AuthSocialUiEvent {
    data class OnEmailChanged(val email: String) : AuthSocialUiEvent()
    data class OnBirthDayChanged(val birthDay: String) : AuthSocialUiEvent()
    data class OnAvatarUrlChanged(val avatarUrl: String) : AuthSocialUiEvent()
    data class OnRoleChanged(val role: String) : AuthSocialUiEvent()
    data class OnNameChanged(val name: String) : AuthSocialUiEvent()
    data object Register : AuthSocialUiEvent()
    data object None : AuthSocialUiEvent()
    data object SignUpSuccess : AuthSocialUiEvent()
    data object SignUpFailure : AuthSocialUiEvent()
}