package com.pwhs.quickmem.presentation.app.study_set.studies.quiz.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pwhs.quickmem.core.data.QuizStatus
import com.pwhs.quickmem.domain.model.flashcard.FlashCardResponseModel
import com.pwhs.quickmem.presentation.app.study_set.studies.quiz.RandomAnswer

@Composable
fun QuizView(
    modifier: Modifier = Modifier,
    flashCard: FlashCardResponseModel,
    randomAnswer: List<RandomAnswer> = emptyList(),
    correctColor: Color = MaterialTheme.colorScheme.primary,
    incorrectColor: Color = MaterialTheme.colorScheme.error,
    onCorrectAnswer: (QuizStatus, String) -> Unit,
    canResetState: Boolean = false
) {
    var isSelectCorrectAnswer by remember { mutableStateOf(false) }
    var selectedAnswer by remember { mutableStateOf("") }
    var feedbackMessage by remember { mutableStateOf("Select an answer") }
    val correctMessages = listOf("Great job!", "Well done!", "Correct!", "Nice work!")
    val incorrectMessages = listOf("Try again!", "Incorrect!", "Not quite!", "Keep trying!")

    LaunchedEffect(key1 = canResetState) {
        selectedAnswer = ""
        feedbackMessage = "Select an answer"
        isSelectCorrectAnswer = false
    }

    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(vertical = 16.dp)
                ) {
                    Text(
                        text = flashCard.term,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = when {
                                flashCard.term.length <= 10 -> 25.sp
                                flashCard.term.length <= 20 -> 20.sp
                                flashCard.term.length <= 30 -> 18.sp
                                else -> 16.sp
                            }
                        )
                    )
                }

                Text(
                    text = feedbackMessage,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = when {
                            correctMessages.contains(feedbackMessage) -> correctColor
                            incorrectMessages.contains(feedbackMessage) -> incorrectColor
                            else -> MaterialTheme.colorScheme.onSurface
                        }
                    ),
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                if (selectedAnswer.isNotEmpty() && flashCard.explanation?.isNotEmpty() == true) {
                    Text(
                        text = "Explanation: ${flashCard.explanation}",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurface
                        ),
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                }
            }
            items(randomAnswer) { randomAnswer ->
                LearnQuizCardAnswer(
                    correctColor = correctColor,
                    incorrectColor = incorrectColor,
                    randomAnswer = randomAnswer,
                    selectedAnswer = selectedAnswer,
                    onAnswerSelected = {
                        selectedAnswer = it
                        if (selectedAnswer == randomAnswer.answer && randomAnswer.isCorrect) {
                            feedbackMessage = correctMessages.random()
                            onCorrectAnswer(QuizStatus.CORRECT, selectedAnswer)
                            isSelectCorrectAnswer = true
                        } else {
                            feedbackMessage = incorrectMessages.random()
                            onCorrectAnswer(QuizStatus.WRONG, selectedAnswer)
                            isSelectCorrectAnswer = false
                        }
                    }
                )
            }
        }
    }
}
