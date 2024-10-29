package com.pwhs.quickmem.presentation.app.folder.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.pwhs.quickmem.presentation.app.folder.detail.component.FolderDetailTopAppBar
import com.pwhs.quickmem.presentation.app.folder.detail.component.FolderMenuBottomSheet
import com.pwhs.quickmem.presentation.app.folder.detail.component.FolderSortOptionBottomSheet
import com.pwhs.quickmem.presentation.component.QuickMemAlertDialog
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<RootGraph>(
    navArgs = FolderDetailArgs::class
)
@Composable
fun FolderDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: FolderDetailViewModel = hiltViewModel(),
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

    FolderDetail(modifier = modifier)

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FolderDetail(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    onFolderRefresh: () -> Unit = {},
    username: String = "User",
    userAvatar: String = "",
    folders: List<Any> = emptyList(),
    onEditFolder: () -> Unit = {},
    onStudyFolderDetailClicked: () -> Unit = {},
    onNavigateBack: () -> Unit = {},
    onAddStudySet: () -> Unit = {}
) {
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
                title = "Folder Title",
                dateLabel = "Date Label",
                onNavigateBack = onNavigateBack,
                onMoreClicked = { showMoreBottomSheet = true },
                onAddStudySet = onAddStudySet,
                onStudyFolderDetailClicked = onStudyFolderDetailClicked,
                currentSortOption = currentSortOption,
                onSortOptionClicked = { sortOptionBottomSheet = true }
            )
        }
    ) { innerPadding ->
        PullToRefreshBox(
            modifier = Modifier.fillMaxWidth(),
            state = refreshState,
            isRefreshing = isLoading,
            onRefresh = { onFolderRefresh() }
        ) {
            when {
                folders.isEmpty() -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(innerPadding)
                            .padding(top = 40.dp)
                            .padding(horizontal = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        AsyncImage(
                            model = userAvatar,
                            contentDescription = "User avatar",
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                        Text(
                            text = "Hello, $username",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp
                            )
                        )
                        HorizontalDivider(
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                        )
                        Text(
                            text = "Get started by searching for a study set or creating your own",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            ),
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    }
                }
                else -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(innerPadding)
                    ) {
                        // Folder content
                        Text(text = "Folder content")
                    }
                }
            }
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

@Preview
@Composable
private fun FolderDetailPreview() {
    MaterialTheme {
        FolderDetail()
    }
}