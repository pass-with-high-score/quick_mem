package com.pwhs.quickmem.presentation.app.folder.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.pwhs.quickmem.domain.model.study_set.FolderStudySetResponseModel
import com.pwhs.quickmem.presentation.app.folder.detail.component.FolderDetailTopAppBar
import com.pwhs.quickmem.presentation.app.folder.detail.component.FolderMenuBottomSheet
import com.pwhs.quickmem.presentation.app.folder.detail.component.FolderSortOptionBottomSheet
import com.pwhs.quickmem.presentation.app.folder.detail.component.ListStudySetInnerFolder
import com.pwhs.quickmem.presentation.component.LoadingOverlay
import com.pwhs.quickmem.presentation.component.QuickMemAlertDialog
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultBackNavigator
import java.text.SimpleDateFormat
import java.util.Locale

@Destination<RootGraph>(
    navArgs = FolderDetailArgs::class
)
@Composable
fun FolderDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: FolderDetailViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    resultNavigator: ResultBackNavigator<Boolean>
) {
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                FolderDetailUiEvent.StudySetDeleted -> {}
            }
        }
    }

    FolderDetail(
        modifier = modifier,
        title = uiState.title,
        createdAt = uiState.createdAt,
        updatedAt = uiState.updatedAt,
        isLoading = uiState.isLoading,
        onFolderRefresh = { viewModel.onEvent(FolderDetailUiAction.Refresh) },
        studySet = uiState.studySets,
        onStudySetClick = {  },
        onEditFolder = { viewModel.onEvent(FolderDetailUiAction.OnEditFolderClicked) },
        onStudyFolderDetailClicked = {  },
        onNavigateBack = {
            resultNavigator.setResult(true)
            navigator.navigateUp()
        },
        onAddStudySet = {  }
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FolderDetail(
    modifier: Modifier = Modifier,
    title: String = "",
    createdAt: String = "",
    updatedAt: String = "",
    isLoading: Boolean = false,
    onFolderRefresh: () -> Unit = {},
    studySet: List<FolderStudySetResponseModel> = emptyList(),
    onStudySetClick: (String) -> Unit = {},
    onEditFolder: () -> Unit = {},
    onStudyFolderDetailClicked: () -> Unit = {},
    onNavigateBack: () -> Unit = {},
    onAddStudySet: () -> Unit = {}
) {

    val formattedCreatedAt = formatDate(createdAt)
    val formattedUpdatedAt = formatDate(updatedAt)
    val dateLabel = if (createdAt != updatedAt) {
        "Modified $formattedUpdatedAt"
    } else {
        "Created $formattedCreatedAt"
    }

    var refreshState = rememberPullToRefreshState()
    var sortOptionBottomSheet by remember { mutableStateOf(false) }
    var showMoreBottomSheet by remember { mutableStateOf(false) }
    var currentSortOption by remember { mutableStateOf(SortOptionEnum.RECENT) }
    val sheetSortOptionState = rememberModalBottomSheetState()
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
                onStudyFolderDetailClicked = onStudyFolderDetailClicked,
                currentSortOption = currentSortOption,
                onSortOptionClicked = { sortOptionBottomSheet = true }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .background(Color.Transparent)
                .padding(innerPadding)
        ){
            PullToRefreshBox(
                state = refreshState,
                isRefreshing = isLoading,
                onRefresh = onFolderRefresh
            ) {
                Column{
                    ListStudySetInnerFolder (
                        studySet = studySet,
                        onStudySetClick = onStudySetClick
                    )
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
                showMoreBottomSheet = true},
            onConfirm = {
                onEditFolder()
                showDeleteConfirmationDialog = false
            },
            title = "Delete Folder",
            text = "Are you sure you want to delete this folder?",
            confirmButtonTitle = "Delete",
            dismissButtonTitle = "Cancel",
        )
    }
    FolderSortOptionBottomSheet(
        onSortOptionClicked = {
            currentSortOption = it
            sortOptionBottomSheet = false },
        sortOptionBottomSheet = sortOptionBottomSheet,
        sheetSortOptionState = sheetSortOptionState,
        onDismissRequest = { sortOptionBottomSheet = false }
    )
    FolderMenuBottomSheet(
        onEditFolder = onEditFolder,
        onDeleteFolder = {
            showDeleteConfirmationDialog = true
            showMoreBottomSheet = false
        },
        onShareFolder = {},
        onReportFolder = {},
        showMoreBottomSheet = showMoreBottomSheet,
        sheetShowMoreState = sheetShowMoreState,
        onDismissRequest = { showMoreBottomSheet = false }
    )
}

fun formatDate(dateString: String): String {
    if (dateString.isEmpty()) {
        return ""
    }
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val date = inputFormat.parse(dateString)
    return outputFormat.format(date)
}

@Preview
@Composable
private fun FolderDetailPreview() {
    MaterialTheme {
        FolderDetail()
    }
}