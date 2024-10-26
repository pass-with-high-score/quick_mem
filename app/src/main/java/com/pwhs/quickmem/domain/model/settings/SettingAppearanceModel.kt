package com.pwhs.quickmem.domain.model.settings

import androidx.compose.ui.graphics.Color

data class SettingAppearanceModel(
    val id: Int = 0,
    val name: String = "",
    val color: Color? = null,
    val description: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
) {
    companion object {
        val defaultSettings = listOf(
            SettingAppearanceModel(
                1,
                "Device theme",
                color = Color(0xFF000000),
                description = "Device theme"
            ),
            SettingAppearanceModel(
                2,
                "Dark theme",
                color = Color(0xFF000000),
                description = "Dark theme"
            ),
            SettingAppearanceModel(
                3,
                "Light theme",
                color = Color(0xFF000000),
                description = "Light theme"
            )
        )
    }
}
