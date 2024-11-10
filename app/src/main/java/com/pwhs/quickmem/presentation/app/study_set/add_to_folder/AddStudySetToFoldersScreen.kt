package com.pwhs.quickmem.presentation.app.study_set.add_to_folder

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pwhs.quickmem.domain.model.folder.GetFolderResponseModel
import com.pwhs.quickmem.presentation.app.study_set.add_to_folder.component.AddStudySetToFoldersList
import com.pwhs.quickmem.presentation.app.study_set.add_to_folder.component.AddStudySetToFoldersTopAppBar
import com.pwhs.quickmem.presentation.component.LoadingOverlay
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.CreateFolderScreenDestination
import com.ramcosta.composedestinations.generated.destinations.CreateFolderScreenDestination.invoke
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultBackNavigator

@Destination<RootGraph>(
    navArgs = AddStudySetToFoldersArgs::class
)
@Composable
fun AddStudySetToFoldersScreen(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator,
    viewModel: AddStudySetToFoldersViewModel = hiltViewModel(),
    resultNavigator: ResultBackNavigator<Boolean>,

    ) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is AddStudySetToFoldersUiEvent.Error -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }

                is AddStudySetToFoldersUiEvent.StudySetAddedToFolders -> {
                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                    resultNavigator.setResult(true)
                    navigator.navigateUp()
                }
            }
        }
    }
    AddStudySetToFolders(
        modifier = modifier,
        isLoading = uiState.isLoading,
        folders = uiState.folders,
        folderImportedIds = uiState.folderImportedIds,
        userAvatar = uiState.userAvatar,
        username = uiState.username,
        onDoneClick = {
            viewModel.onEvent(AddStudySetToFoldersUiAction.AddStudySetToFolders)
        },
        onNavigateCancel = {
            navigator.navigateUp()
        },
        onCreateFolderClick = {
            navigator.navigate(
                CreateFolderScreenDestination()
            )
        },
        onAddStudySetToFolders = {
            viewModel.onEvent(AddStudySetToFoldersUiAction.ToggleStudySetImport(it))
        }
    )
}

@Composable
fun AddStudySetToFolders(
    modifier: Modifier = Modifier,
    onDoneClick: () -> Unit = {},
    onNavigateCancel: () -> Unit = {},
    onCreateFolderClick: () -> Unit = {},
    folders: List<GetFolderResponseModel> = emptyList(),
    folderImportedIds: List<String> = emptyList(),
    userAvatar: String = "",
    username: String = "",
    onAddStudySetToFolders: (String) -> Unit = {},
    isLoading: Boolean = false,
) {
    Scaffold(
        containerColor = colorScheme.background,
        modifier = modifier,
        topBar = {
            AddStudySetToFoldersTopAppBar(
                onDoneClick = onDoneClick,
                onNavigateCancel = onNavigateCancel,
                title = "Add to folder"
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onCreateFolderClick,
                containerColor = colorScheme.secondary,
                contentColor = colorScheme.onSecondary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Create folder"
                )
            }
        }
    ) { innerPadding ->
        Box {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp)
            ) {
                AddStudySetToFoldersList(
                    modifier = modifier,
                    folders = folders,
                    folderImportedIds = folderImportedIds,
                    onAddStudySetToFolders = onAddStudySetToFolders,
                    avatarUrl = userAvatar,
                    username = username,
                )
            }
            LoadingOverlay(isLoading = isLoading)
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun AddStudySetToFolderPreview() {
    AddStudySetToFolders()
}