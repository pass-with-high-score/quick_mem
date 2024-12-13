package com.pwhs.quickmem.presentation.app.study_set.detail.progress

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.pwhs.quickmem.R
import com.pwhs.quickmem.ui.theme.QuickMemTheme

@Composable
fun StudySetDonutChart(
    modifier: Modifier = Modifier,
    color: Color = Color(0xFF4CAF50),
    studySetsNotLearn: Int = 0,
    studySetsStillLearn: Int = 0,
    studySetsMastered: Int = 0,
    donutChartSize: Dp = 220.dp
) {
    val total = (studySetsNotLearn + studySetsStillLearn + studySetsMastered).coerceAtLeast(0)
    val percentages = listOf(
        studySetsMastered.coerceAtLeast(0).toFloat() / total,
        studySetsStillLearn.coerceAtLeast(0).toFloat() / total,
        studySetsNotLearn.coerceAtLeast(0).toFloat() / total,
    )

    val colors = listOf(
        color,
        color.copy(alpha = 0.6f),
        color.copy(alpha = 0.3f),
    )

    val animatedPercentage = percentages.map { target ->
        animateFloatAsState(
            targetValue = target,
            animationSpec = tween(durationMillis = 1000)
        ).value
    }

    Surface(
        modifier = modifier
            .size(donutChartSize)
            .padding(16.dp)
            .shadow(8.dp, shape = CircleShape),
        shape = CircleShape,
        color = Color.White,
        tonalElevation = 8.dp,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val size = size.minDimension
                val radius = size / 2
                val strokeWidth = 45f

                var startAngle = -90f
                if (total > 0) {
                    animatedPercentage.forEachIndexed { index, percentage ->
                        val sweepAngle = percentage * 360f
                        drawArc(
                            color = colors[index],
                            startAngle = startAngle,
                            sweepAngle = sweepAngle,
                            useCenter = false,
                            style = Stroke(width = strokeWidth)
                        )
                        startAngle += sweepAngle
                    }
                } else {
                    drawCircle(
                        color = color,
                        radius = radius - strokeWidth / 2,
                        center = center,
                        style = Stroke(width = strokeWidth),
                    )
                }

                drawOval(
                    color = Color.White,
                    topLeft = Offset(strokeWidth / 2, strokeWidth / 2),
                    size = Size(size - strokeWidth, size - strokeWidth)
                )

                drawArc(
                    color = Color.White,
                    startAngle = 0f,
                    sweepAngle = 360f,
                    useCenter = false,
                    style = Stroke(width = 1.dp.toPx())
                )

                drawCircle(
                    color = Color.White,
                    radius = radius - strokeWidth / 2
                )
            }

            if (studySetsMastered == total) {
                AnimatedVisibility(
                    visible = true,
                    enter = scaleIn() + fadeIn(),
                    exit = scaleOut() + fadeOut()
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = stringResource(R.string.txt_mastered),
                        tint = color,
                        modifier = Modifier.size(48.dp)
                    )
                }
            } else {
                val percentAnimationText by animateFloatAsState(
                    targetValue = (studySetsMastered.toFloat() / total * 100),
                    animationSpec = tween(
                        durationMillis = 1000,
                        easing = {
                            val x = it * 2 - 1
                            0.5f * (x * x * x + 1)
                        },
                    )
                )

                Text(
                    text = "${percentAnimationText.toInt()} %",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Black
                    ),
                    color = color,
                    maxLines = 1
                )
            }
        }
    }
}


@Preview
@Composable
private fun StudySetDonutChartPreview() {
    QuickMemTheme {
        Surface(
            modifier = Modifier.padding(16.dp),
            color = Color(0xFFF5F5F5)
        ) {
            StudySetDonutChart(
                studySetsNotLearn = 0,
                studySetsStillLearn = 0,
                studySetsMastered = 70
            )
        }
    }
}