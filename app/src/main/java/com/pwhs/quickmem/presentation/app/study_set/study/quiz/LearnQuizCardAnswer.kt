package com.pwhs.quickmem.presentation.app.study_set.study.quiz

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun LearnQuizCardAnswer(
    modifier: Modifier = Modifier,
    correctColor: Color = MaterialTheme.colorScheme.primary,
    incorrectColor: Color = MaterialTheme.colorScheme.error,
    randomAnswer: RandomAnswer,
    selectedAnswer: String,
    onAnswerSelected: (String) -> Unit = {},
) {
    val isSelected = selectedAnswer.isNotEmpty()
    val isCorrect = isSelected && randomAnswer.isCorrect
    val isIncorrect = isSelected && selectedAnswer == randomAnswer.answer

    Card(
        modifier = modifier.fillMaxWidth(),
        enabled = !isSelected,
        border = BorderStroke(
            width = if (isCorrect || isIncorrect) 2.dp else 0.dp,
            color = when {
                isCorrect -> correctColor
                isIncorrect -> incorrectColor
                else -> MaterialTheme.colorScheme.onSurface
            }
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            disabledContainerColor = MaterialTheme.colorScheme.surface
        ),
        onClick = {
            onAnswerSelected(randomAnswer.answer)
        }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Icon(
                imageVector = when {
                    isCorrect -> Icons.Default.Done
                    isIncorrect -> Icons.Default.Clear
                    else -> Icons.Default.RadioButtonUnchecked
                },
                contentDescription = null,
                tint = when {
                    isCorrect -> correctColor
                    isIncorrect -> incorrectColor
                    else -> MaterialTheme.colorScheme.onSurface
                }
            )
            Text(
                text = randomAnswer.answer,
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp),
                style = MaterialTheme.typography.bodyMedium.copy(),
                color = when {
                    isCorrect -> correctColor
                    isIncorrect -> incorrectColor
                    else -> MaterialTheme.colorScheme.onSurface
                }
            )
            if (randomAnswer.imageURL.isNotEmpty()) {
                AsyncImage(
                    model = randomAnswer.imageURL,
                    contentDescription = null,
                )
            }
        }
    }
}
