package com.pwhs.quickmem.presentation.app.settings.user_info.change_password

import kotlinx.serialization.Serializable

@Serializable
data class ChangePasswordSettingArgs(
    val email: String,
)
