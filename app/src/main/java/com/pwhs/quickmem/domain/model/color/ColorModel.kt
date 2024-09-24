package com.pwhs.quickmem.domain.model.color

import androidx.compose.ui.graphics.Color

data class ColorModel(
    val id: Int,
    val name: String,
    val hexValue: String,
    val createdAt: String? = null,
    val updatedAt: String? = null
) {


    // list default colors
    companion object {
        val defaultColors = listOf(
            ColorModel(
                1,
                "Blue",
                "#2970ff",
            ),
            ColorModel(
                2,
                "Cyan",
                "#1eb6d8",
            ),
            ColorModel(
                3,
                "Teal",
                "#2cbea7",
            ),
            ColorModel(
                4,
                "Lime",
                "#75cb32",
            ),
            ColorModel(
                5,
                "Gold",
                "#ecb220",
            ),
            ColorModel(
                6,
                "Pink",
                "#f65077",
            ),
            ColorModel(
                7,
                "Purple",
                "#d856f2",
            ),
            ColorModel(
                8,
                "Violet",
                "#876af8",
            )
        )
    }
}
