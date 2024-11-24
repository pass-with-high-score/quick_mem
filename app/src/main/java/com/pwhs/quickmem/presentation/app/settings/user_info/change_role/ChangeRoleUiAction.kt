package com.pwhs.quickmem.presentation.app.settings.user_info.change_role

import com.pwhs.quickmem.core.data.enums.UserRole

sealed class ChangeRoleUiAction {
    data class SelectRole(val role: UserRole) : ChangeRoleUiAction()
    data object SaveRole : ChangeRoleUiAction()
}

