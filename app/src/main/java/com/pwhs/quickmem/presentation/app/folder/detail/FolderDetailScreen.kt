package com.pwhs.quickmem.presentation.app.folder.detail

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pwhs.quickmem.R
import com.pwhs.quickmem.core.utils.AppConstant
import com.pwhs.quickmem.domain.model.study_set.GetStudySetResponseModel
import com.pwhs.quickmem.presentation.app.folder.detail.component.FolderDetailStudySetList
import com.pwhs.quickmem.presentation.app.folder.detail.component.FolderDetailTopAppBar
import com.pwhs.quickmem.presentation.app.folder.detail.component.FolderMenuBottomSheet
import com.pwhs.quickmem.presentation.component.LoadingOverlay
import com.pwhs.quickmem.presentation.component.QuickMemAlertDialog
import com.pwhs.quickmem.ui.theme.QuickMemTheme
import com.pwhs.quickmem.util.formatDate
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.AddStudySetToFolderScreenDestination
import com.ramcosta.composedestinations.generated.destinations.EditFolderScreenDestination
import com.ramcosta.composedestinations.generated.destinations.StudySetDetailScreenDestination
import com.ramcosta.composedestinations.generated.destinations.UserDetailScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.ramcosta.composedestinations.result.ResultRecipient
import timber.log.Timber

