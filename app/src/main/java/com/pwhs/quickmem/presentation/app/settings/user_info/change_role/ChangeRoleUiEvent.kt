package com.pwhs.quickmem.presentation.app.settings.user_info.change_role

sealed class ChangeRoleUiEvent {
    data class ShowError(val message: String) : ChangeRoleUiEvent()
    data object RoleChangedSuccessfully : ChangeRoleUiEvent()
    data class ShowUnderageDialog(val message: String) : ChangeRoleUiEvent()
}

