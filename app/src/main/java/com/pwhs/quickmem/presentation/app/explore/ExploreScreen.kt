package com.pwhs.quickmem.presentation.app.explore

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pwhs.quickmem.core.data.enums.DifficultyLevel
import com.pwhs.quickmem.core.data.enums.QuestionType
import com.pwhs.quickmem.domain.model.streak.GetTopStreakResponseModel
import com.pwhs.quickmem.presentation.app.explore.create_study_set_ai.CreateStudySetAITab
import com.pwhs.quickmem.presentation.app.explore.top_streak.TopStreakScreen
import com.pwhs.quickmem.presentation.component.LoadingOverlay
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.StudySetDetailScreenDestination
import com.ramcosta.composedestinations.generated.destinations.UserDetailScreenDestination
import com.ramcosta.composedestinations.generated.destinations.UserDetailScreenDestination.invoke
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination<RootGraph>
fun ExploreScreen(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator,
    viewModel: ExploreViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is ExploreUiEvent.Error -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }

                is ExploreUiEvent.CreatedStudySet -> {
                    navigator.navigate(
                        StudySetDetailScreenDestination(
                            id = event.studySetId,
                        )
                    )
                }
            }
        }
    }

    Explore(
        modifier = modifier,
        isLoading = uiState.isLoading,
        topStreaks = uiState.topStreaks,
        rankOwner = uiState.rankOwner,
        streakOwner = uiState.streakOwner,
        title = uiState.title,
        description = uiState.description,
        numberOfFlashcards = uiState.numberOfFlashcards,
        language = uiState.language,
        questionType = uiState.questionType,
        difficultyLevel = uiState.difficulty,
        onTitleChange = { viewModel.onEvent(ExploreUiAction.OnTitleChanged(it)) },
        onDescriptionChange = { viewModel.onEvent(ExploreUiAction.OnDescriptionChanged(it)) },
        onNumberOfFlashcardsChange = {
            viewModel.onEvent(
                ExploreUiAction.OnNumberOfFlashcardsChange(
                    it
                )
            )
        },
        onLanguageChange = { viewModel.onEvent(ExploreUiAction.OnLanguageChanged(it)) },
        onQuestionTypeChange = { viewModel.onEvent(ExploreUiAction.OnQuestionTypeChanged(it)) },
        onDifficultyLevelChange = { viewModel.onEvent(ExploreUiAction.OnDifficultyLevelChanged(it)) },
        onTopStreakRefresh = { viewModel.onEvent(ExploreUiAction.RefreshTopStreaks) },
        onClickToUserDetail = {
            navigator.navigate(
                UserDetailScreenDestination(
                    userId = it,
                )
            )
        },
        onCreateStudySet = {
            viewModel.onEvent(ExploreUiAction.OnCreateStudySet)
        },
        errorMessage = uiState.errorMessage
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Explore(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    rankOwner: Int? = null,
    streakOwner: GetTopStreakResponseModel? = null,
    topStreaks: List<GetTopStreakResponseModel> = emptyList(),
    onClickToUserDetail: (String) -> Unit = {},
    onTopStreakRefresh: () -> Unit = {},
    title: String = "",
    description: String = "",
    numberOfFlashcards: Int = 0,
    language: String = "",
    questionType: QuestionType = QuestionType.MULTIPLE_CHOICE,
    difficultyLevel: DifficultyLevel = DifficultyLevel.EASY,
    onTitleChange: (String) -> Unit = {},
    onDescriptionChange: (String) -> Unit = {},
    onNumberOfFlashcardsChange: (Int) -> Unit = {},
    onLanguageChange: (String) -> Unit = {},
    onQuestionTypeChange: (QuestionType) -> Unit = {},
    onDifficultyLevelChange: (DifficultyLevel) -> Unit = {},
    onCreateStudySet: () -> Unit = {},
    errorMessage: String = ""
) {
    var tabIndex by rememberSaveable { mutableIntStateOf(0) }
    val tabTitles = listOf(
        "Create Study Set AI",
        "Top Streak",
    )

    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Explore",
                        style = typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                actions = {

                }
            )
        }
    ) { innerPadding ->
        Box {
            Column(
                modifier = modifier.padding(innerPadding),
            ) {
                TabRow(
                    selectedTabIndex = tabIndex,
                    indicator = { tabPositions ->
                        SecondaryIndicator(
                            Modifier.tabIndicatorOffset(tabPositions[tabIndex]),
                            color = colorScheme.primary,
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
                    ExploreTabEnum.CREATE_STUDY_SET.index -> CreateStudySetAITab(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        title = title,
                        description = description,
                        numberOfFlashcards = numberOfFlashcards,
                        language = language,
                        questionType = questionType,
                        difficultyLevel = difficultyLevel,
                        onTitleChange = onTitleChange,
                        onDescriptionChange = onDescriptionChange,
                        onNumberOfFlashcardsChange = onNumberOfFlashcardsChange,
                        onLanguageChange = onLanguageChange,
                        onQuestionTypeChange = onQuestionTypeChange,
                        onDifficultyLevelChange = onDifficultyLevelChange,
                        onCreateStudySet = onCreateStudySet
                    )

                    ExploreTabEnum.TOP_STREAK.index -> TopStreakScreen(
                        isLoading = isLoading,
                        rankOwner = rankOwner,
                        topStreaks = topStreaks,
                        streakOwner = streakOwner,
                        onClickToUserDetail = {
                            onClickToUserDetail(it.userId)
                        },
                        onTopStreakRefresh = onTopStreakRefresh,
                    )
                }
            }
            LoadingOverlay(isLoading = isLoading)
        }
    }
}

@Preview
@Composable
private fun ExploreScreenPreview() {
    MaterialTheme {
        Explore()
    }
}