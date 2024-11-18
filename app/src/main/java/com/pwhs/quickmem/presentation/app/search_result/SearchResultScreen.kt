package com.pwhs.quickmem.presentation.app.search_result

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.pwhs.quickmem.domain.model.classes.GetClassByOwnerResponseModel
import com.pwhs.quickmem.domain.model.color.ColorModel
import com.pwhs.quickmem.domain.model.folder.GetFolderResponseModel
import com.pwhs.quickmem.domain.model.study_set.GetStudySetResponseModel
import com.pwhs.quickmem.domain.model.subject.SubjectModel
import com.pwhs.quickmem.domain.model.users.SearchUserResponseModel
import com.pwhs.quickmem.presentation.app.search_result.all_result.ListAllResultScreen
import com.pwhs.quickmem.presentation.app.search_result.classes.ListResultClassesScreen
import com.pwhs.quickmem.presentation.app.search_result.component.SearchResultEnum
import com.pwhs.quickmem.presentation.app.search_result.component.TopBarSearchResult
import com.pwhs.quickmem.presentation.app.search_result.folder.ListResultFolderScreen
import com.pwhs.quickmem.presentation.app.search_result.study_set.ListResultStudySetScreen
import com.pwhs.quickmem.presentation.app.search_result.study_set.component.FilterStudySetBottomSheet
import com.pwhs.quickmem.presentation.app.search_result.study_set.enum.SearchResultCreatorEnum
import com.pwhs.quickmem.presentation.app.search_result.study_set.enum.SearchResultSizeEnum
import com.pwhs.quickmem.presentation.app.search_result.user.ListResultUserScreen
import com.pwhs.quickmem.presentation.component.LoadingOverlay
import com.pwhs.quickmem.ui.theme.QuickMemTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.ClassDetailScreenDestination
import com.ramcosta.composedestinations.generated.destinations.FolderDetailScreenDestination
import com.ramcosta.composedestinations.generated.destinations.StudySetDetailScreenDestination
import com.ramcosta.composedestinations.generated.destinations.UserDetailScreenDestination
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
    val context = LocalContext.current
    var tabIndex by remember { mutableIntStateOf(0) }

    val studySetItems: LazyPagingItems<GetStudySetResponseModel> =
        viewModel.studySetState.collectAsLazyPagingItems()

    val folderItems: LazyPagingItems<GetFolderResponseModel> =
        viewModel.folderState.collectAsLazyPagingItems()

    val classItems: LazyPagingItems<GetClassByOwnerResponseModel> =
        viewModel.classState.collectAsLazyPagingItems()

    val userItems: LazyPagingItems<SearchUserResponseModel> =
        viewModel.userState.collectAsLazyPagingItems()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is SearchResultUiEvent.Error -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    SearchResult(
        modifier = modifier,
        tabIndex = tabIndex,
        query = uiState.query,
        onTabSelected = { tabIndex = it },
        studySets = studySetItems,
        classes = classItems,
        folders = folderItems,
        users = userItems,
        colorModel = uiState.colorModel,
        onColorChange = {
            viewModel.onEvent(SearchResultUiAction.ColorChanged(it))
        },
        subjectModel = uiState.subjectModel,
        onSubjectChange = {
            viewModel.onEvent(SearchResultUiAction.SubjectChanged(it))
        },
        sizeModel = uiState.sizeStudySetModel,
        onSizeChange = {
            viewModel.onEvent(SearchResultUiAction.SizeChanged(it))
        },
        creatorTypeModel = uiState.creatorTypeModel,
        onCreatorChange = {
            viewModel.onEvent(SearchResultUiAction.CreatorTypeChanged(it))
        },
        onApplyClick = {
            viewModel.onEvent(SearchResultUiAction.ApplyFilter)
        },
        onStudySetRefresh = {
            viewModel.onEvent(SearchResultUiAction.RefreshStudySets)
        },
        onClassRefresh = {
            viewModel.onEvent(SearchResultUiAction.RefreshClasses)
        },
        onFolderRefresh = {
            viewModel.onEvent(SearchResultUiAction.RefreshFolders)
        },
        onStudySetClick = {
            navigator.navigate(
                StudySetDetailScreenDestination(
                    id = it?.id ?: "",
                    code = ""
                )
            )
        },
        onClassClick = {
            navigator.navigate(
                ClassDetailScreenDestination(
                    id = it?.id ?: "",
                    code = it?.joinToken ?: "",
                    title = it?.title ?: "",
                    description = it?.description ?: ""
                )
            )
        },
        onFolderClick = {
            navigator.navigate(
                FolderDetailScreenDestination(
                    id = it?.id ?: "",
                    code = ""
                )
            )
        },
        onUserItemClicked = {
            navigator.navigate(
                UserDetailScreenDestination(
                    userId = it?.id ?: "",
                    isOwner = uiState.userResponseModel.id == (it?.id ?: "")
                )
            )
        },
        onNavigateBack = {
            navigator.navigateUp()
        },
        onResetClick = {
            viewModel.onEvent(SearchResultUiAction.ResetFilter)
        },
        onSeeAllClickClass = { tabIndex = SearchResultEnum.CLASS.index },
        onSeeAllClickFolder = { tabIndex = SearchResultEnum.FOLDER.index },
        onSeeAllClickStudySet = { tabIndex = SearchResultEnum.STUDY_SET.index },
        onSeeAllClickUsers = { tabIndex = SearchResultEnum.USER.index }
    )
}

