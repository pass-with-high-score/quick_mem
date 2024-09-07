package com.pwhs.quickmem.util

import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode

fun Modifier.gradientBackground(): Modifier {
    return this.background(
        brush = Brush.linearGradient(
            colors = listOf(
                Color(0xFF608dfd),
                Color(0xFFFFFFFF)
            ),
            tileMode = TileMode.Repeated
        )
    )
}