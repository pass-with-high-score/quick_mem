package com.pwhs.quickmem.presentation.app.study_set.study.flip_flashcard

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import com.pwhs.quickmem.presentation.app.study_set.study.component.StudyFlipBottomSheet
import com.pwhs.quickmem.presentation.app.study_set.study.component.StudyFlipFlashCard
import com.pwhs.quickmem.presentation.app.study_set.study.component.StudyTopAppBar
import com.pwhs.quickmem.presentation.component.LoadingOverlay
import com.pwhs.quickmem.util.toColor
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
        isEndOfList = uiState.isEndOfList,
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
        studySetColor = uiState.studySetColor.hexValue.toColor(),
        onSwipeRight = { isSwipingRight ->
            viewModel.onEvent(FlipFlashCardUiAction.OnSwipeRight(isSwipingRight))
        },
        onSwipeLeft = { isSwipingLeft ->
            viewModel.onEvent(FlipFlashCardUiAction.OnSwipeLeft(isSwipingLeft))
        },
        onUpdateCountKnown = { isIncrease, flashCardId ->
            viewModel.onEvent(FlipFlashCardUiAction.OnUpdateCountKnown(isIncrease, flashCardId))
        },
        onUpdateCountStillLearning = { isIncrease, flashCardId ->
            viewModel.onEvent(
                FlipFlashCardUiAction.OnUpdateCountStillLearning(
                    isIncrease,
                    flashCardId
                )
            )
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
    studySetColor: Color = MaterialTheme.colorScheme.primary,
    isLoading: Boolean = false,
    isSwipingLeft: Boolean = false,
    isSwipingRight: Boolean = false,
    isEndOfList: Boolean = false,
    onBackClicked: () -> Unit = { },
    onUpdatedCardIndex: (Int) -> Unit = { },
    onSwipeRight: (Boolean) -> Unit = { },
    onSwipeLeft: (Boolean) -> Unit = { },
    onUpdateCountKnown: (Boolean, String) -> Unit = { _, _ -> },
    onUpdateCountStillLearning: (Boolean, String) -> Unit = { _, _ -> }
) {
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
                val currentProgress by animateFloatAsState(
                    targetValue = if (flashCards.isNotEmpty()) currentCardIndex / flashCards.size.toFloat() else 0f
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
                    when (isEndOfList) {
                        false -> {
                            if (flashCards.isNotEmpty()) {
                                CardStack(
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .zIndex(2f),
                                    stackState = stackState,
                                    cardElevation = 10.dp,
                                    scaleRatio = 0.95f,
                                    rotationMaxDegree = 0,
                                    displacementThreshold = 120.dp,
                                    animationDuration = Duration.NORMAL,
                                    visibleCount = when (flashCards.size) {
                                        in 1..3 -> flashCards.size
                                        else -> 3
                                    },
                                    stackDirection = Direction.Bottom,
                                    swipeDirection = SwipeDirection.FREEDOM,
                                    swipeMethod = SwipeMethod.AUTOMATIC_AND_MANUAL,
                                    items = flashCards,
                                    onSwiped = { index, direction ->
                                        onUpdatedCardIndex(index)
                                        val flashCardId = flashCards[index].id
                                        Timber.d("Swiped index: $index, direction: $direction")
                                        when (direction) {
                                            Direction.Left, Direction.TopAndLeft, Direction.BottomAndLeft, Direction.Top -> {
                                                onUpdateCountStillLearning(true, flashCardId)
                                            }

                                            Direction.Right, Direction.TopAndRight, Direction.BottomAndRight, Direction.Bottom -> {
                                                onUpdateCountKnown(true, flashCardId)
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
                                        isShowingEffect = currentCardIndex == flashCards.indexOf(it),
                                        flashCardColor = studySetColor
                                    )
                                }
                            }
                        }

                        true -> {
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
                }
                AnimatedVisibility(
                    visible = !isEndOfList,
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(40.dp)
                    ) {
                        IconButton(
                            onClick = {
                                stackState.swipe(Direction.Left)
                            },
                            modifier = Modifier
                                .border(1.dp, Color.Gray, CircleShape)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
                                contentDescription = "Swipe Left",
                                tint = stillLearningColor,
                                modifier = Modifier
                                    .padding(10.dp)
                            )
                        }
                        IconButton(
                            onClick = {
                                stackState.swipe(Direction.Right)
                            },
                            modifier = Modifier
                                .border(1.dp, Color.Gray, CircleShape)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                                contentDescription = "Swipe Right",
                                tint = knownColor,
                                modifier = Modifier
                                    .padding(10.dp)
                            )
                        }
                    }
                }
            }
            LoadingOverlay(isLoading = isLoading)
        }
    }

    StudyFlipBottomSheet(
        bottomSheetState = settingBottomSheetState,
        showBottomSheet = showSettingBottomSheet,
        onDismiss = {
            showSettingBottomSheet = false
        }
    )
}

@Preview
@Composable
private fun FlipFlashCardScreenPreview() {
    MaterialTheme {
        FlipFlashCard()
    }
}