@Composable
fun SearchResult(
    isLoading: Boolean = false,
    query: String = "",
    modifier: Modifier = Modifier,
    tabIndex: Int,
    onTabSelected: (Int) -> Unit,
    colorModel: ColorModel? = ColorModel.defaultColors.first(),
    onColorChange: (ColorModel) -> Unit = {},
    subjectModel: SubjectModel? = SubjectModel.defaultSubjects.first(),
    onSubjectChange: (SubjectModel) -> Unit = {},
    sizeModel: SearchResultSizeEnum = SearchResultSizeEnum.ALL,
    onSizeChange: (SearchResultSizeEnum) -> Unit = {},
    creatorTypeModel: SearchResultCreatorEnum = SearchResultCreatorEnum.ALL,
    onCreatorChange: (SearchResultCreatorEnum) -> Unit = {},
    onApplyClick: () -> Unit = {},
    onStudySetRefresh: () -> Unit = {},
    onClassRefresh: () -> Unit = {},
    onFolderRefresh: () -> Unit = {},
    studySets: LazyPagingItems<GetStudySetResponseModel>? = null,
    classes: LazyPagingItems<GetClassByOwnerResponseModel>? = null,
    folders: LazyPagingItems<GetFolderResponseModel>? = null,
    users: LazyPagingItems<SearchUserResponseModel>? = null,
    onStudySetClick: (GetStudySetResponseModel?) -> Unit = {},
    onClassClick: (GetClassByOwnerResponseModel?) -> Unit = {},
    onFolderClick: (GetFolderResponseModel?) -> Unit = {},
    onUserItemClicked: (SearchUserResponseModel?) -> Unit = {},
    onNavigateBack: () -> Unit = {},
    onResetClick: () -> Unit = {},
    onSeeAllClickStudySet: () -> Unit = {},
    onSeeAllClickFolder: () -> Unit = {},
    onSeeAllClickClass: () -> Unit = {},
    onSeeAllClickUsers: () -> Unit = {}
) {
    var showFilterBottomSheet by remember { mutableStateOf(false) }
    val tabTitles = listOf(
        "All Result",
        "Study Set",
        "Folder",
        "Class",
        "User"
    )
    Scaffold(
        containerColor = colorScheme.background,
        modifier = modifier,
        topBar = {
            TopBarSearchResult(
                onNavigateBack = onNavigateBack,
                title = "Result \"${query}\"",
                onClickFilter = {
                    if (tabIndex == SearchResultEnum.STUDY_SET.index) {
                        showFilterBottomSheet = true
                    }
                },
                showFilterIcon = tabIndex == SearchResultEnum.STUDY_SET.index
            )
        }
    ) { innerPadding ->

        Box {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
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
                            onClick = { onTabSelected(index) },
                        )
                    }
                }
                when (tabIndex) {
                    SearchResultEnum.ALL_RESULT.index -> ListAllResultScreen(
                        studySets = studySets,
                        classes = classes,
                        folders = folders,
                        users = users,
                        onStudySetClick = onStudySetClick,
                        onFolderClick = onFolderClick,
                        onClassClicked = onClassClick,
                        onUserItemClicked = onUserItemClicked,
                        onSeeAllClickClass = onSeeAllClickClass,
                        onSeeAllClickFolder = onSeeAllClickFolder,
                        onSeeAllClickStudySet = onSeeAllClickStudySet,
                        onSeeAllClickUsers = onSeeAllClickUsers,
                    )

                    SearchResultEnum.STUDY_SET.index -> ListResultStudySetScreen(
                        studySets = studySets,
                        onStudySetClick = onStudySetClick,
                        onStudySetRefresh = onStudySetRefresh,
                    )

                    SearchResultEnum.FOLDER.index -> ListResultFolderScreen(
                        modifier = modifier,
                        folders = folders,
                        onFolderClick = onFolderClick,
                        onFolderRefresh = onFolderRefresh
                    )

                    SearchResultEnum.CLASS.index -> ListResultClassesScreen(
                        modifier = modifier,
                        classes = classes,
                        onClassClicked = onClassClick,
                        onClassRefresh = onClassRefresh,
                    )

                    SearchResultEnum.USER.index -> ListResultUserScreen(
                        modifier = modifier,
                        users = users,
                        onUserItemClicked = onUserItemClicked
                    )
                }
            }
            LoadingOverlay(
                isLoading = isLoading
            )
        }
    }
    if (showFilterBottomSheet) {
        when (tabIndex) {
            SearchResultEnum.STUDY_SET.index -> FilterStudySetBottomSheet(
                colorModel = colorModel,
                onColorChange = onColorChange,
                onSubjectChange = onSubjectChange,
                subjectModel = subjectModel,
                onResetClick = onResetClick,
                onNavigateBack = {
                    showFilterBottomSheet = false
                },
                sizeModel = sizeModel,
                onSizeChange = onSizeChange,
                creatorTypeModel = creatorTypeModel,
                onCreatorChange = onCreatorChange,
                onApplyClick = {
                    onApplyClick()
                    showFilterBottomSheet = false
                }
            )
        }
    }
}

@Preview
@Composable
private fun SearchResultScreen() {
    QuickMemTheme {
        SearchResult(
            isLoading = false,
            tabIndex = 0,
            onTabSelected = {},
            onNavigateBack = {}
        )
    }
}