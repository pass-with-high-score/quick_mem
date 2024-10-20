package com.pwhs.quickmem.presentation.app.study_set.detail.progress

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun StudySetDonutChart(
    modifier: Modifier = Modifier,
    color: Color = Color(0xFF4CAF50),
    studySetsNotLearn: Int = 0,
    studySetsStillLearn: Int = 0,
    studySetsMastered: Int = 0
) {
    val total = studySetsNotLearn + studySetsStillLearn + studySetsMastered
    val percentages = listOf(
        studySetsNotLearn.toFloat() / total,
        studySetsStillLearn.toFloat() / total,
        studySetsMastered.toFloat() / total
    )

    val colors = listOf(
        color.copy(alpha = 0.5f),
        color.copy(alpha = 0.8f),
        color
    )

    Surface(
        modifier = modifier
            .size(220.dp)
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
                percentages.forEachIndexed { index, percentage ->
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

                drawCircle(
                    color = Color.White,
                    radius = radius - strokeWidth / 2
                )
            }

            Text(
                text = "${(studySetsMastered.toFloat() / total * 100).toInt()} %",
                style = MaterialTheme.typography.titleLarge,
                color = color,
                maxLines = 1
            )
        }
    }
}

@Preview
@Composable
private fun StudySetDonutChartPreview() {
    MaterialTheme {
        Surface(
            modifier = Modifier.padding(16.dp),
            color = Color(0xFFF5F5F5)
        ) {
            StudySetDonutChart()
        }
    }
}