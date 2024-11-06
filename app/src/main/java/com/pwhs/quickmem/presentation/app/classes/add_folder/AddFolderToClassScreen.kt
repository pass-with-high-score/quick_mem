package com.pwhs.quickmem.presentation.app.classes.add_folder

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pwhs.quickmem.domain.model.folder.GetFolderResponseModel
import com.pwhs.quickmem.presentation.app.library.folder.component.FolderItem
import com.pwhs.quickmem.presentation.component.CreateTopAppBar
import com.pwhs.quickmem.presentation.component.LoadingOverlay
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<RootGraph>
@Composable
fun AddFolderToClassScreen(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator,
    viewModel: AddFolderToClassViewModel = hiltViewModel(),
) {
    val folders by viewModel.folders.collectAsState()

    AddFolderUI(
        modifier = Modifier,
        onNavigateBack = {
            navigator.navigateUp()
        },
        folders = folders
    )
}

@Composable
fun AddFolderUI(
    modifier: Modifier = Modifier,
    onDoneClick: () -> Unit = {},
    onNavigateBack: () -> Unit = {},
    folders: List<GetFolderResponseModel>,
    isLoading: Boolean = false,
    onSelectedFolder: (String) -> Unit = {}
) {
    Scaffold(
        containerColor = colorScheme.background,
        modifier = modifier,
        topBar = {
            CreateTopAppBar(
                onDoneClick = onDoneClick,
                onNavigateBack = onNavigateBack,
                title = "Add folder"
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding)
        ) {
            items(folders) { folder ->
                FolderItem(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    title = folder.title,
                    numOfStudySets = folder.studySetCount,
                    onClick = { onSelectedFolder(folder.id) },
                    userResponseModel = folder.owner
                )
            }
        }
        LoadingOverlay(
            isLoading = isLoading
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddFolderUIPreview() {

}