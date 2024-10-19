package com.pwhs.quickmem.presentation.app.study_set.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.IosShare
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.pwhs.quickmem.domain.model.flashcard.StudySetFlashCardResponseModel
import com.pwhs.quickmem.domain.model.users.UserResponseModel
import com.pwhs.quickmem.presentation.app.study_set.detail.material.MaterialTabScreen
import com.pwhs.quickmem.presentation.app.study_set.detail.progress.ProgressTabScreen
import com.pwhs.quickmem.util.gradientBackground
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.CreateFlashCardScreenDestination
import com.ramcosta.composedestinations.generated.destinations.EditStudySetScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
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
    resultBakFlashCard: ResultRecipient<CreateFlashCardScreenDestination, Boolean>,
    resultEditFlashCard: ResultRecipient<EditStudySetScreenDestination, Boolean>
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
                            studySetSubjectId = uiState.subject.id,
                            studySetColorId = uiState.colorModel.id,
                            studySetIsPublic = uiState.isPublic
                        )
                    )
                }
            }
        }
    }
    StudySetDetail(
        modifier = modifier,
        onNavigateBack = {
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
        onToggleStarredFlashCard = { id, isStarred ->
            viewModel.onEvent(StudySetDetailUiAction.OnStarFlashCardClicked(id, isStarred))
        },
        onEditStudySet = {
            viewModel.onEvent(StudySetDetailUiAction.OnEditStudySetClicked)
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
    color: Color = Color.Blue,
    flashCardCount: Int = 0,
    userResponse: UserResponseModel = UserResponseModel(),
    flashCards: List<StudySetFlashCardResponseModel> = emptyList(),
    onFlashCardClick: (String) -> Unit = {},
    onDeleteFlashCard: () -> Unit = {},
    onToggleStarredFlashCard: (String, Boolean) -> Unit = { _, _ -> },
    onEditStudySet: () -> Unit = {}
) {
    var tabIndex by remember { mutableIntStateOf(0) }
    val tabTitles = listOf("Material", "Progress")
    var showMoreBottomSheet by remember { mutableStateOf(false) }
    val sheetShowMoreState = rememberModalBottomSheetState()
    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            LargeTopAppBar(
                title = {
                    Column {
                        Text(
                            title, style = typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = colorScheme.onSurface
                            )
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            AsyncImage(
                                model = userResponse.avatarUrl,
                                contentDescription = "User Avatar",
                                modifier = Modifier
                                    .size(24.dp)
                                    .padding(end = 8.dp)
                            )
                            Text(
                                userResponse.username,
                                style = typography.bodyMedium.copy(
                                    color = colorScheme.secondary
                                )
                            )
                            VerticalDivider(
                                modifier = Modifier
                                    .padding(horizontal = 8.dp)
                                    .size(16.dp)
                            )
                            Text(
                                "$flashCardCount flashcards",
                                style = typography.bodyMedium.copy(
                                    color = colorScheme.secondary
                                )
                            )
                        }
                    }
                },
                modifier = modifier.background(color.gradientBackground()),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                ),
                expandedHeight = 120.dp,
                collapsedHeight = 56.dp,
                navigationIcon = {
                    IconButton(
                        onClick = onNavigateBack,
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = colorScheme.onSurface
                        )
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { }
                    ) {
                        Icon(
                            imageVector = Icons.Default.IosShare,
                            contentDescription = "Share"
                        )
                    }
                    IconButton(
                        onClick = onAddFlashcard
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add"
                        )
                    }
                    IconButton(
                        onClick = {
                            showMoreBottomSheet = true
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "More"
                        )
                    }
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
                    onToggleStarClick = onToggleStarredFlashCard
                )

                1 -> ProgressTabScreen()
            }
        }
    }
    if (showMoreBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showMoreBottomSheet = false },
            sheetState = sheetShowMoreState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                ItemMenuBottomSheet(
                    onClick = {
                        onEditStudySet()
                        showMoreBottomSheet = false
                    },
                    icon = Icons.Outlined.Edit,
                    title = "Edit"
                )
                ItemMenuBottomSheet(
                    onClick = { },
                    icon = Icons.Outlined.Folder,
                    title = "Add to folder"
                )
                ItemMenuBottomSheet(
                    onClick = { },
                    icon = Icons.Outlined.Group,
                    title = "Add to class"
                )
                ItemMenuBottomSheet(
                    onClick = { },
                    icon = Icons.Outlined.ContentCopy,
                    title = "Save and edit"
                )
                ItemMenuBottomSheet(
                    onClick = { },
                    icon = Icons.Outlined.Refresh,
                    title = "Reset progress"
                )
                ItemMenuBottomSheet(
                    onClick = { },
                    icon = Icons.Outlined.Info,
                    title = "Set info"
                )
                ItemMenuBottomSheet(
                    onClick = { },
                    icon = Icons.Default.DeleteOutline,
                    title = "Delete study set",
                    color = Color.Red
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StudySetDetailScreenPreview() {
    StudySetDetail()
}