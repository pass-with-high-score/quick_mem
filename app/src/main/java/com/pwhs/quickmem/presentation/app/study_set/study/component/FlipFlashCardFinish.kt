package com.pwhs.quickmem.presentation.app.study_set.study.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.pwhs.quickmem.R
import com.pwhs.quickmem.presentation.app.study_set.detail.progress.StudySetDonutChart
import com.pwhs.quickmem.util.toStringTime
import timber.log.Timber

@Composable
fun FlipFlashCardFinish(
    modifier: Modifier = Modifier,
    isEndOfList: Boolean,
    countStillLearning: Int,
    countKnown: Int,
    studySetColor: Color,
    flashCardSize: Int,
    learningTime: Long,
    onContinueLearningClicked: () -> Unit,
    onRestartClicked: () -> Unit,
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.confetti))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        isPlaying = isEndOfList,
    )
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
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp)
                .padding(16.dp)
        ) {
            Text(
                text = "You're doing great!",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            StudySetDonutChart(
                modifier = Modifier
                    .size(200.dp)
                    .padding(16.dp),
                studySetsStillLearn = countStillLearning,
                studySetsMastered = countKnown,
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
                onClick = {},
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
                    Image(
                        painter = painterResource(id = R.drawable.ic_card),
                        contentDescription = "Card",
                        contentScale = ContentScale.Crop
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
                                append(countKnown.toString())
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
                onClick = {},
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
                    Image(
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
                    Timber.d("toStringTime learningTime: ${learningTime.toStringTime()}")
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
            if (countStillLearning > 0) {
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
                        text = "Keep reviewing $countStillLearning terms",
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
                    text = "Restart Flashcards",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}