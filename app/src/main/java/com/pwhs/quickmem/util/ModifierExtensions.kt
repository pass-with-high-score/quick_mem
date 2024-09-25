package com.pwhs.quickmem.util

import android.graphics.Paint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.zIndex

fun Modifier.gradientBackground(): Modifier {
    return this.background(
        brush = Brush.verticalGradient(
            colors = listOf(
                Color(0xFF87A6F3),
                Color(0x87A6F3FF),
                Color(0xFFFFFFFF)
            ),
            tileMode = TileMode.Mirror
        )
    )
}

fun Modifier.bottomBorder(strokeWidth: Dp, color: Color) = composed(
    factory = {
        val density = LocalDensity.current
        val strokeWidthPx = density.run { strokeWidth.toPx() }

        Modifier.drawBehind {
            val width = size.width
            val height = size.height - strokeWidthPx / 2

            drawLine(
                color = color,
                start = Offset(x = 0f, y = height),
                end = Offset(x = width, y = height),
                strokeWidth = strokeWidthPx
            )
        }
    }
)

fun Modifier.loadingOverlay(isLoading: Boolean): Modifier = this.then(
    if (isLoading) {
        Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
            .zIndex(1f)
            .drawWithContent {
                this@drawWithContent.drawContent()
                drawRect(Color.Black.copy(alpha = 0.5f))
                drawIntoCanvas { canvas ->
                    val centerX = size.width / 2
                    val centerY = size.height / 2
                    // Vẽ CircularProgressIndicator ở giữa
                    val radius = 50f
                    val paint = Paint().apply {
                        isAntiAlias = true
                        style = Paint.Style.STROKE
                        strokeWidth = 10f
                        color = Color.White.toArgb()
                    }
                    canvas.nativeCanvas.drawCircle(centerX, centerY, radius, paint)
                }
            }
    } else {
        Modifier
    }
)