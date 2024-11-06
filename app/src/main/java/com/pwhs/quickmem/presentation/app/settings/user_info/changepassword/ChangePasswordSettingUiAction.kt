package com.pwhs.quickmem.presentation.app.settings.user_info.changepassword

sealed class ChangePasswordSettingUiAction {
    data class OnCurrentPasswordChanged(val currentPassword: String) : ChangePasswordSettingUiAction()
    data class OnNewPasswordChanged(val newPassword: String) : ChangePasswordSettingUiAction()
    data class OnConfirmPasswordChanged(val confirmPassword: String) : ChangePasswordSettingUiAction()
    object OnSaveClicked : ChangePasswordSettingUiAction()
}