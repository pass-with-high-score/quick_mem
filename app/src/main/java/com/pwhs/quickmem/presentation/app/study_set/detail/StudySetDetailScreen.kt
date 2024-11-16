package com.pwhs.quickmem.presentation.app.study_set.detail

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.pwhs.quickmem.R
import com.pwhs.quickmem.core.data.FlipCardStatus
import com.pwhs.quickmem.core.utils.AppConstant
import com.pwhs.quickmem.domain.model.flashcard.StudySetFlashCardResponseModel
import com.pwhs.quickmem.domain.model.users.UserResponseModel
import com.pwhs.quickmem.presentation.app.study_set.detail.component.StudySetDetailTopAppBar
import com.pwhs.quickmem.presentation.app.study_set.detail.component.StudySetMoreOptionsBottomSheet
import com.pwhs.quickmem.presentation.app.study_set.detail.material.MaterialTabScreen
import com.pwhs.quickmem.presentation.app.study_set.detail.progress.ProgressTabScreen
import com.pwhs.quickmem.presentation.component.LoadingOverlay
import com.pwhs.quickmem.presentation.component.QuickMemAlertDialog
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.AddStudySetToClassesScreenDestination
import com.ramcosta.composedestinations.generated.destinations.AddStudySetToFoldersScreenDestination
import com.ramcosta.composedestinations.generated.destinations.CreateFlashCardScreenDestination
import com.ramcosta.composedestinations.generated.destinations.EditFlashCardScreenDestination
import com.ramcosta.composedestinations.generated.destinations.EditStudySetScreenDestination
import com.ramcosta.composedestinations.generated.destinations.FlipFlashCardScreenDestination
import com.ramcosta.composedestinations.generated.destinations.LearnByQuizScreenDestination
import com.ramcosta.composedestinations.generated.destinations.LearnByTrueFalseScreenDestination
import com.ramcosta.composedestinations.generated.destinations.LearnByWriteScreenDestination
import com.ramcosta.composedestinations.generated.destinations.StudySetInfoScreenDestination
import com.ramcosta.composedestinations.generated.destinations.UserDetailScreenDestination
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
    resultEditFlashCard: ResultRecipient<EditFlashCardScreenDestination, Boolean>,
    resultFlipFlashCard: ResultRecipient<FlipFlashCardScreenDestination, Boolean>,
    resultQuiz: ResultRecipient<LearnByQuizScreenDestination, Boolean>,
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

    resultFlipFlashCard.onNavResult { result ->
        when (result) {
            is NavResult.Canceled -> {}
            is NavResult.Value -> {
                if (result.value) {
                    viewModel.onEvent(StudySetDetailUiAction.Refresh)
                }
            }
        }
    }

    resultQuiz.onNavResult { result ->
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
                    viewModel.onEvent(StudySetDetailUiAction.Refresh)
                }

                StudySetDetailUiEvent.FlashCardStarred -> {
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
                    resultNavigator.setResult(true)
                    navigator.navigateUp()
                }

                StudySetDetailUiEvent.StudySetProgressReset -> {
                    viewModel.onEvent(StudySetDetailUiAction.Refresh)
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
        isLoading = uiState.isLoading,
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
        onAddToClass = {
            navigator.navigate(
                AddStudySetToClassesScreenDestination(
                    studySetId = uiState.id
                )
            )
        },
        onAddToFolder = {
            navigator.navigate(
                AddStudySetToFoldersScreenDestination(
                    studySetId = uiState.id
                )
            )
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
        onNavigateToStudySetInfo = {
            navigator.navigate(
                StudySetInfoScreenDestination(
                    title = uiState.title,
                    description = uiState.description,
                    isPublic = uiState.isPublic,
                    authorUsername = uiState.user.username,
                    authorAvatarUrl = uiState.user.avatarUrl,
                    creationDate = uiState.createdAt
                )
            )
        },
        linkShareCode = uiState.linkShareCode,
        onDeleteStudySet = {
            viewModel.onEvent(StudySetDetailUiAction.OnDeleteStudySetClicked)
        },
        onResetProgress = {
            viewModel.onEvent(StudySetDetailUiAction.OnResetProgressClicked(uiState.id))
        },
        onNavigateToQuiz = {
            navigator.navigate(
                LearnByQuizScreenDestination(
                    studySetId = uiState.id,
                    studySetTitle = uiState.title,
                    studySetDescription = uiState.description,
                    studySetColorId = uiState.colorModel.id,
                    studySetSubjectId = uiState.subject.id,
                )
            )
        },
        onNavigateToTrueFalse = {
            navigator.navigate(
                LearnByTrueFalseScreenDestination
            )
        },
        onNavigateToWrite = {
            navigator.navigate(
                LearnByWriteScreenDestination
            )
        },
        onNavigateToFlip = {
            navigator.navigate(
                FlipFlashCardScreenDestination(
                    studySetId = uiState.id,
                    studySetTitle = uiState.title,
                    studySetDescription = uiState.description,
                    studySetColorId = uiState.colorModel.id,
                    studySetSubjectId = uiState.subject.id,
                )
            )
        },
        onRefresh = {
            viewModel.onEvent(StudySetDetailUiAction.Refresh)
        },
        onNavigateToUserDetail = {
            navigator.navigate(
                UserDetailScreenDestination(
                    userId = uiState.user.id,
                    isOwner = uiState.isOwner
                )
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudySetDetail(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
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
    onNavigateToStudySetInfo: () -> Unit = {},
    onToggleStarredFlashCard: (String, Boolean) -> Unit = { _, _ -> },
    onEditStudySet: () -> Unit = {},
    onAddToClass: () -> Unit = {},
    onAddToFolder: () -> Unit = {},
    onDeleteStudySet: () -> Unit = {},
    onResetProgress: () -> Unit = {},
    onNavigateToQuiz: () -> Unit = {},
    onNavigateToTrueFalse: () -> Unit = {},
    onNavigateToWrite: () -> Unit = {},
    onNavigateToFlip: () -> Unit = {},
    onRefresh: () -> Unit = {},
    onNavigateToUserDetail: () -> Unit = {}
) {
    val context = LocalContext.current
    var tabIndex by remember { mutableIntStateOf(0) }
    val tabTitles = listOf(
        stringResource(R.string.txt_material),
        stringResource(R.string.txt_progress)
    )
    var showMoreBottomSheet by remember { mutableStateOf(false) }
    val sheetShowMoreState = rememberModalBottomSheetState()
    var showDeleteConfirmationDialog by remember { mutableStateOf(false) }
    var showResetProgressDialog by remember { mutableStateOf(false) }
    val refreshState = rememberPullToRefreshState()
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
                onNavigateToUserDetail = onNavigateToUserDetail,
                onMoreClicked = { showMoreBottomSheet = true },
                onShareClicked = {
                    val link = AppConstant.BASE_URL + "study-set/share/" + linkShareCode
                    val text = context.getString(R.string.txt_check_out_this_study_set, title, link)
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
        Box(
            modifier = Modifier
                .background(Color.Transparent)
                .padding(innerPadding)
        ) {
            PullToRefreshBox(
                onRefresh = onRefresh,
                isRefreshing = isLoading,
                state = refreshState
            ) {
                Column {
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
                            )
                        }
                    }
                    when (tabIndex) {
                        StudySetDetailEnum.MATERIAL.index -> MaterialTabScreen(
                            flashCards = flashCards,
                            onFlashCardClick = onFlashCardClick,
                            onDeleteFlashCardClick = onDeleteFlashCard,
                            onToggleStarClick = onToggleStarredFlashCard,
                            onEditFlashCardClick = onEditFlashCard,
                            onAddFlashCardClick = onAddFlashcard,
                            onNavigateToQuiz = onNavigateToQuiz,
                            onNavigateToTrueFalse = onNavigateToTrueFalse,
                            onNavigateToWrite = onNavigateToWrite,
                            onNavigateToFlip = onNavigateToFlip
                        )

                        StudySetDetailEnum.PROGRESS.index -> ProgressTabScreen(
                            totalStudySet = flashCardCount,
                            color = color,
                            studySetsNotLearnCount = flashCards.count { it.flipStatus == FlipCardStatus.NONE.name },
                            studySetsStillLearningCount = flashCards.count { it.flipStatus == FlipCardStatus.STILL_LEARNING.name },
                            studySetsKnowCount = flashCards.count { it.flipStatus == FlipCardStatus.KNOW.name },
                        )
                    }

                }
            }

            LoadingOverlay(
                isLoading = isLoading
            )
        }
    }
    StudySetMoreOptionsBottomSheet(
        onEditStudySet = onEditStudySet,
        onDeleteStudySet = {
            showDeleteConfirmationDialog = true
        },
        onAddToClass = onAddToClass,
        onAddToFolder = onAddToFolder,
        showMoreBottomSheet = showMoreBottomSheet,
        sheetShowMoreState = sheetShowMoreState,
        onDismissRequest = { showMoreBottomSheet = false },
        onInfoStudySet = onNavigateToStudySetInfo,
        onResetProgress = {
            showResetProgressDialog = true
            showMoreBottomSheet = false
        }
    )
    if (showDeleteConfirmationDialog) {
        QuickMemAlertDialog(
            onDismissRequest = { showDeleteConfirmationDialog = false },
            onConfirm = {
                onDeleteStudySet()
                showDeleteConfirmationDialog = false
            },
            title = stringResource(R.string.txt_delete_study_set),
            text = stringResource(R.string.txt_are_you_sure_you_want_to_delete_this_study_set),
            confirmButtonTitle = stringResource(R.string.txt_delete),
            dismissButtonTitle = stringResource(R.string.txt_cancel),
        )
    }

    if (showResetProgressDialog) {
        QuickMemAlertDialog(
            onDismissRequest = { showResetProgressDialog = false },
            onConfirm = {
                onResetProgress()
                showResetProgressDialog = false
            },
            title = stringResource(R.string.txt_reset_progress),
            text = stringResource(R.string.txt_are_you_sure_you_want_to_reset_the_progress_of_this_study_set),
            confirmButtonTitle = stringResource(R.string.txt_reset),
            dismissButtonTitle = stringResource(R.string.txt_cancel),
            buttonColor = Color.Red
        )
    }
}

@Preview(showBackground = true)
@Composable
fun StudySetDetailScreenPreview() {
    StudySetDetail()
}