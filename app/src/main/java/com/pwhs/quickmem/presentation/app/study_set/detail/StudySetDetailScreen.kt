package com.pwhs.quickmem.presentation.app.study_set.detail

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.pwhs.quickmem.core.utils.AppConstant
import com.pwhs.quickmem.domain.model.flashcard.StudySetFlashCardResponseModel
import com.pwhs.quickmem.domain.model.users.UserResponseModel
import com.pwhs.quickmem.presentation.app.study_set.detail.component.StudySetDetailTopAppBar
import com.pwhs.quickmem.presentation.app.study_set.detail.component.StudySetFlashCardList
import com.pwhs.quickmem.presentation.app.study_set.detail.material.MaterialTabScreen
import com.pwhs.quickmem.presentation.app.study_set.detail.progress.ProgressTabScreen
import com.pwhs.quickmem.presentation.component.QuickMemAlertDialog
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.CreateFlashCardScreenDestination
import com.ramcosta.composedestinations.generated.destinations.EditFlashCardScreenDestination
import com.ramcosta.composedestinations.generated.destinations.EditStudySetScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.ramcosta.composedestinations.result.ResultRecipient
import timber.log.Timber

@Destination<RootGraph>(
    navArgs = StudySetDetailArgs::class
)
@Composable
fun StudySetDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: StudySetDetailViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    resultNavigator: ResultBackNavigator<Boolean>,
    resultBakFlashCard: ResultRecipient<CreateFlashCardScreenDestination, Boolean>,
    resultEditStudySet: ResultRecipient<EditStudySetScreenDestination, Boolean>,
    resultEditFlashCard: ResultRecipient<EditFlashCardScreenDestination, Boolean>
) {
    val uiState by viewModel.uiState.collectAsState()

    resultBakFlashCard.onNavResult { result ->
        when (result) {
            is NavResult.Canceled -> {}
            is NavResult.Value -> {
                if (result.value) {
                    viewModel.onEvent(StudySetDetailUiAction.Refresh)
                }
            }
        }
    }
    resultEditStudySet.onNavResult { result ->
        when (result) {
            is NavResult.Canceled -> {}
            is NavResult.Value -> {
                if (result.value) {
                    viewModel.onEvent(StudySetDetailUiAction.Refresh)
                }
            }
        }
    }

    resultEditFlashCard.onNavResult { result ->
        when (result) {
            is NavResult.Canceled -> {}
            is NavResult.Value -> {
                if (result.value) {
                    viewModel.onEvent(StudySetDetailUiAction.Refresh)
                }
            }
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                StudySetDetailUiEvent.FlashCardDeleted -> {
                    Timber.d("FlashCardDeleted")
                    viewModel.onEvent(StudySetDetailUiAction.Refresh)
                }

                StudySetDetailUiEvent.FlashCardStarred -> {
                    Timber.d("FlashCardStarred")
                    viewModel.onEvent(StudySetDetailUiAction.Refresh)
                }

                StudySetDetailUiEvent.NavigateToEditStudySet -> {
                    Timber.d("${uiState.id} ${uiState.title} ${uiState.subject.id} ${uiState.colorModel.id} ${uiState.isPublic}")
                    navigator.navigate(
                        EditStudySetScreenDestination(
                            studySetId = uiState.id,
                            studySetTitle = uiState.title,
                            studySetDescription = uiState.description,
                            studySetSubjectId = uiState.subject.id,
                            studySetColorId = uiState.colorModel.id,
                            studySetIsPublic = uiState.isPublic
                        )
                    )
                }

                StudySetDetailUiEvent.NavigateToEditFlashCard -> {
                    val flashCard =
                        uiState.flashCards.find { it.id == uiState.idOfFlashCardSelected }
                    navigator.navigate(
                        EditFlashCardScreenDestination(
                            flashcardId = flashCard!!.id,
                            term = flashCard.term,
                            definition = flashCard.definition,
                            definitionImageUrl = flashCard.definitionImageURL ?: "",
                            hint = flashCard.hint ?: "",
                            explanation = flashCard.explanation ?: ""
                        )
                    )
                }

                StudySetDetailUiEvent.StudySetDeleted -> {
                    Timber.d("StudySetDeleted")
                    resultNavigator.setResult(true)
                    navigator.navigateUp()
                }
            }
        }
    }
    StudySetDetail(
        modifier = modifier,
        onNavigateBack = {
            resultNavigator.setResult(true)
            navigator.navigateUp()
        },
        onAddFlashcard = {
            navigator.navigate(
                CreateFlashCardScreenDestination(
                    studySetId = uiState.id,
                    studySetTitle = uiState.title
                )
            )
        },
        title = uiState.title,
        color = uiState.color,
        flashCardCount = uiState.flashCardCount,
        flashCards = uiState.flashCards,
        userResponse = uiState.user,
        onFlashCardClick = { id ->
            viewModel.onEvent(StudySetDetailUiAction.OnIdOfFlashCardSelectedChanged(id))
        },
        onDeleteFlashCard = {
            viewModel.onEvent(StudySetDetailUiAction.OnDeleteFlashCardClicked)
        },
        onEditFlashCard = {
            viewModel.onEvent(StudySetDetailUiAction.OnEditFlashCardClicked)
        },
        onToggleStarredFlashCard = { id, isStarred ->
            viewModel.onEvent(StudySetDetailUiAction.OnStarFlashCardClicked(id, isStarred))
        },
        onEditStudySet = {
            viewModel.onEvent(StudySetDetailUiAction.OnEditStudySetClicked)
        },
        linkShareCode = uiState.linkShareCode,
        onDeleteStudySet = {
            viewModel.onEvent(StudySetDetailUiAction.OnDeleteStudySetClicked)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudySetDetail(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit = {},
    onAddFlashcard: () -> Unit = {},
    title: String = "",
    linkShareCode: String = "",
    color: Color = Color.Blue,
    flashCardCount: Int = 0,
    userResponse: UserResponseModel = UserResponseModel(),
    flashCards: List<StudySetFlashCardResponseModel> = emptyList(),
    onFlashCardClick: (String) -> Unit = {},
    onDeleteFlashCard: () -> Unit = {},
    onEditFlashCard: () -> Unit = {},
    onToggleStarredFlashCard: (String, Boolean) -> Unit = { _, _ -> },
    onEditStudySet: () -> Unit = {},
    onDeleteStudySet: () -> Unit = {}
) {
    val context = LocalContext.current
    var tabIndex by remember { mutableIntStateOf(0) }
    val tabTitles = listOf("Material", "Progress")
    var showMoreBottomSheet by remember { mutableStateOf(false) }
    val sheetShowMoreState = rememberModalBottomSheetState()
    var showDeleteConfirmationDialog by remember { mutableStateOf(false) }
    Scaffold(
        modifier = modifier,
        containerColor = Color.Transparent,
        topBar = {
            StudySetDetailTopAppBar(

                title = title,
                color = color,
                userResponse = userResponse,
                flashCardCount = flashCardCount,
                onNavigateBack = onNavigateBack,
                onAddFlashcard = onAddFlashcard,
                onMoreClicked = { showMoreBottomSheet = true },
                onShareClicked = {
                    val link = AppConstant.BASE_URL + "study-set/share/" + linkShareCode
                    val text = "Check out this study set: $title\n$link"
                    val sendIntent = Intent(Intent.ACTION_SEND).apply {
                        putExtra(Intent.EXTRA_TEXT, text)
                        type = "text/plain"
                    }
                    val shareIntent = Intent.createChooser(sendIntent, null)
                    context.startActivity(shareIntent)
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .background(Color.Transparent)
                .padding(innerPadding)

        ) {
            TabRow(
                selectedTabIndex = tabIndex,
                indicator = { tabPositions ->
                    SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[tabIndex]),
                        color = color,
                    )
                },
                contentColor = colorScheme.onSurface,
            ) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        text = {
                            Text(
                                title, style = typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = if (tabIndex == index) Color.Black else Color.Gray
                                )
                            )
                        },
                        selected = tabIndex == index,
                        onClick = { tabIndex = index },
                        icon = {
                            when (index) {
                                0 -> {}
                                1 -> {}
                            }
                        }
                    )
                }
            }
            when (tabIndex) {
                0 -> MaterialTabScreen(
                    flashCards = flashCards,
                    onFlashCardClick = onFlashCardClick,
                    onDeleteFlashCardClick = onDeleteFlashCard,
                    onToggleStarClick = onToggleStarredFlashCard,
                    onEditFlashCardClick = onEditFlashCard,
                    onAddFlashCardClick = onAddFlashcard
                )

                1 -> ProgressTabScreen()
            }
        }
    }
    StudySetFlashCardList(
        onEditStudySet = {
            onEditStudySet()
            showMoreBottomSheet = false
        },
        onDeleteStudySet = {
            showDeleteConfirmationDialog = true
            showMoreBottomSheet = false
        },
        showMoreBottomSheet = showMoreBottomSheet,
        sheetShowMoreState = sheetShowMoreState,
        onDismissRequest = { showMoreBottomSheet = false }
    )
    if (showDeleteConfirmationDialog) {
        QuickMemAlertDialog(
            onDismissRequest = { showDeleteConfirmationDialog = false },
            onConfirm = {
                onDeleteStudySet()
                showDeleteConfirmationDialog = false
            },
            title = "Delete Study Set",
            text = "Are you sure you want to delete this study set?",
            confirmButtonTitle = "Delete",
            dismissButtonTitle = "Cancel",
        )
    }
}

@Preview(showBackground = true)
@Composable
fun StudySetDetailScreenPreview() {
    StudySetDetail()
}