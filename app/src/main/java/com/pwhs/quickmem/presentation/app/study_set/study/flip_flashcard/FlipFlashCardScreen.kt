package com.pwhs.quickmem.presentation.app.study_set.study.flip_flashcard

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.msusman.compose.cardstack.CardStack
import com.msusman.compose.cardstack.Direction
import com.msusman.compose.cardstack.Duration
import com.msusman.compose.cardstack.SwipeDirection
import com.msusman.compose.cardstack.SwipeMethod
import com.msusman.compose.cardstack.rememberStackState
import com.pwhs.quickmem.R
import com.pwhs.quickmem.domain.model.flashcard.FlashCardResponseModel
import com.pwhs.quickmem.presentation.app.study_set.component.StudyCardBottomSheet
import com.pwhs.quickmem.presentation.app.study_set.detail.progress.StudySetDonutChart
import com.pwhs.quickmem.presentation.app.study_set.study.component.FlipFlashCardStatusRow
import com.pwhs.quickmem.presentation.app.study_set.study.component.StudyFlipFlashCard
import com.pwhs.quickmem.presentation.app.study_set.study.component.StudyTopAppBar
import com.pwhs.quickmem.presentation.component.LoadingOverlay
import com.pwhs.quickmem.ui.theme.QuickMemTheme
import com.pwhs.quickmem.util.toColor
import com.pwhs.quickmem.util.toStringTime
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
                FlipFlashCardUiEvent.Finished -> TODO()
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
        learningTime = uiState.learningTime,
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
        },
        onRestartClicked = {
            viewModel.onEvent(FlipFlashCardUiAction.OnRestartClicked)
        },
        onContinueLearningClicked = {
            viewModel.onEvent(FlipFlashCardUiAction.OnContinueLearningClicked)
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
    learningTime: Long = 0L,
    onBackClicked: () -> Unit = { },
    onUpdatedCardIndex: (Int) -> Unit = { },
    onSwipeRight: (Boolean) -> Unit = { },
    onSwipeLeft: (Boolean) -> Unit = { },
    onUpdateCountKnown: (Boolean, String) -> Unit = { _, _ -> },
    onUpdateCountStillLearning: (Boolean, String) -> Unit = { _, _ -> },
    onRestartClicked: () -> Unit = { },
    onContinueLearningClicked: () -> Unit = { }
) {
    var showHintBottomSheet by remember {
        mutableStateOf(false)
    }
    var showExplanationBottomSheet by remember {
        mutableStateOf(false)
    }
    val hintBottomSheetState = rememberModalBottomSheetState()
    val explanationBottomSheetState = rememberModalBottomSheetState()
    val stillLearningColor = Color(0xffd05700)
    val knownColor = Color(0xff18ae79)
    val stackState = rememberStackState()
    val suggestedText = listOf(
        "Tap the card to flip",
        "Swipe left to mark as known",
        "Swipe right to mark as still learning"
    )
    var currentTextIndex by remember { mutableIntStateOf(0) }
    var flipFlashCardSupport by remember {
        mutableStateOf(StudySetSupportEnum.NONE)
    }
    var isCardFlip by remember {
        mutableStateOf(true)
    }
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.confetti))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        isPlaying = isEndOfList,
    )
    LaunchedEffect(currentCardIndex, isCardFlip) {
        val currentFlashCard = flashCards.getOrNull(currentCardIndex)
        flipFlashCardSupport = when {
            currentFlashCard?.hint != null && !isCardFlip -> StudySetSupportEnum.SHOW_HINT
            currentFlashCard?.explanation != null && isCardFlip -> StudySetSupportEnum.SHOW_EXPLANATION
            else -> StudySetSupportEnum.NONE
        }
    }
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
                isEnOfSet = isEndOfList,
                onRestartClicked = {
                    onRestartClicked()
                    stackState.reset()
                },
                shouldShowRestart = !isEndOfList
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
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .zIndex(1f)
                ) {
                    when (isEndOfList) {
                        false -> {
                            Column(
                                modifier = Modifier.fillMaxSize()
                            ) {
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
                                        visibleCount = minOf(3, flashCards.size),
                                        stackDirection = Direction.Bottom,
                                        swipeDirection = SwipeDirection.FREEDOM,
                                        swipeMethod = SwipeMethod.AUTOMATIC_AND_MANUAL,
                                        items = flashCards,
                                        onSwiped = { index, direction ->
                                            Timber.d("Index of flashcard: $index")
                                            if (flipFlashCardSupport == StudySetSupportEnum.SHOW_HINT) {
                                                isCardFlip = true
                                            } else if (flipFlashCardSupport == StudySetSupportEnum.SHOW_EXPLANATION) {
                                                isCardFlip = false
                                            }
                                            if (index in flashCards.indices) {
                                                onUpdatedCardIndex(index)
                                                val flashCardId = flashCards[index].id
                                                Timber.d("Swiped index: $index, direction: $direction")
                                                when (direction) {
                                                    Direction.Left, Direction.TopAndLeft, Direction.BottomAndLeft, Direction.Top -> {
                                                        onUpdateCountStillLearning(
                                                            true,
                                                            flashCardId
                                                        )
                                                    }

                                                    Direction.Right, Direction.TopAndRight, Direction.BottomAndRight, Direction.Bottom -> {
                                                        onUpdateCountKnown(true, flashCardId)
                                                    }

                                                    else -> {
                                                    }
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
                                    ) { flashcard ->
                                        StudyFlipFlashCard(
                                            flashCard = flashcard,
                                            modifier = Modifier.fillMaxSize(),
                                            isSwipingLeft = isSwipingLeft,
                                            isSwipingRight = isSwipingRight,
                                            stillLearningColor = stillLearningColor,
                                            knownColor = knownColor,
                                            isShowingEffect = currentCardIndex == flashCards.indexOf(
                                                flashcard
                                            ),
                                            flashCardColor = studySetColor,
                                            onFlip = {
                                                isCardFlip = !isCardFlip
                                            }
                                        )
                                    }
                                }
                            }
                        }

                        true -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
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
                                                    append("/${flashCards.size}")
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
                                                stackState.reset()
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
                                            stackState.reset()
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
                        when (flipFlashCardSupport) {
                            StudySetSupportEnum.SHOW_HINT -> {
                                ElevatedButton(
                                    onClick = {
                                        showHintBottomSheet = true
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = studySetColor.copy(alpha = 0.6f)
                                    ),
                                    elevation = ButtonDefaults.elevatedButtonElevation(
                                        defaultElevation = 3.dp
                                    ),
                                    border = BorderStroke(1.dp, Color.White)
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Outlined.Lightbulb,
                                            contentDescription = "Hint",
                                        )
                                        Text(
                                            "Show Hint",
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                    }
                                }
                            }

                            StudySetSupportEnum.SHOW_EXPLANATION -> {
                                ElevatedButton(
                                    onClick = {
                                        showExplanationBottomSheet = true
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = studySetColor.copy(alpha = 0.6f)
                                    ),
                                    elevation = ButtonDefaults.elevatedButtonElevation(
                                        defaultElevation = 3.dp
                                    ),
                                    border = BorderStroke(1.dp, Color.White)
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Outlined.Lightbulb,
                                            contentDescription = "Explanation",
                                        )
                                        Text(
                                            "Show Explanation",
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                    }
                                }
                            }

                            StudySetSupportEnum.NONE -> {
                                // Do nothing
                            }
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

    StudyCardBottomSheet(
        modifier = Modifier,
        title = "Hint",
        contentText = flashCards.getOrNull(currentCardIndex)?.hint ?: "",
        onDismiss = {
            showHintBottomSheet = false
        },
        isShowBottomSheet = showHintBottomSheet,
        sheetState = hintBottomSheetState
    )

    StudyCardBottomSheet(
        modifier = Modifier,
        title = "Explanation",
        contentText = flashCards.getOrNull(currentCardIndex)?.explanation ?: "",
        onDismiss = {
            showExplanationBottomSheet = false
        },
        isShowBottomSheet = showExplanationBottomSheet,
        sheetState = explanationBottomSheetState
    )
}

@Preview(showBackground = true, device = Devices.PIXEL_7_PRO)
@Composable
private fun FlipFlashCardScreenPreview() {
    QuickMemTheme {
        FlipFlashCard(
            isEndOfList = true,
        )
    }
}