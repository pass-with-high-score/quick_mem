package com.pwhs.quickmem.presentation.app.study_set.studies.write

import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.filled.ArrowCircleUp
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.pwhs.quickmem.R
import com.pwhs.quickmem.core.data.enums.WriteStatus
import com.pwhs.quickmem.domain.model.flashcard.FlashCardResponseModel
import com.pwhs.quickmem.presentation.app.study_set.studies.component.UnfinishedLearningBottomSheet
import com.pwhs.quickmem.presentation.app.study_set.studies.write.component.WriteFlashcardFinish
import com.pwhs.quickmem.presentation.component.LoadingOverlay
import com.pwhs.quickmem.presentation.component.ViewImageDialog
import com.pwhs.quickmem.ui.theme.QuickMemTheme
import com.pwhs.quickmem.util.rememberImeState
import com.pwhs.quickmem.util.toColor
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.result.ResultBackNavigator
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Destination<RootGraph>(
    navArgs = LearnByWriteArgs::class
)
@Composable
fun LearnByWriteScreen(
    modifier: Modifier = Modifier,
    viewModel: LearnByWriteViewModel = hiltViewModel(),
    resultBackNavigator: ResultBackNavigator<Boolean>
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                LearnByWriteUiEvent.Back -> {
                    resultBackNavigator.navigateBack(true)
                }

                LearnByWriteUiEvent.Finished -> {
                    Toast.makeText(context, "Finished", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    LearnByWrite(
        modifier = modifier,
        onEndSessionClick = {
            viewModel.onEvent(LearnByWriteUiAction.OnBackClicked)
        },
        isLoading = uiState.isLoading,
        isEndOfList = uiState.isEndOfList,
        currentCardIndex = uiState.currentCardIndex,
        flashCardList = uiState.flashCardList,
        studySetColor = uiState.studySetColor.hexValue.toColor(),
        writeQuestion = uiState.writeQuestion,
        wrongAnswerCount = uiState.wrongAnswerCount,
        learningTime = uiState.learningTime,
        listWrongAnswer = uiState.listWrongAnswer,
        onContinueLearningClicked = {
            viewModel.onEvent(LearnByWriteUiAction.ContinueLearnWrongAnswer)
        },
        onRestart = {
            viewModel.onEvent(LearnByWriteUiAction.RestartLearn)
        },
        onSubmitAnswer = { id, status, answer ->
            viewModel.onEvent(LearnByWriteUiAction.OnAnswer(id, status, answer))
        },
        isGetAll = uiState.isGetAll
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LearnByWrite(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    isEndOfList: Boolean = false,
    currentCardIndex: Int = 0,
    studySetColor: Color = MaterialTheme.colorScheme.primary,
    flashCardList: List<FlashCardResponseModel> = emptyList(),
    writeQuestion: WriteQuestion? = null,
    onEndSessionClick: () -> Unit = {},
    onRestart: () -> Unit = {},
    wrongAnswerCount: Int = 0,
    learningTime: Long = 0,
    listWrongAnswer: List<WriteQuestion> = emptyList(),
    onContinueLearningClicked: () -> Unit = {},
    onSubmitAnswer: (String, WriteStatus, String) -> Unit = { _, _, _ -> },
    isGetAll: Boolean = false
) {
    var isImageViewerOpen by remember { mutableStateOf(false) }
    var definitionImageUri by remember { mutableStateOf("") }
    var userAnswer by rememberSaveable { mutableStateOf("") }
    val showHintBottomSheet = remember { mutableStateOf(false) }
    val hintBottomSheetState = rememberModalBottomSheetState()
    var showUnfinishedLearningBottomSheet by remember { mutableStateOf(false) }
    val unFinishedLearningBottomSheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var debounceJob: Job? = null
    val imeState = rememberImeState()
    val scrollState = rememberScrollState()
    LaunchedEffect(key1 = imeState.value) {
        if (imeState.value) {
            scrollState.animateScrollTo(scrollState.viewportSize, tween(300))
        }
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    when (isLoading) {
                        true -> Text(stringResource(R.string.txt_loading))
                        false -> when (isEndOfList) {
                            false -> Text("${currentCardIndex + 1}/${flashCardList.size}")
                            true -> Text(stringResource(R.string.txt_finished))
                        }
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            if (isEndOfList) {
                                onEndSessionClick()
                            } else {
                                showUnfinishedLearningBottomSheet = true
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Default.Clear,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    if (!isEndOfList && isGetAll) {
                        IconButton(
                            onClick = {
                                userAnswer = ""
                                onRestart()
                            }
                        ) {
                            Icon(
                                imageVector = Default.RestartAlt,
                                contentDescription = stringResource(R.string.txt_restart),
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
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .verticalScroll(scrollState)
                                .imePadding()
                        ) {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .requiredHeight(300.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surface
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
                                        text = writeQuestion?.definition ?: "",
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontSize = when (writeQuestion?.definition?.length
                                                ?: 0) {
                                                in 0..10 -> 16.sp
                                                in 11..20 -> 14.sp
                                                else -> 12.sp
                                            }
                                        ),
                                        modifier = Modifier
                                            .padding(8.dp)
                                            .padding(bottom = 8.dp)
                                    )
                                    if (writeQuestion?.definitionImageUrl?.isNotEmpty() == true) {
                                        AsyncImage(
                                            model = writeQuestion.definitionImageUrl,
                                            contentDescription = "Definition Image",
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier
                                                .size(80.dp)
                                                .clickable {
                                                    isImageViewerOpen = true
                                                    definitionImageUri =
                                                        writeQuestion.definitionImageUrl
                                                }
                                        )
                                    }
                                }
                            }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 16.dp),
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                TextField(
                                    value = userAnswer,
                                    maxLines = 1,
                                    keyboardActions = KeyboardActions(
                                        onDone = {
                                            userAnswer = ""
                                        }
                                    ),
                                    keyboardOptions = KeyboardOptions(
                                        autoCorrectEnabled = false,
                                        imeAction = ImeAction.Done,
                                        showKeyboardOnFocus = true
                                    ),
                                    placeholder = {
                                        Text(
                                            text = stringResource(R.string.txt_enter_your_answer),
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                fontWeight = FontWeight.Bold
                                            )
                                        )
                                    },
                                    trailingIcon = {
                                        TextButton(
                                            onClick = {
                                                debounceJob?.cancel()
                                                debounceJob = scope.launch {
                                                    delay(500)
                                                    onSubmitAnswer(
                                                        writeQuestion?.id ?: "",
                                                        WriteStatus.SKIPPED,
                                                        ""
                                                    )
                                                }
                                            }
                                        ) {
                                            Text(
                                                text = stringResource(R.string.txt_don_t_know),
                                                style = MaterialTheme.typography.bodyMedium.copy(
                                                    color = studySetColor,
                                                    fontWeight = FontWeight.Bold
                                                )
                                            )
                                        }
                                    },
                                    onValueChange = {
                                        userAnswer = it
                                    },
                                    colors = TextFieldDefaults.colors(
                                        unfocusedContainerColor = Color.Transparent,
                                        focusedContainerColor = Color.Transparent,
                                        focusedIndicatorColor = studySetColor,
                                        unfocusedIndicatorColor = studySetColor
                                    ),
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(horizontal = 8.dp),
                                )

                                IconButton(
                                    onClick = {
                                        debounceJob?.cancel()
                                        debounceJob = scope.launch {
                                            delay(500)
                                            if (userAnswer.isNotEmpty() && writeQuestion?.term == userAnswer) {
                                                onSubmitAnswer(
                                                    writeQuestion.id,
                                                    WriteStatus.CORRECT,
                                                    userAnswer
                                                )
                                                userAnswer = ""
                                            } else {
                                                onSubmitAnswer(
                                                    writeQuestion?.id ?: "",
                                                    WriteStatus.WRONG,
                                                    userAnswer
                                                )
                                                userAnswer = ""
                                            }
                                        }
                                    },
                                    enabled = userAnswer.isNotEmpty(),
                                    colors = IconButtonDefaults.iconButtonColors(
                                        disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(
                                            alpha = 0.5f
                                        ),
                                        contentColor = studySetColor
                                    )
                                ) {
                                    Icon(
                                        imageVector = Default.ArrowCircleUp,
                                        contentDescription = "Submit",
                                    )
                                }
                            }
                        }
                    }

                    true -> {
                        WriteFlashcardFinish(
                            isEndOfList = true,
                            wrongAnswerCount = wrongAnswerCount,
                            correctAnswerCount = flashCardList.size - wrongAnswerCount,
                            studySetColor = studySetColor,
                            learningTime = learningTime,
                            onContinueLearningClicked = onContinueLearningClicked,
                            listWrongAnswer = listWrongAnswer,
                            flashCardSize = flashCardList.size,
                            onRestartClicked = onRestart,
                            isGetAll = isGetAll
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
                            text = stringResource(R.string.txt_hint),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            text = writeQuestion?.hint ?: "",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            if (showUnfinishedLearningBottomSheet) {
                UnfinishedLearningBottomSheet(
                    onDismissRequest = {
                        showUnfinishedLearningBottomSheet = false
                    },
                    onKeepLearningClick = {
                        showUnfinishedLearningBottomSheet = false
                    },
                    onEndSessionClick = {
                        onEndSessionClick()
                        showUnfinishedLearningBottomSheet = false
                    },
                    sheetState = unFinishedLearningBottomSheetState
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun LearnByWriteScreenPreview() {
    QuickMemTheme {
        LearnByWrite()
    }
}