package com.pwhs.quickmem.domain.model.settings

import androidx.compose.ui.graphics.Color

data class SettingModel(
    val id: Int = 0,
    val name: String = "",
    val color: Color? = null,
    val description: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
) {
    companion object {
        val defaultSettings = listOf(
            SettingModel(
                1,
                "General",
                color = Color(0xFF000000),
                description = "General settings for the application"
            ),
            SettingModel(
                2,
                "Privacy",
                color = Color(0xFF000000),
                description = "Privacy settings for managing user data"
            ),
            SettingModel(
                3,
                "Notifications",
                color = Color(0xFF000000),
                description = "Manage app notifications"
            ),
            SettingModel(
                4,
                "Account",
                color = Color(0xFF000000),
                description = "Account settings for profile and security"
            ),
            SettingModel(
                5,
                "Language",
                color = Color(0xFF000000),
                description = "Set application language"
            ),
            SettingModel(
                6,
                "Help & Support",
                color = Color(0xFF000000),
                description = "Get help and support"
            )
        )
    }
}
