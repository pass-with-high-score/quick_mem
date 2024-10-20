package com.pwhs.quickmem.presentation.app.study_set.detail.progress

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.ehsannarmani.compose_charts.PieChart
import ir.ehsannarmani.compose_charts.models.Pie

@Composable
fun ProgressTabScreen(
    modifier: Modifier = Modifier,
    totalStudySet: Int = 0,
    studySetsNotLearn: Int = 0,
    studySetsStillLearn: Int = 0,
    studySetsMastered: Int = 0,
    color: Color = MaterialTheme.colorScheme.primary
) {
    Scaffold { innerPadding ->
        Box(
            modifier = modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Your Progress", style = typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    PieChart(
                        modifier = Modifier
                            .size(200.dp)
                            .padding(bottom = 16.dp),
                        data = listOf(
                            Pie(
                                label = "Not learned",
                                data = studySetsNotLearn.toDouble(),
                                color = color.copy(alpha = 0.6f)
                            ),
                            Pie(
                                label = "Still learning",
                                data = studySetsStillLearn.toDouble(),
                                color = color.copy(alpha = 0.8f)
                            ),
                            Pie(
                                label = "Mastered",
                                data = studySetsMastered.toDouble(),
                                color = color
                            )
                        ),
                        onPieClick = {
                            val pieIndex = when (it.label) {
                                "Not learned" -> 0
                                "Still learning" -> 1
                                "Mastered" -> 2
                                else -> 0
                            }
                            println("Pie index: $pieIndex")
                        },
                        selectedScale = 1.2f,
                        scaleAnimEnterSpec = spring<Float>(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        ),
                        colorAnimEnterSpec = tween(300),
                        colorAnimExitSpec = tween(300),
                        scaleAnimExitSpec = tween(300),
                        spaceDegreeAnimExitSpec = tween(300),
                        style = Pie.Style.Fill
                    )
                    ProgressRow(
                        label = "Not learned",
                        percentage = studySetsNotLearn * 100 / totalStudySet,
                        color = color.copy(alpha = 0.6f)
                    )

                    ProgressRow(
                        label = "Still learning",
                        percentage = studySetsStillLearn * 100 / totalStudySet,
                        color = color.copy(alpha = 0.8f)
                    )

                    ProgressRow(
                        label = "Mastered",
                        percentage = studySetsMastered * 100 / totalStudySet,
                        color = color
                    )
                }
            }
        }
    }
}
