package com.pwhs.quickmem.presentation.app.settings.user_info.change_password

sealed class ChangePasswordSettingUiAction {
    data class OnCurrentPasswordChanged(val currentPassword: String) :
        ChangePasswordSettingUiAction()

    data class OnNewPasswordChanged(val newPassword: String) : ChangePasswordSettingUiAction()
    data class OnConfirmPasswordChanged(val confirmPassword: String) :
        ChangePasswordSettingUiAction()

    data object OnSaveClicked : ChangePasswordSettingUiAction()
}