package com.pwhs.quickmem.presentation.app.settings.user_info.change_role

sealed class ChangeRoleUiAction {
    data class SelectRole(val role: String) : ChangeRoleUiAction()
    data object SaveRole : ChangeRoleUiAction()
}

