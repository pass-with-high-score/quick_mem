package com.pwhs.quickmem.presentation.app.study_set.study.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun FlipFlashCardStatusRow(
    modifier: Modifier = Modifier,
    countStillLearning: Int,
    countKnown: Int,
    stillLearningColor: Color,
    knownColor: Color,
    suggestedText: List<String>,
    currentTextIndex: Int,
    isSwipingLeft: Boolean,
    isSwipingRight: Boolean
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
    ) {
        Box(
            modifier = Modifier
                .height(45.dp)
                .width(65.dp)
                .border(
                    width = 2.dp,
                    color = stillLearningColor.copy(alpha = 0.4f),
                    shape = RoundedCornerShape(
                        topEnd = 50.dp,
                        bottomEnd = 50.dp
                    )
                )
                .background(
                    color = stillLearningColor.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(
                        topEnd = 50.dp,
                        bottomEnd = 50.dp
                    )
                ),
            contentAlignment = Alignment.CenterEnd,
            content = {
                Text(
                    text = if (isSwipingLeft) {
                        "+1"
                    } else {
                        countStillLearning.toString()
                    },
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = stillLearningColor
                    ),
                    modifier = Modifier.padding(end = 25.dp)
                        .animateContentSize()
                )
            }
        )

        AnimatedContent(
            targetState = currentTextIndex,
            content = { index ->
                Text(
                    text = suggestedText[index],
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
            },
            modifier = Modifier.weight(1f)
        )

        Box(
            modifier = Modifier
                .height(45.dp)
                .width(65.dp)
                .border(
                    width = 2.dp,
                    color = knownColor.copy(alpha = 0.4f),
                    shape = RoundedCornerShape(
                        topStart = 50.dp,
                        bottomStart = 50.dp
                    )
                )
                .background(
                    color = knownColor.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(
                        topStart = 50.dp,
                        bottomStart = 50.dp
                    )
                ),
            contentAlignment = Alignment.CenterStart,
            content = {
                Text(
                    text = if (isSwipingRight) {
                        "+1"
                    } else {
                        countKnown.toString()
                    },
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = knownColor
                    ),
                    modifier = Modifier.padding(start = 25.dp)
                        .animateContentSize()
                )
            }
        )
    }
}