@Destination<RootGraph>(
    navArgs = FolderDetailArgs::class
)
@Composable
fun FolderDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: FolderDetailViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    resultNavigator: ResultBackNavigator<Boolean>,
    resultEditFolder: ResultRecipient<EditFolderScreenDestination, Boolean>,
    resultStudySetDetail: ResultRecipient<StudySetDetailScreenDestination, Boolean>,
    resultAddStudySet: ResultRecipient<AddStudySetToFolderScreenDestination, Boolean>
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    resultAddStudySet.onNavResult { result ->
        when (result) {
            is NavResult.Canceled -> {}
            is NavResult.Value -> {
                if (result.value) {
                    viewModel.onEvent(FolderDetailUiAction.Refresh)
                }
            }
        }
    }

    resultEditFolder.onNavResult { result ->
        when (result) {
            is NavResult.Canceled -> {}
            is NavResult.Value -> {
                if (result.value) {
                    viewModel.onEvent(FolderDetailUiAction.Refresh)
                }
            }
        }
    }

    resultStudySetDetail.onNavResult { result ->
        when (result) {
            is NavResult.Canceled -> {}
            is NavResult.Value -> {
                if (result.value) {
                    viewModel.onEvent(FolderDetailUiAction.Refresh)
                }
            }
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                FolderDetailUiEvent.NavigateToEditFolder -> {
                    Timber.d(uiState.id)
                    navigator.navigate(
                        EditFolderScreenDestination(
                            folderId = uiState.id,
                            folderTitle = uiState.title,
                            folderDescription = uiState.description,
                            folderIsPublic = uiState.isPublic
                        )
                    )
                }

                is FolderDetailUiEvent.ShowError -> {
                    Timber.d("ShowError: ${event.message}")
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }

                FolderDetailUiEvent.FolderDeleted -> {
                    resultNavigator.navigateBack(true)
                }
            }
        }
    }

    FolderDetail(
        modifier = modifier,
        title = uiState.title,
        isOwner = uiState.isOwner,
        createdAt = uiState.createdAt,
        updatedAt = uiState.updatedAt,
        isLoading = uiState.isLoading,
        linkShareCode = uiState.linkShareCode,
        onFolderRefresh = { viewModel.onEvent(FolderDetailUiAction.Refresh) },
        userAvatar = uiState.user.avatarUrl,
        studySets = uiState.studySets,
        onStudySetClick = {
            navigator.navigate(
                StudySetDetailScreenDestination(
                    id = it,
                )
            )
        },
        onEditFolder = { viewModel.onEvent(FolderDetailUiAction.EditFolder) },
        onStudyFolderClick = { viewModel.onEvent(FolderDetailUiAction.Refresh) },
        onNavigateBack = {
            resultNavigator.navigateBack(true)
        },
        onAddStudySet = {
            navigator.navigate(
                AddStudySetToFolderScreenDestination(
                    folderId = uiState.id
                )
            )
        },
        onDeleteFolder = { viewModel.onEvent(FolderDetailUiAction.DeleteFolder) },
        onNavigateToUserDetail = {
            navigator.navigate(
                UserDetailScreenDestination(
                    userId = uiState.user.id
                )
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FolderDetail(
    modifier: Modifier = Modifier,
    title: String = "",
    isOwner: Boolean,
    linkShareCode: String = "",
    createdAt: String = "",
    updatedAt: String = "",
    isLoading: Boolean = false,
    onFolderRefresh: () -> Unit = {},
    studySets: List<GetStudySetResponseModel> = emptyList(),
    userAvatar: String = "",
    onStudySetClick: (String) -> Unit = {},
    onEditFolder: () -> Unit = {},
    onDeleteFolder: () -> Unit = {},
    onStudyFolderClick: () -> Unit = {},
    onNavigateBack: () -> Unit = {},
    onAddStudySet: () -> Unit = {},
    onNavigateToUserDetail: () -> Unit = {}
) {
    val context = LocalContext.current
    val formattedCreatedAt = formatDate(createdAt)
    val formattedUpdatedAt = formatDate(updatedAt)
    val dateLabel = if (createdAt != updatedAt) {
        "Modified $formattedUpdatedAt"
    } else {
        "Created $formattedCreatedAt"
    }

    val refreshState = rememberPullToRefreshState()
    var showMoreBottomSheet by remember { mutableStateOf(false) }
    val sheetShowMoreState = rememberModalBottomSheetState()
    var showDeleteConfirmationDialog by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier,
        topBar = {
            FolderDetailTopAppBar(
                title = title,
                dateLabel = dateLabel,
                onNavigateBack = onNavigateBack,
                onMoreClicked = { showMoreBottomSheet = true },
                onAddStudySet = onAddStudySet,
                avatarUrl = userAvatar,
                onNavigateToUserDetail = onNavigateToUserDetail,
                isOwner = isOwner
            )
        },
        floatingActionButton = {
            if (isOwner) {
                FloatingActionButton(
                    onClick = onStudyFolderClick,
                    containerColor = colorScheme.secondary,
                    contentColor = colorScheme.onSecondary
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = stringResource(R.string.txt_study)
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            PullToRefreshBox(
                state = refreshState,
                isRefreshing = isLoading,
                onRefresh = onFolderRefresh
            ) {
                FolderDetailStudySetList(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    studySets = studySets,
                    onStudySetClick = onStudySetClick,
                    onAddFlashCardClick = onAddStudySet,
                    isOwner = isOwner
                )
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
                onDeleteFolder()
                showDeleteConfirmationDialog = false
            },
            title = stringResource(R.string.txt_delete_folder),
            text = stringResource(R.string.txt_are_you_sure_you_want_to_delete_this_folder),
            confirmButtonTitle = stringResource(R.string.txt_delete),
            dismissButtonTitle = stringResource(R.string.txt_cancel),
        )
    }
    FolderMenuBottomSheet(
        onEditFolder = onEditFolder,
        onDeleteFolder = {
            showDeleteConfirmationDialog = true
            showMoreBottomSheet = false
        },
        onShareFolder = {
            val link = AppConstant.BASE_URL + "folder/share/" + linkShareCode
            val text = context.getString(R.string.txt_check_out_this_folder, title, link)
            val sendIntent = Intent(Intent.ACTION_SEND).apply {
                putExtra(Intent.EXTRA_TEXT, text)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            context.startActivity(shareIntent)
        },
        showMoreBottomSheet = showMoreBottomSheet,
        sheetShowMoreState = sheetShowMoreState,
        onDismissRequest = { showMoreBottomSheet = false },
        isOwner = isOwner
    )
}

@Preview(showBackground = true)
@Composable
private fun FolderDetailPreview() {
    QuickMemTheme {
        FolderDetail(
            title = "Folder Title",
            isOwner = true
        )
    }
}