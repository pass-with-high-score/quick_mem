package com.pwhs.quickmem.presentation.app.library

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pwhs.quickmem.domain.model.classes.GetClassByOwnerResponseModel
import com.pwhs.quickmem.domain.model.folder.GetFolderResponseModel
import com.pwhs.quickmem.domain.model.study_set.GetStudySetResponseModel
import com.pwhs.quickmem.presentation.app.library.classes.ListClassesScreen
import com.pwhs.quickmem.presentation.app.library.folder.ListFolderScreen
import com.pwhs.quickmem.presentation.app.library.study_set.ListStudySetScreen
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.CreateClassScreenDestination
import com.ramcosta.composedestinations.generated.destinations.CreateFolderScreenDestination
import com.ramcosta.composedestinations.generated.destinations.CreateStudySetScreenDestination
import com.ramcosta.composedestinations.generated.destinations.FolderDetailScreenDestination
import com.ramcosta.composedestinations.generated.destinations.StudySetDetailScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import timber.log.Timber

@Composable
@Destination<RootGraph>
fun LibraryScreen(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator,
    viewModel: LibraryViewModel = hiltViewModel(),
    resultStudySetDetail: ResultRecipient<StudySetDetailScreenDestination, Boolean>,
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    resultStudySetDetail.onNavResult { result ->
        when (result) {
            NavResult.Canceled -> {
                Timber.d("StudySetDetailScreen was canceled")
            }

            is NavResult.Value -> {
                viewModel.onEvent(LibraryUiAction.Refresh)
            }
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is LibraryUiEvent.Error -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Library(
        modifier = modifier,
        isLoading = uiState.isLoading,
        studySets = uiState.studySets,
        classes = uiState.classes,
        folders = uiState.folders,
        avatarUrl = uiState.userAvatar,
        username = uiState.username,
        onStudySetRefresh = {
            viewModel.onEvent(LibraryUiAction.RefreshStudySets)
        },
        onClassRefresh = {
            viewModel.onEvent(LibraryUiAction.RefreshClasses)
        },
        onFolderRefresh = {
            viewModel.onEvent(LibraryUiAction.RefreshFolders)
        },
        onStudySetClick = {
            navigator.navigate(StudySetDetailScreenDestination(id = it))
        },
        onClassClick = {
            navigator.navigate(CreateClassScreenDestination)
        },
        onFolderClick = {
            navigator.navigate(FolderDetailScreenDestination(id = it))
        },
        navigateToCreateStudySet = {
            navigator.navigate(CreateStudySetScreenDestination)
        },
        navigateToCreateClass = {
            navigator.navigate(CreateClassScreenDestination)
        },
        navigateToCreateFolder = {
            navigator.navigate(CreateFolderScreenDestination)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Library(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    avatarUrl: String = "",
    username: String = "",
    onStudySetRefresh: () -> Unit = {},
    onClassRefresh: () -> Unit = {},
    onFolderRefresh: () -> Unit = {},
    studySets: List<GetStudySetResponseModel> = emptyList(),
    classes: List<GetClassByOwnerResponseModel> = emptyList(),
    folders: List<GetFolderResponseModel> = emptyList(),
    onStudySetClick: (String) -> Unit = {},
    onClassClick: (String) -> Unit = {},
    onFolderClick: (String) -> Unit = {},
    navigateToCreateStudySet: () -> Unit = {},
    navigateToCreateClass: () -> Unit = {},
    navigateToCreateFolder: () -> Unit = {},
) {
    var tabIndex by remember { mutableIntStateOf(0) }
    val tabTitles = listOf("Study sets", "Classes", "Folders")
    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Library",
                        style = typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            when (tabIndex) {
                                LibraryTabEnum.STUDY_SET.index -> {
                                    navigateToCreateStudySet()
                                }

                                LibraryTabEnum.CLASS.index -> {
                                    navigateToCreateClass()
                                }

                                LibraryTabEnum.FOLDER.index -> {
                                    navigateToCreateFolder()
                                }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add study set",
                            modifier = Modifier.size(24.dp)
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
                        icon = {
                            when (index) {
                                LibraryTabEnum.STUDY_SET.index -> {}
                                LibraryTabEnum.CLASS.index -> {}
                                LibraryTabEnum.FOLDER.index -> {}
                            }
                        }
                    )
                }
            }
            when (tabIndex) {
                LibraryTabEnum.STUDY_SET.index -> ListStudySetScreen(
                    modifier = Modifier.padding(top = 8.dp),
                    isLoading = isLoading,
                    studySets = studySets,
                    avatarUrl = avatarUrl,
                    username = username,
                    onStudySetClick = onStudySetClick,
                    onStudySetRefresh = onStudySetRefresh
                )

                LibraryTabEnum.CLASS.index -> ListClassesScreen(
                    modifier = modifier,
                    isLoading = isLoading,
                    classes = classes,
                    onClassClicked = onClassClick,
                    onClassRefresh = onClassRefresh,
                )

                LibraryTabEnum.FOLDER.index -> ListFolderScreen(
                    modifier = modifier,
                    isLoading = isLoading,
                    folders = folders,
                    onFolderClick = onFolderClick,
                    onAddFolderClick = navigateToCreateFolder,
                    onFolderRefresh = onFolderRefresh
                )
            }
        }
    }
}