package com.pwhs.quickmem.presentation.app.study_set.studies.true_false

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.pwhs.quickmem.domain.model.flashcard.FlashCardResponseModel
import com.pwhs.quickmem.presentation.app.study_set.studies.true_false.component.TrueFalseButton
import com.pwhs.quickmem.presentation.app.study_set.studies.true_false.component.TrueFalseFlashcardFinish
import com.pwhs.quickmem.presentation.component.LoadingOverlay
import com.pwhs.quickmem.presentation.component.ViewImageDialog
import com.pwhs.quickmem.ui.theme.QuickMemTheme
import com.pwhs.quickmem.util.toColor
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<RootGraph>(
    navArgs = LearnByTrueFalseArgs::class
)
@Composable
fun LearnByTrueFalseScreen(
    modifier: Modifier = Modifier,
    viewModel: LearnByTrueFalseViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val uiState by viewModel.uiState.collectAsState()
    LearnByTrueFalse(
        modifier = modifier,
        onNavigateBack = {
            navigator.navigateUp()
        },
        isLoading = uiState.isLoading,
        isEndOfList = uiState.isEndOfList,
        currentCardIndex = uiState.currentCardIndex,
        flashCardList = uiState.flashCardList,
        studySetColor = uiState.studySetColor.hexValue.toColor(),
        randomQuestion = uiState.randomQuestion,
        wrongAnswerCount = uiState.wrongAnswerCount,
        learningTime = uiState.learningTime,
        listWrongAnswer = uiState.listWrongAnswer,
        onContinueLearningClicked = {
            viewModel.onEvent(LearnByTrueFalseUiAction.ContinueLearnWrongAnswer)
        },
        onRestart = {
            viewModel.onEvent(LearnByTrueFalseUiAction.RestartLearn)
        },
        onAnswer = { isTrue, flashcardId ->
            viewModel.onEvent(
                LearnByTrueFalseUiAction.OnAnswer(
                    flashcardId = flashcardId,
                    isCorrect = isTrue
                )
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LearnByTrueFalse(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    isEndOfList: Boolean = false,
    currentCardIndex: Int = 0,
    studySetColor: Color = MaterialTheme.colorScheme.primary,
    flashCardList: List<FlashCardResponseModel> = emptyList(),
    randomQuestion: TrueFalseQuestion? = null,
    onNavigateBack: () -> Unit = {},
    onRestart: () -> Unit = {},
    onAnswer: (Boolean, String) -> Unit = { _, _ -> },
    wrongAnswerCount: Int = 0,
    learningTime: Long = 0,
    listWrongAnswer: List<TrueFalseQuestion> = emptyList(),
    onContinueLearningClicked: () -> Unit = {},
) {
    var isImageViewerOpen by remember { mutableStateOf(false) }
    var definitionImageUri by remember { mutableStateOf("") }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    when (isLoading) {
                        true -> Text("Loading")
                        false -> when (isEndOfList) {
                            false -> Text("${currentCardIndex + 1}/${flashCardList.size}")
                            true -> Text("Finished")
                        }
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = onNavigateBack
                    ) {
                        Icon(
                            imageVector = Default.Clear,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    if (!isEndOfList) {
                        IconButton(
                            onClick = onRestart
                        ) {
                            Icon(
                                imageVector = Default.RestartAlt,
                                contentDescription = "Restart",
                                tint = studySetColor
                            )
                        }
                    }
                }
            )
        },
    ) { innerPadding ->
        Box {
            Column(
                modifier = modifier
                    .padding(innerPadding)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val currentProgress by animateFloatAsState(
                    targetValue = currentCardIndex.toFloat() / flashCardList.size.toFloat()
                )
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    progress = {
                        when (isEndOfList) {
                            false -> currentProgress
                            true -> 1f
                        }
                    },
                    color = when {
                        currentProgress < 0.2f -> studySetColor.copy(alpha = 0.2f)
                        currentProgress < 0.5f -> studySetColor.copy(alpha = 0.5f)
                        currentProgress < 0.8f -> studySetColor.copy(alpha = 0.8f)
                        else -> studySetColor
                    }
                )
                when (isEndOfList) {
                    false -> {
                        LazyColumn(
                            modifier = Modifier.padding(horizontal = 16.dp)
                        ) {
                            item {
                                Text(
                                    text = "Choose True or False",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        color = studySetColor
                                    ),
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                            item {
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(200.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.surface
                                    ),
                                    border = BorderStroke(
                                        width = 0.5.dp,
                                        color = studySetColor.copy(alpha = 0.5f)
                                    ),
                                    shape = MaterialTheme.shapes.small
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(8.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center

                                    ) {
                                        Text(
                                            text = randomQuestion?.term ?: "",
                                            style = MaterialTheme.typography.titleLarge.copy(
                                                fontSize = when (randomQuestion?.term?.length
                                                    ?: 0) {
                                                    in 0..10 -> 24.sp
                                                    in 11..20 -> 20.sp
                                                    else -> 16.sp
                                                }
                                            ),
                                            color = studySetColor,
                                            modifier = Modifier.padding(8.dp)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.padding(8.dp))
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(300.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.surface
                                    ),
                                    border = BorderStroke(
                                        width = 0.5.dp,
                                        color = studySetColor.copy(alpha = 0.5f)
                                    ),
                                    shape = MaterialTheme.shapes.small
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(8.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        if (randomQuestion?.definitionImageUrl?.isNotEmpty() == true) {
                                            AsyncImage(
                                                model = randomQuestion.definitionImageUrl,
                                                contentDescription = "Definition Image",
                                                contentScale = ContentScale.Crop,
                                                modifier = Modifier
                                                    .size(80.dp)
                                                    .clickable {
                                                        isImageViewerOpen = true
                                                        definitionImageUri =
                                                            randomQuestion.definitionImageUrl
                                                    }
                                            )
                                        }
                                        Text(
                                            text = randomQuestion?.definition ?: "",
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                fontSize = when (randomQuestion?.definition?.length
                                                    ?: 0) {
                                                    in 0..10 -> 16.sp
                                                    in 11..20 -> 14.sp
                                                    else -> 12.sp
                                                }
                                            ),
                                            modifier = Modifier.padding(8.dp)
                                        )
                                    }
                                }
                            }
                            item {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 16.dp),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    TrueFalseButton(
                                        title = "True",
                                        onClick = {
                                            if (randomQuestion?.isRandom == true) {
                                                onAnswer(false, randomQuestion.id)
                                            } else {
                                                onAnswer(true, randomQuestion?.id ?: "")
                                            }
                                        },
                                        modifier = Modifier.weight(1f),
                                        isTrue = true
                                    )
                                    Spacer(modifier = Modifier.padding(horizontal = 8.dp))
                                    TrueFalseButton(
                                        title = "False",
                                        onClick = {
                                            if (randomQuestion?.isRandom == true) {
                                                onAnswer(true, randomQuestion.id)
                                            } else {
                                                onAnswer(false, randomQuestion?.id ?: "")
                                            }
                                        },
                                        modifier = Modifier.weight(1f),
                                        isTrue = false
                                    )
                                }
                            }
                        }
                    }

                    true -> {
                        TrueFalseFlashcardFinish(
                            isEndOfList = true,
                            wrongAnswerCount = wrongAnswerCount,
                            correctAnswerCount = flashCardList.size - wrongAnswerCount,
                            studySetColor = studySetColor,
                            learningTime = learningTime,
                            onContinueLearningClicked = onContinueLearningClicked,
                            listWrongAnswer = listWrongAnswer,
                            flashCardSize = flashCardList.size,
                            onRestartClicked = onRestart
                        )
                    }
                }
            }
            LoadingOverlay(isLoading = isLoading)
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
}

@Preview(showSystemUi = true)
@Composable
private fun LearnByTrueFalseScreenPreview() {
    QuickMemTheme {
        LearnByTrueFalse()
    }
}