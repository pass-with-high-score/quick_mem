package com.pwhs.quickmem.presentation.app.search_result

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pwhs.quickmem.domain.model.classes.GetClassByOwnerResponseModel
import com.pwhs.quickmem.domain.model.folder.GetFolderResponseModel
import com.pwhs.quickmem.domain.model.study_set.GetStudySetResponseModel
import com.pwhs.quickmem.presentation.app.search_result.all_result.ListAllResultScreen
import com.pwhs.quickmem.presentation.app.search_result.classes.ListResultClassesScreen
import com.pwhs.quickmem.presentation.app.search_result.folder.ListResultFolderScreen
import com.pwhs.quickmem.presentation.app.search_result.study_set.ListResultStudySetScreen
import com.pwhs.quickmem.ui.theme.QuickMemTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.ClassDetailScreenDestination
import com.ramcosta.composedestinations.generated.destinations.ClassDetailScreenDestination.invoke
import com.ramcosta.composedestinations.generated.destinations.CreateFolderScreenDestination
import com.ramcosta.composedestinations.generated.destinations.FolderDetailScreenDestination
import com.ramcosta.composedestinations.generated.destinations.FolderDetailScreenDestination.invoke
import com.ramcosta.composedestinations.generated.destinations.StudySetDetailScreenDestination
import com.ramcosta.composedestinations.generated.destinations.StudySetDetailScreenDestination.invoke
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<RootGraph>(
    navArgs = SearchResultArgs::class
)
@Composable
fun SearchResultScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchResultViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {

                else -> {}
            }
        }
    }
    SearchResult(
        modifier = modifier,
        query = uiState.query,
        isLoading = uiState.isLoading,
        studySets = uiState.studySets,
        classes = uiState.classes,
        folders = uiState.folders,
        avatarUrl = uiState.userAvatar,
        username = uiState.username,
        onFilterOptionBottomSheet = {

        },
        onStudySetRefresh = {

        },
        onClassRefresh = {

        },
        onFolderRefresh = {

        },
        onStudySetClick = {
            navigator.navigate(
                StudySetDetailScreenDestination(
                    id = it.id,
                    code = ""
                )
            )
        },
        onClassClick = {
            navigator.navigate(
                ClassDetailScreenDestination(
                    id = it.id,
                    code = it.joinToken ?: "",
                    title = it.title,
                    description = it.description
                )
            )
        },
        onFolderClick = {
            navigator.navigate(
                FolderDetailScreenDestination(
                    id = it.id,
                    code = ""
                )
            )
        },
        navigateToCreateFolder = {
            navigator.navigate(CreateFolderScreenDestination)
        }
    )
}

@Composable
fun SearchResult(
    modifier: Modifier = Modifier,
    query: String = "",
    isLoading: Boolean = false,
    avatarUrl: String = "",
    username: String = "",
    onStudySetRefresh: () -> Unit = {},
    onClassRefresh: () -> Unit = {},
    onFolderRefresh: () -> Unit = {},
    studySets: List<GetStudySetResponseModel> = emptyList(),
    classes: List<GetClassByOwnerResponseModel> = emptyList(),
    folders: List<GetFolderResponseModel> = emptyList(),
    onStudySetClick: (GetStudySetResponseModel) -> Unit = {},
    onClassClick: (GetClassByOwnerResponseModel) -> Unit = {},
    onFolderClick: (GetFolderResponseModel) -> Unit = {},
    navigateToCreateFolder: () -> Unit = {},
    onFilterOptionBottomSheet: () -> Unit = {}
) {
    Scaffold { innerPadding ->
        var tabIndex by remember { mutableIntStateOf(0) }
        val tabTitles = listOf(
            "All Result",
            "Study Set",
            "Folder",
            "Class"
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            ScrollableTabRow(
                selectedTabIndex = tabIndex,
                indicator = { tabPositions ->
                    SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[tabIndex])
                    )
                },
                contentColor = colorScheme.onSurface,
                edgePadding = 8.dp
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
                SearchResultEnum.ALL_RESULT.index -> ListAllResultScreen(

                )
                SearchResultEnum.STUDY_SET.index -> ListResultStudySetScreen(
                    isLoading = isLoading,
                    studySets = studySets,
                    avatarUrl = avatarUrl,
                    username = username,
                    onStudySetClick = onStudySetClick,
                    onStudySetRefresh = onStudySetRefresh,
                    onFilterOptionBottomSheet = onFilterOptionBottomSheet
                )
                SearchResultEnum.FOLDER.index -> ListResultFolderScreen(
                    modifier = modifier,
                    isLoading = isLoading,
                    folders = folders,
                    onFolderClick = onFolderClick,
                    onAddFolderClick = navigateToCreateFolder,
                    onFolderRefresh = onFolderRefresh
                )
                SearchResultEnum.CLASS.index -> ListResultClassesScreen(
                    modifier = modifier,
                    isLoading = isLoading,
                    classes = classes,
                    onClassClicked = onClassClick,
                    onClassRefresh = onClassRefresh,
                )
            }
            Text(text = "Search Result: $query")
        }
    }
}

@Preview
@Composable
private fun SearchResultScreen() {
    QuickMemTheme {
        SearchResult()
    }
}