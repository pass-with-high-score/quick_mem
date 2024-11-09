package com.pwhs.quickmem.presentation.app.classes.add_folder

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pwhs.quickmem.domain.model.folder.GetFolderResponseModel
import com.pwhs.quickmem.presentation.app.classes.add_folder.component.AddFolderToClassList
import com.pwhs.quickmem.presentation.app.classes.add_folder.component.AddFolderToClassTopAppBar
import com.pwhs.quickmem.presentation.component.LoadingOverlay
import com.pwhs.quickmem.ui.theme.QuickMemTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.CreateFolderScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultBackNavigator

@Destination<RootGraph>(
    navArgs = AddFolderToClassArgs::class
)
@Composable
fun AddFolderToClassScreen(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator,
    viewModel: AddFolderToClassViewModel = hiltViewModel(),
    resultNavigator: ResultBackNavigator<Boolean>,
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is AddFolderToClassUIEvent.ShowError -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }

                AddFolderToClassUIEvent.StudySetAddedToClass -> {
                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                    resultNavigator.setResult(true)
                    navigator.navigateUp()
                }
            }
        }
    }

    AddFolderToClass(
        modifier = modifier,
        isLoading = uiState.isLoading,
        folders = uiState.folders,
        listFolderIds = uiState.folderImportedIds,
        userAvatar = uiState.userAvatar,
        username = uiState.username,
        onDoneClick = {
            viewModel.onEvent(AddFolderToClassUIAction.AddFolderToClass)
        },
        onNavigateCancel = {
            navigator.navigateUp()
        },
        onCreateFolderToClassClick = {
            navigator.navigate(
                CreateFolderScreenDestination()
            )
        },
        onAddFolderToClass = {
            viewModel.onEvent(AddFolderToClassUIAction.ToggleFolderImport(it))
        }
    )
}

@Composable
fun AddFolderToClass(
    modifier: Modifier = Modifier,
    onDoneClick: () -> Unit = {},
    onNavigateCancel: () -> Unit = {},
    onCreateFolderToClassClick: () -> Unit = {},
    folders: List<GetFolderResponseModel> = emptyList(),
    listFolderIds: List<String> = emptyList(),
    userAvatar: String = "",
    username: String = "",
    onAddFolderToClass: (String) -> Unit = {},
    isLoading: Boolean = false,
) {
    Scaffold(
        containerColor = colorScheme.background,
        modifier = modifier,
        topBar = {
            AddFolderToClassTopAppBar(
                onDoneClick = onDoneClick,
                onNavigateCancel = onNavigateCancel,
                title = "Add Folder"
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onCreateFolderToClassClick,
                containerColor = colorScheme.secondary,
                contentColor = colorScheme.onSecondary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Create Folder"
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
                AddFolderToClassList(
                    modifier = modifier,
                    folders = folders,
                    listFolderIds = listFolderIds,
                    onAddFolderToClass = onAddFolderToClass,
                    avatarUrl = userAvatar,
                    username = username,
                )
            }
        }
        LoadingOverlay(isLoading = isLoading)
    }
}

@Preview
@Composable
fun AddFolderToClassPreview() {
    QuickMemTheme {
        AddFolderToClass(
            folders = emptyList()
        )
    }
}