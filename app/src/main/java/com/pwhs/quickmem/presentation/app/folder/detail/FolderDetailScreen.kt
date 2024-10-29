package com.pwhs.quickmem.presentation.app.folder.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pwhs.quickmem.presentation.app.folder.detail.component.ButtonStudyFolderDetail
import com.pwhs.quickmem.presentation.app.folder.detail.component.FolderDetailTopAppBar
import com.pwhs.quickmem.presentation.app.folder.detail.component.FolderSortOptionBottomSheet
import com.pwhs.quickmem.presentation.app.folder.detail.component.FolderSortOptionTextButton
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
    onStudyFolderDetailClicked: () -> Unit = {},
    onNavigateBack: () -> Unit = {},
    onAddStudySet: () -> Unit = {}
) {
    var sortOptionBottomSheet by remember { mutableStateOf(false) }
    var currentSortOption by remember { mutableStateOf(SortOptionEnum.RECENT) }
    val sheetSortOptionState = rememberModalBottomSheetState()
    val sheetShowMoreState = rememberModalBottomSheetState()
    Scaffold(
        modifier = modifier,
        topBar = {
            FolderDetailTopAppBar(
                title = "Folder Title",
                dateLabel = "Date Label",
                onNavigateBack = onNavigateBack,
                onMoreClicked = {  },
                onAddStudySet = onAddStudySet,
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier.padding(innerPadding)
                .padding(horizontal = 16.dp)
                .padding(top = 24.dp)
        ) {
            ButtonStudyFolderDetail(
                title = "Study",
                onStudyFolderDetailClicked = onStudyFolderDetailClicked
            )
            FolderSortOptionTextButton(
                currentSortOption = currentSortOption,
                onSortOptionClicked = { sortOptionBottomSheet = true }
            )
        }
    }
    FolderSortOptionBottomSheet(
        onSortOptionClicked = {
            currentSortOption = it
            sortOptionBottomSheet = false },
        sortOptionBottomSheet = sortOptionBottomSheet,
        sheetSortOptionState = sheetSortOptionState,
        onDismissRequest = { sortOptionBottomSheet = false }
    )
}

@Preview
@Composable
private fun FolderDetailPreview() {
    MaterialTheme {
        FolderDetail()
    }
}