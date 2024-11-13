package com.pwhs.quickmem.presentation.app.classes.detail

import android.content.Intent
import android.widget.Toast
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
import com.pwhs.quickmem.core.utils.AppConstant
import com.pwhs.quickmem.domain.model.folder.GetFolderResponseModel
import com.pwhs.quickmem.domain.model.study_set.GetStudySetResponseModel
import com.pwhs.quickmem.domain.model.users.ClassMemberModel
import com.pwhs.quickmem.domain.model.users.UserResponseModel
import com.pwhs.quickmem.presentation.app.classes.detail.component.ClassDetailBottomSheet
import com.pwhs.quickmem.presentation.app.classes.detail.component.ClassDetailTopAppBar
import com.pwhs.quickmem.presentation.app.classes.detail.folders.FoldersTabScreen
import com.pwhs.quickmem.presentation.app.classes.detail.members.MembersTabScreen
import com.pwhs.quickmem.presentation.app.classes.detail.study_sets.StudySetsTabScreen
import com.pwhs.quickmem.presentation.component.LoadingOverlay
import com.pwhs.quickmem.presentation.component.QuickMemAlertDialog
import com.pwhs.quickmem.ui.theme.QuickMemTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.AddFolderToClassScreenDestination
import com.ramcosta.composedestinations.generated.destinations.AddStudySetToClassScreenDestination
import com.ramcosta.composedestinations.generated.destinations.EditClassScreenDestination
import com.ramcosta.composedestinations.generated.destinations.FolderDetailScreenDestination
import com.ramcosta.composedestinations.generated.destinations.StudySetDetailScreenDestination
import com.ramcosta.composedestinations.generated.destinations.UserDetailScreenDestination
import com.ramcosta.composedestinations.generated.destinations.WelcomeScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.ramcosta.composedestinations.result.ResultRecipient
import timber.log.Timber

