package com.pwhs.quickmem.util

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

fun Color.gradientBackground(): Brush {
    return Brush.verticalGradient(
        colors = listOf(
            this.copy(alpha = 0.8f),
            this.copy(alpha = 0.6f),
            this.copy(alpha = 0.4f),
            this.copy(alpha = 0.2f),
            this.copy(alpha = 0.0f)
        )
    )
}