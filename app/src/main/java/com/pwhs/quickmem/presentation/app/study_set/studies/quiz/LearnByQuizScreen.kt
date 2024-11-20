package com.pwhs.quickmem.presentation.app.study_set.studies.quiz

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.pwhs.quickmem.domain.model.flashcard.FlashCardResponseModel
import com.pwhs.quickmem.presentation.app.study_set.studies.quiz.component.QuizView
import com.pwhs.quickmem.ui.theme.QuickMemTheme
import com.pwhs.quickmem.util.toColor
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultBackNavigator
import timber.log.Timber

@Destination<RootGraph>(
    navArgs = LearnByQuizArgs::class
)
@Composable
fun LearnByQuizScreen(
    modifier: Modifier = Modifier,
    resultNavigator: ResultBackNavigator<Boolean>,
    navigator: DestinationsNavigator,
    viewModel: LearnFlashCardViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                else -> {}
            }

        }
    }
    LearnByQuiz(
        modifier = modifier,
        onNavigateBack = {
            resultNavigator.navigateBack(true)
        },
        flashCardList = uiState.flashCardList,
        flashCardLearnRound = uiState.flashCardLearnRound,
        flashCardLearnRoundIndex = uiState.flashCardLearnRoundIndex,
        randomAnswers = uiState.randomAnswers,
        studySetColor = uiState.studySetColor.hexValue.toColor(),
        onCorrectAnswer = { flashCardId, isCorrect ->
            viewModel.onEvent(LearnByQuizUiAction.SubmitCorrectAnswer(flashCardId, isCorrect))
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LearnByQuiz(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit = {},
    flashCardList: List<FlashCardResponseModel> = emptyList(),
    flashCardLearnRound: List<FlashCardResponseModel> = emptyList(),
    flashCardLearnRoundIndex: Int = 0,
    randomAnswers: List<RandomAnswer> = emptyList(),
    studySetColor: Color = MaterialTheme.colorScheme.primary,
    onCorrectAnswer: (String, Boolean) -> Unit = { _, _ -> }
) {
    val incorrectColor = Color(0xFF860010)
    val correctColor = Color(0xFF6c9184)
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Round 1")
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
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
        ) {
            val currentProgress by animateFloatAsState(
                targetValue = flashCardLearnRoundIndex.toFloat() / flashCardLearnRound.size.toFloat()
            )
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth(),
                progress = {
                    if (currentProgress.isNaN()) 0f else currentProgress
                },
                color = when {
                    currentProgress < 0.2f -> studySetColor.copy(alpha = 0.2f)
                    currentProgress < 0.5f -> studySetColor.copy(alpha = 0.5f)
                    currentProgress < 0.8f -> studySetColor.copy(alpha = 0.8f)
                    else -> studySetColor
                }
            )
            val flashCard = flashCardLearnRound.getOrNull(flashCardLearnRoundIndex) ?: return@Column
            QuizView(
                modifier = Modifier
                    .fillMaxSize(),
                flashCard = flashCard,
                randomAnswer = randomAnswers,
                correctColor = correctColor,
                incorrectColor = incorrectColor
            ) {
                Timber.d("Correct Answer")
                onCorrectAnswer(flashCard.id, it)
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