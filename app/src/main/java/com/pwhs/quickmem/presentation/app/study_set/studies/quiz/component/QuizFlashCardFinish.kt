package com.pwhs.quickmem.presentation.app.study_set.studies.quiz.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.pwhs.quickmem.R
import com.pwhs.quickmem.core.data.states.WrongAnswer
import com.pwhs.quickmem.presentation.app.study_set.detail.progress.StudySetDonutChart
import com.pwhs.quickmem.presentation.component.ViewImageDialog
import com.pwhs.quickmem.ui.theme.correctColor
import com.pwhs.quickmem.ui.theme.incorrectColor
import com.pwhs.quickmem.util.toStringTime

@Composable
fun QuizFlashCardFinish(
    modifier: Modifier = Modifier,
    isEndOfList: Boolean,
    wrongAnswerCount: Int,
    correctAnswerCount: Int,
    studySetColor: Color,
    flashCardSize: Int,
    learningTime: Long,
    onContinueLearningClicked: () -> Unit,
    listWrongAnswer: List<WrongAnswer>,
    onRestartClicked: () -> Unit,
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.confetti))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        isPlaying = isEndOfList,
    )
    val encouragementMessage = when {
        correctAnswerCount == 0 -> "Don't give up! Keep trying!"
        correctAnswerCount <= flashCardSize * 0.3 -> "Good start! Keep going!"
        correctAnswerCount <= flashCardSize * 0.6 -> "You're doing great! Keep it up!"
        correctAnswerCount < flashCardSize -> "Almost there! Keep pushing!"
        else -> "Excellent! You've mastered it!"
    }
    var isImageViewerOpen by remember { mutableStateOf(false) }
    var definitionImageUri by remember { mutableStateOf("") }
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier
                .fillMaxSize()
                .zIndex(2f)
        )
        LazyColumn {
            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 20.dp)
                        .padding(16.dp)
                ) {
                    Text(
                        text = encouragementMessage,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    StudySetDonutChart(
                        modifier = Modifier
                            .size(200.dp)
                            .padding(16.dp),
                        studySetsStillLearn = wrongAnswerCount.coerceAtLeast(0),
                        studySetsMastered = correctAnswerCount.coerceAtLeast(0),
                        color = studySetColor
                    )

                    Text(
                        text = "Keep focusing on your study set to master it!",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(16.dp)
                    )

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        border = BorderStroke(1.dp, studySetColor),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 3.dp,
                        )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_card),
                                contentDescription = "Card",
                            )
                            Text(
                                text = "Flashcards learned",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontSize = 18.sp
                                ),
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(
                                        start = 8.dp,
                                        end = 16.dp
                                    ),
                                textAlign = TextAlign.Start,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(
                                        style = SpanStyle(
                                            fontWeight = FontWeight.Bold
                                        )
                                    ) {
                                        append(correctAnswerCount.coerceAtLeast(0).toString())
                                    }
                                    append("/${flashCardSize}")
                                },
                                fontSize = 18.sp,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        border = BorderStroke(1.dp, studySetColor),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 3.dp,
                        )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.AccessTime,
                                contentDescription = "Time",
                            )
                            Text(
                                text = "Learning Time",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontSize = 18.sp
                                ),
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(
                                        start = 8.dp,
                                        end = 16.dp
                                    ),
                                textAlign = TextAlign.Start,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                            Text(
                                text = learningTime.toStringTime(),
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                    if (wrongAnswerCount > 0) {
                        Button(
                            onClick = {
                                onContinueLearningClicked()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            shape = MaterialTheme.shapes.small,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = studySetColor,
                                contentColor = Color.White
                            ),
                            border = BorderStroke(1.dp, studySetColor)
                        ) {
                            Text(
                                text = "Finish $wrongAnswerCount answers now",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                    Button(
                        onClick = {
                            onRestartClicked()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        shape = MaterialTheme.shapes.small,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = studySetColor
                        ),
                        border = BorderStroke(1.dp, studySetColor)
                    ) {
                        Text(
                            text = "Learn from the beginning",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }
            if (wrongAnswerCount < 0) {
                item {
                    Text(
                        text = "Review your wrong answers",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                    )
                }
            }
            items(listWrongAnswer) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(bottom = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            Text(
                                text = "Term: ${it.flashCard.term}",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier.weight(1f)
                            )
                            if (!it.flashCard.definitionImageURL.isNullOrEmpty()) {
                                AsyncImage(
                                    model = it.flashCard.definitionImageURL,
                                    contentDescription = "Definition Image",
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clickable {
                                            isImageViewerOpen = true
                                            definitionImageUri = it.flashCard.definitionImageURL
                                        },
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                        Text(
                            text = "Correct Answer: ${it.flashCard.definition}",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = correctColor
                            )
                        )
                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                        Text(
                            text = "Your Answer: ${it.userAnswer}",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = incorrectColor
                            )
                        )
                    }
                }
            }
        }

        // Image Viewer Dialog
        if (isImageViewerOpen) {
            ViewImageDialog(
                definitionImageUri = definitionImageUri,
                onDismissRequest = {
                    isImageViewerOpen = false
                    definitionImageUri = ""
                }
            )
        }
    }
}