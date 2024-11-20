package com.pwhs.quickmem.presentation.app.study_set.studies.quiz

import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.automirrored.filled.NavigateNext
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pwhs.quickmem.core.data.QuizStatus
import com.pwhs.quickmem.domain.model.flashcard.FlashCardResponseModel
import com.pwhs.quickmem.presentation.app.study_set.studies.quiz.component.QuizFlashCardFinish
import com.pwhs.quickmem.presentation.app.study_set.studies.quiz.component.QuizView
import com.pwhs.quickmem.presentation.component.LoadingOverlay
import com.pwhs.quickmem.ui.theme.QuickMemTheme
import com.pwhs.quickmem.util.toColor
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.result.ResultBackNavigator

@Destination<RootGraph>(
    navArgs = LearnByQuizArgs::class
)
@Composable
fun LearnByQuizScreen(
    modifier: Modifier = Modifier,
    resultNavigator: ResultBackNavigator<Boolean>,
    viewModel: LearnByQuizViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                LearnByQuizUiEvent.Finished -> {
                    Toast.makeText(context, "Finished", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }
    LearnByQuiz(
        modifier = modifier,
        isLoading = uiState.isLoading,
        onNavigateBack = {
            resultNavigator.navigateBack(true)
        },
        wrongAnswerCount = uiState.wrongAnswerCount,
        listWrongAnswer = uiState.listWrongAnswer,
        learningTime = uiState.learningTime,
        currentCardIndex = uiState.currentCardIndex,
        flashCardList = uiState.flashCardList,
        randomAnswers = uiState.randomAnswers,
        flashCard = uiState.currentFlashCard,
        studySetColor = uiState.studySetColor.hexValue.toColor(),
        onCorrectAnswer = { flashCardId, quizStatus, userAnswer ->
            viewModel.onEvent(
                LearnByQuizUiAction.SubmitCorrectAnswer(
                    flashCardId,
                    quizStatus,
                    userAnswer
                )
            )
        },
        onLoadNextFlashCard = {
            viewModel.onEvent(LearnByQuizUiAction.LoadNextFlashCard)
        },
        isEndOfList = uiState.isEndOfList,
        onContinueLearningClicked = {
            viewModel.onEvent(LearnByQuizUiAction.ContinueLearnWrongAnswer)
        },
        onRestartClicked = {
            viewModel.onEvent(LearnByQuizUiAction.RestartLearn)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LearnByQuiz(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit = {},
    isLoading: Boolean = false,
    wrongAnswerCount: Int = 0,
    listWrongAnswer: List<WrongAnswer> = emptyList(),
    learningTime: Long = 0,
    flashCardList: List<FlashCardResponseModel> = emptyList(),
    randomAnswers: List<RandomAnswer> = emptyList(),
    currentCardIndex: Int = 0,
    studySetColor: Color = MaterialTheme.colorScheme.primary,
    onCorrectAnswer: (String, QuizStatus, String) -> Unit = { _, _, _ -> },
    onLoadNextFlashCard: () -> Unit = {},
    flashCard: FlashCardResponseModel? = null,
    isEndOfList: Boolean = false,
    onContinueLearningClicked: () -> Unit = {},
    onRestartClicked: () -> Unit = {}
) {
    val incorrectColor = Color(0xFF860010)
    val correctColor = Color(0xFF6c9184)
    var canResetState by remember { mutableStateOf(false) }
    val showHintBottomSheet = remember { mutableStateOf(false) }
    val hintBottomSheetState = rememberModalBottomSheetState()
    var selectedAnswer by remember { mutableStateOf<String?>(null) }
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
                    if (!flashCard?.hint.isNullOrEmpty()) {
                        IconButton(
                            onClick = {
                                showHintBottomSheet.value = true
                            }
                        ) {
                            Icon(
                                imageVector = Default.LightMode,
                                contentDescription = "Hint",
                                tint = studySetColor
                            )
                        }
                    }
                    if (!isEndOfList) {
                        IconButton(
                            onClick = onRestartClicked
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
        floatingActionButton = {
            if (!isEndOfList) {
                FloatingActionButton(
                    onClick = {
                        canResetState = !canResetState
                        if (selectedAnswer == null) {
                            onCorrectAnswer(flashCard?.id ?: "", QuizStatus.SKIPPED, "")
                        }
                        onLoadNextFlashCard()
                        selectedAnswer = null
                    },
                    shape = MaterialTheme.shapes.extraLarge,
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.NavigateNext,
                        contentDescription = "Next Flash Card"
                    )
                }
            }
        }
    ) { innerPadding ->
        Box {
            Column(
                modifier = modifier
                    .padding(innerPadding)
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
                        if (flashCard != null) {
                            QuizView(
                                modifier = Modifier
                                    .fillMaxSize(),
                                flashCard = flashCard,
                                randomAnswer = randomAnswers,
                                correctColor = correctColor,
                                incorrectColor = incorrectColor,
                                onCorrectAnswer = { status, userAnswer ->
                                    selectedAnswer = userAnswer
                                    onCorrectAnswer(flashCard.id, status, userAnswer)
                                },
                                canResetState = canResetState
                            )
                        }
                    }

                    true -> {
                        QuizFlashCardFinish(
                            modifier = Modifier.fillMaxSize(),
                            isEndOfList = isEndOfList,
                            wrongAnswerCount = wrongAnswerCount,
                            correctAnswerCount = flashCardList.size - wrongAnswerCount,
                            studySetColor = studySetColor,
                            flashCardSize = flashCardList.size,
                            learningTime = learningTime,
                            onContinueLearningClicked = {
                                canResetState = !canResetState
                                onContinueLearningClicked()
                            },
                            onRestartClicked = {
                                canResetState = !canResetState
                                onRestartClicked()
                            },
                            listWrongAnswer = listWrongAnswer,
                            wrongColor = incorrectColor,
                            correctColor = correctColor
                        )
                    }
                }
            }
            LoadingOverlay(
                isLoading = isLoading
            )
            if (showHintBottomSheet.value) {
                ModalBottomSheet(
                    sheetState = hintBottomSheetState,
                    onDismissRequest = {
                        showHintBottomSheet.value = false
                    }
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Hint",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            text = flashCard?.hint ?: "",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }

    }
}

@Preview
@Composable
private fun LearnByQuizScreenPreview() {
    QuickMemTheme {
        LearnByQuiz()
    }
}