package com.pwhs.quickmem.presentation.app.study_set.study.flip_flashcard

import androidx.collection.floatSetOf
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.msusman.compose.cardstack.CardStack
import com.msusman.compose.cardstack.Direction
import com.msusman.compose.cardstack.Duration
import com.msusman.compose.cardstack.SwipeDirection
import com.msusman.compose.cardstack.SwipeMethod
import com.msusman.compose.cardstack.rememberStackState
import com.pwhs.quickmem.domain.model.flashcard.FlashCardResponseModel
import com.pwhs.quickmem.presentation.app.study_set.study.component.FlipFlashCardStatusRow
import com.pwhs.quickmem.presentation.app.study_set.study.component.StudyFlipFlashCard
import com.pwhs.quickmem.presentation.app.study_set.study.component.StudyTopAppBar
import com.pwhs.quickmem.presentation.component.LoadingOverlay
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultBackNavigator
import kotlinx.coroutines.delay
import timber.log.Timber

@Destination<RootGraph>(
    navArgs = FlipFlashCardArgs::class
)
@Composable
fun FlipFlashCardScreen(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator,
    resultNavigator: ResultBackNavigator<Boolean>,
    viewModel: FlipFlashCardViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                else -> {}
            }

        }
    }
    FlipFlashCard(
        modifier = modifier,
        flashCards = uiState.flashCardList,
        isLoading = uiState.isLoading,
        isSwipingLeft = uiState.isSwipingLeft,
        isSwipingRight = uiState.isSwipingRight,
        onBackClicked = {
            resultNavigator.setResult(true)
            navigator.navigateUp()
        },
        currentCardIndex = uiState.currentCardIndex,
        countKnown = uiState.countKnown,
        countStillLearning = uiState.countStillLearning,
        onUpdatedCardIndex = { index ->
            viewModel.onEvent(FlipFlashCardUiAction.OnUpdateCardIndex(index))
        },
        onSwipeRight = { isSwipingRight ->
            viewModel.onEvent(FlipFlashCardUiAction.OnSwipeRight(isSwipingRight))
        },
        onSwipeLeft = { isSwipingLeft ->
            viewModel.onEvent(FlipFlashCardUiAction.OnSwipeLeft(isSwipingLeft))
        },
        onUpdateCountKnown = { isIncrease ->
            viewModel.onEvent(FlipFlashCardUiAction.OnUpdateCountKnown(isIncrease))
        },
        onUpdateCountStillLearning = { isIncrease ->
            viewModel.onEvent(FlipFlashCardUiAction.OnUpdateCountStillLearning(isIncrease))
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlipFlashCard(
    modifier: Modifier = Modifier,
    currentCardIndex: Int = 0,
    countKnown: Int = 0,
    countStillLearning: Int = 0,
    flashCards: List<FlashCardResponseModel> = emptyList(),
    isLoading: Boolean = false,
    isSwipingLeft: Boolean = false,
    isSwipingRight: Boolean = false,
    onBackClicked: () -> Unit = { },
    onUpdatedCardIndex: (Int) -> Unit = { },
    onSwipeRight: (Boolean) -> Unit = { },
    onSwipeLeft: (Boolean) -> Unit = { },
    onUpdateCountKnown: (Boolean) -> Unit = { },
    onUpdateCountStillLearning: (Boolean) -> Unit = { }
) {
    Timber.d("FlipFlashCard: $flashCards")
    Timber.d("FlipFlashCard: $isLoading")
    var showSettingBottomSheet by remember {
        mutableStateOf(false)
    }
    val settingBottomSheetState = rememberModalBottomSheetState()
    val stillLearningColor = Color(0xffd05700)
    val knownColor = Color(0xff18ae79)
    val stackState = rememberStackState()
    val suggestedText = listOf(
        "Tap the card to flip",
        "Swipe left to mark as known",
        "Swipe right to mark as still learning"
    )

    var currentTextIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(5000)
            currentTextIndex = (currentTextIndex + 1) % suggestedText.size
        }
    }
    Scaffold(
        topBar = {
            StudyTopAppBar(
                currentCardIndex = currentCardIndex,
                totalCards = flashCards.size,
                onBackClicked = onBackClicked,
                onSettingsClicked = {
                    showSettingBottomSheet = true
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = modifier
                .padding(innerPadding)
                .zIndex(1f)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.TopCenter)
            ) {
                val currentProgress =
                    if (flashCards.isNotEmpty()) currentCardIndex / flashCards.size.toFloat() else 0f
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    progress = {
                        if (currentProgress.isNaN()) 0f else currentProgress
                    }
                )
                FlipFlashCardStatusRow(
                    modifier = Modifier.fillMaxWidth(),
                    countStillLearning = countStillLearning,
                    countKnown = countKnown,
                    stillLearningColor = stillLearningColor,
                    knownColor = knownColor,
                    suggestedText = suggestedText,
                    currentTextIndex = currentTextIndex,
                    isSwipingLeft = isSwipingLeft,
                    isSwipingRight = isSwipingRight
                )
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(10.dp)
                        .zIndex(1f)
                ) {
                    if (!isLoading && currentCardIndex < flashCards.size) {
                        CardStack(
                            modifier = Modifier
                                .padding(16.dp)
                                .zIndex(2f),
                            stackState = stackState,
                            cardElevation = 10.dp,
                            scaleRatio = 0.95f,
                            rotationMaxDegree = 20,
                            displacementThreshold = 120.dp,
                            animationDuration = Duration.NORMAL,
                            visibleCount = if (flashCards.size > 1) flashCards.size - 1 else 1,
                            stackDirection = Direction.Bottom,
                            swipeDirection = SwipeDirection.FREEDOM,
                            swipeMethod = SwipeMethod.AUTOMATIC_AND_MANUAL,
                            items = flashCards,
                            onSwiped = { index, direction ->
                                Timber.d("Direction: ${direction.name}")
                                onUpdatedCardIndex(index)
                                when (direction) {
                                    Direction.Left, Direction.TopAndLeft, Direction.BottomAndLeft, Direction.Top -> {
                                        Timber.d("Still learning")
                                        onUpdateCountStillLearning(true)
                                    }

                                    Direction.Right, Direction.TopAndRight, Direction.BottomAndRight, Direction.Bottom -> {
                                        onUpdateCountKnown(true)
                                        Timber.d("Known")
                                    }

                                    else -> {
                                    }
                                }
                            },
                            onChange = { direction ->
                                when (direction) {
                                    Direction.Left, Direction.TopAndLeft, Direction.BottomAndLeft, Direction.Top -> {
                                        onSwipeLeft(true)
                                        onSwipeRight(false)
                                    }

                                    Direction.Right, Direction.TopAndRight, Direction.BottomAndRight, Direction.Bottom -> {
                                        onSwipeRight(true)
                                        onSwipeLeft(false)
                                    }

                                    Direction.None -> {
                                        onSwipeLeft(false)
                                        onSwipeRight(false)
                                    }
                                }
                            }
                        ) {
                            StudyFlipFlashCard(
                                flashCard = it,
                                modifier = Modifier.fillMaxSize(),
                                isSwipingLeft = isSwipingLeft,
                                isSwipingRight = isSwipingRight,
                                stillLearningColor = stillLearningColor,
                                knownColor = knownColor,
                                isShowingEffect = currentCardIndex == flashCards.indexOf(it)
                            )
                        }

                    } else {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No flashcards available",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Back"
                        )
                    }
                }
            }
            LoadingOverlay(isLoading = isLoading)
        }
    }

    if (showSettingBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showSettingBottomSheet = false
            },
            sheetState = settingBottomSheetState
        ) {
            Column {
                Text(
                    text = "Setting",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Preview
@Composable
private fun FlipFlashCardScreenPreview() {
    MaterialTheme {
        FlipFlashCard()
    }
}