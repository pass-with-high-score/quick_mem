package com.pwhs.quickmem.presentation.app.study_set.study.flip_flashcard

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.msusman.compose.cardstack.CardStack
import com.msusman.compose.cardstack.Direction
import com.msusman.compose.cardstack.Duration
import com.msusman.compose.cardstack.SwipeDirection
import com.msusman.compose.cardstack.SwipeMethod
import com.msusman.compose.cardstack.rememberStackState
import com.pwhs.quickmem.domain.model.flashcard.FlashCardResponseModel
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
        onBackClicked = {
            resultNavigator.setResult(true)
            navigator.navigateUp()
        },
        currentCardIndex = uiState.currentCardIndex,
        countKnown = uiState.countKnown,
        countStillLearning = uiState.countStillLearning,
        onSwipe = { id ->
            viewModel.onEvent(FlipFlashCardUiAction.OnSwipe(id))
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
    onBackClicked: () -> Unit = { },
    onSwipe: (String) -> Unit = { }
) {
    Timber.d("FlipFlashCard: $flashCards")
    Timber.d("FlipFlashCard: $isLoading")
    var showSettingBottomSheet by remember {
        mutableStateOf(false)
    }
    val settingBottomSheetState = rememberModalBottomSheetState()
    val stillLearningColor = Color(0xffd05700)
    val knownColor = Color(0xff18ae79)
    val scope = rememberCoroutineScope()
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
                currentCardIndex = 0,
                totalCards = flashCards.size,
                onBackClicked = onBackClicked,
                onSettingsClicked = {
                    showSettingBottomSheet = true
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = modifier.padding(innerPadding)
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
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
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
                                text = countStillLearning.toString(),
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = stillLearningColor
                                ),
                                modifier = Modifier.padding(end = 25.dp)
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
                                text = countKnown.toString(),
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = knownColor
                                ),
                                modifier = Modifier.padding(start = 25.dp)
                            )
                        }
                    )
                }
                if (flashCards.isNotEmpty()) {
                    CardStack(
                        modifier = Modifier.padding(16.dp),
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
                        onSwiped = { index ->
                            Timber.d("Swiped: $index")
//                            onSwipe(flashCards[index].id)
                        }
                    ) {
                        StudyFlipFlashCard(
                            flashCard = it,
                            modifier = Modifier.fillMaxSize()
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
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
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