@Composable
@Destination<RootGraph>(
    navArgs = ClassDetailArgs::class
)
fun ClassDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: ClassDetailViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    resultBackNavigator: ResultRecipient<EditClassScreenDestination, Boolean>,
    resultNavigator: ResultBackNavigator<Boolean>,
    resultStudySetDetail: ResultRecipient<StudySetDetailScreenDestination, Boolean>,
    resultFolderDetail: ResultRecipient<FolderDetailScreenDestination, Boolean>,
    resultAddStudySetToClass: ResultRecipient<AddStudySetToClassScreenDestination, Boolean>,
    resultAddFolderToClass: ResultRecipient<AddFolderToClassScreenDestination, Boolean>,
) {

    resultAddFolderToClass.onNavResult { result ->
        when (result) {
            NavResult.Canceled -> {
                Timber.d("AddFolderToClassScreen was canceled")
            }

            is NavResult.Value -> {
                if (result.value) {
                    viewModel.onEvent(ClassDetailUiAction.Refresh)
                }
            }
        }
    }

    resultAddStudySetToClass.onNavResult { result ->
        when (result) {
            NavResult.Canceled -> {
                Timber.d("AddStudySetToClassScreen was canceled")
            }

            is NavResult.Value -> {
                if (result.value) {
                    viewModel.onEvent(ClassDetailUiAction.Refresh)
                }
            }
        }
    }

    resultBackNavigator.onNavResult { result ->
        when (result) {
            NavResult.Canceled -> {
                // Do nothing
            }

            is NavResult.Value -> {
                if (result.value) {
                    viewModel.onEvent(ClassDetailUiAction.Refresh)
                }
            }
        }
    }
    resultStudySetDetail.onNavResult { result ->
        when (result) {
            NavResult.Canceled -> {
                Timber.d("StudySetDetailScreen was canceled")
            }

            is NavResult.Value -> {
                if (result.value) {
                    viewModel.onEvent(ClassDetailUiAction.Refresh)
                }
            }
        }
    }


    resultFolderDetail.onNavResult { result ->
        when (result) {
            NavResult.Canceled -> {
                Timber.d("FolderDetailScreen was canceled")
            }

            is NavResult.Value -> {
                if (result.value) {
                    viewModel.onEvent(ClassDetailUiAction.Refresh)
                }
            }
        }
    }

    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                ClassDetailUiEvent.NavigateToWelcome -> {
                    navigator.navigate(WelcomeScreenDestination) {
                        popUpTo(WelcomeScreenDestination) {
                            inclusive = true
                        }
                    }
                }

                ClassDetailUiEvent.OnJoinClass -> {

                }

                is ClassDetailUiEvent.ShowError -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }

                ClassDetailUiEvent.ClassDeleted -> {
                    Toast.makeText(context,
                        context.getString(R.string.txt_class_deleted), Toast.LENGTH_SHORT).show()
                    navigator.navigateUp()
                }

                ClassDetailUiEvent.NavigateToEditClass -> {
                    navigator.navigate(
                        EditClassScreenDestination(
                            classId = uiState.id,
                            classTitle = uiState.title,
                            classDescription = uiState.description,
                            isMemberAllowed = uiState.allowSet,
                            isSetAllowed = uiState.allowMember
                        )
                    )
                }

                ClassDetailUiEvent.OnNavigateToAddFolder -> {
                    navigator.navigate(AddFolderToClassScreenDestination(classId = uiState.id))
                }

                ClassDetailUiEvent.OnNavigateToAddStudySets -> {
                    navigator.navigate(AddStudySetToClassScreenDestination(classId = uiState.id))
                }
            }
        }
    }

    ClassDetail(
        modifier = modifier,
        linkShareCode = uiState.joinClassCode,
        onRefresh = {
            viewModel.onEvent(ClassDetailUiAction.Refresh)
        },
        onNavigateBack = {
            resultNavigator.navigateBack(true)
        },
        title = uiState.title,
        isLoading = uiState.isLoading,
        userResponseModel = uiState.userResponseModel,
        studySets = uiState.studySets,
        folders = uiState.folders,
        members = uiState.members,
        onEditClass = {
            viewModel.onEvent(ClassDetailUiAction.EditClass)
        },
        onDeleteClass = {
            viewModel.onEvent(ClassDetailUiAction.DeleteClass)
        },
        onNavigateAddStudySets = {
            viewModel.onEvent(ClassDetailUiAction.OnNavigateToAddStudySets)
        },
        onNavigateAddFolder = {
            viewModel.onEvent(ClassDetailUiAction.OnNavigateToAddFolder)
        },
        onNavigateToUserDetail = {
            Timber.d("Navigate to user detail with id: $it")
            Timber.d("User id: ${uiState.userResponseModel.id}")
            navigator.navigate(
                UserDetailScreenDestination(
                    userId = it,
                    isOwner = uiState.userResponseModel.id == it
                )
            )
        },
        onStudySetItemClicked = {
            navigator.navigate(
                StudySetDetailScreenDestination(
                    id = it.id,
                    code = ""
                )
            )
        },
        onFolderItemClicked = {
            navigator.navigate(
                FolderDetailScreenDestination(
                    id = it.id,
                    code = ""
                )
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassDetail(
    modifier: Modifier = Modifier,
    title: String = "",
    isLoading: Boolean = false,
    linkShareCode: String = "",
    userResponseModel: UserResponseModel = UserResponseModel(),
    studySets: List<GetStudySetResponseModel> = emptyList(),
    folders: List<GetFolderResponseModel> = emptyList(),
    members: List<ClassMemberModel> = emptyList(),
    onNavigateBack: () -> Unit = {},
    onNavigateAddFolder: () -> Unit = {},
    onNavigateAddStudySets: () -> Unit = {},
    onNavigateToUserDetail: (String) -> Unit = {},
    onEditClass: () -> Unit = {},
    onDeleteClass: () -> Unit = {},
    onRefresh: () -> Unit = {},
    onStudySetItemClicked: (GetStudySetResponseModel) -> Unit = {},
    onFolderItemClicked: (GetFolderResponseModel) -> Unit = {},
) {
    var tabIndex by remember { mutableIntStateOf(0) }
    val tabTitles = listOf(stringResource(R.string.txt_study_sets), stringResource(R.string.txt_folders),
        stringResource(
            R.string.txt_members
        )
    )

    val refreshState = rememberPullToRefreshState()
    var showMoreBottomSheet by remember { mutableStateOf(false) }
    val sheetShowMoreState = rememberModalBottomSheetState()
    var showDeleteConfirmationDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            ClassDetailTopAppBar(
                title = title,
                onNavigateBack = onNavigateBack,
                onMoreClicked = { showMoreBottomSheet = true },
                modifier = modifier,
                studySetCount = studySets.size,
                userResponse = userResponseModel,
                onNavigateToUserDetail = {
                    onNavigateToUserDetail(it)
                },
                onShareClicked = {
                    val link = AppConstant.BASE_URL + context.getString(R.string.txt_class_join) + linkShareCode
                    val text =
                        context.getString(R.string.txt_join_class_by_link_with_me, title, link)
                    val sendIntent = Intent(Intent.ACTION_SEND).apply {
                        putExtra(Intent.EXTRA_TEXT, text)
                        type = context.getString(R.string.txt_text_plain)
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
                        ClassDetailEnums.STUDY_SETS.index -> StudySetsTabScreen(
                            onAddStudySetClicked = onNavigateAddStudySets,
                            studySets = studySets,
                            onStudySetItemClicked = onStudySetItemClicked
                        )

                        ClassDetailEnums.FOLDERS.index -> FoldersTabScreen(
                            onAddFoldersClicked = onNavigateAddFolder,
                            folder = folders,
                            onFolderItemClicked = onFolderItemClicked
                        )

                        ClassDetailEnums.MEMBERS.index -> MembersTabScreen(
                            member = members,
                            onMembersItemClicked = {
                                onNavigateToUserDetail(it.id)
                            }
                        )
                    }

                }
            }

            LoadingOverlay(
                isLoading = isLoading
            )
        }
    }

    if (showDeleteConfirmationDialog) {
        QuickMemAlertDialog(
            onDismissRequest = {
                showDeleteConfirmationDialog = false
                showMoreBottomSheet = true
            },
            onConfirm = {
                onDeleteClass()
                showDeleteConfirmationDialog = false
            },
            title = stringResource(R.string.txt_delete_class),
            text = stringResource(R.string.txt_are_you_sure_you_want_to_delete_this_class),
            confirmButtonTitle = stringResource(R.string.txt_delete),
            dismissButtonTitle = stringResource(R.string.txt_cancel),
        )
    }
    ClassDetailBottomSheet(
        onAddStudySetToClass = onNavigateAddStudySets,
        onAddFolderToClass = onNavigateAddFolder,
        onEditClass = onEditClass,
        onDeleteClass = {
            showDeleteConfirmationDialog = true
            showMoreBottomSheet = false
        },
        onShareClass = {},
        onReportClass = {},
        showMoreBottomSheet = showMoreBottomSheet,
        sheetShowMoreState = sheetShowMoreState,
        onDismissRequest = { showMoreBottomSheet = false }
    )
}

@Preview
@Composable
private fun ClassDetailScreenPreview() {
    QuickMemTheme {
        ClassDetail(
            title = stringResource(R.string.txt_class_title),
        )
    }
}