package com.pwhs.quickmem.presentation.app.folder.create

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pwhs.quickmem.presentation.app.folder.component.FolderAppBar
import com.pwhs.quickmem.presentation.app.folder.component.FolderPublicSwitch
import com.pwhs.quickmem.presentation.app.folder.component.FolderTextField
import com.pwhs.quickmem.util.loadingOverlay
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination<RootGraph>
fun CreateFolderScreen(
    modifier: Modifier = Modifier,
    viewModel: CreateFolderViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    var showLoading by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                CreateFolderUiEvent.None -> {
                    showLoading = false
                }
                CreateFolderUiEvent.ShowLoading -> {
                    showLoading = true
                }
                is CreateFolderUiEvent.ShowError -> {
                    showLoading = false
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
                is CreateFolderUiEvent.FolderCreated -> {
                    showLoading = false
                    Toast.makeText(context, "Folder Created", Toast.LENGTH_SHORT).show()
                    navigator.navigateUp()
                    // go to folder detail
                }
            }
        }
    }

    CreateFolder(
        modifier = modifier.loadingOverlay(showLoading),
        title = uiState.title,
        titleError = uiState.titleError,
        onTitleChange = { viewModel.onEvent(CreateFolderUiAction.TitleChanged(it)) },
        description = uiState.description,
        onDescriptionChange = { viewModel.onEvent(CreateFolderUiAction.DescriptionChanged(it)) },
        isPublic = uiState.isPublic,
        onIsPublicChange = { viewModel.onEvent(CreateFolderUiAction.PublicChanged(it)) },
        onDoneClick = { viewModel.onEvent(CreateFolderUiAction.SaveClicked) },
        onNavigateBack = { navigator.navigateUp() }
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateFolder(
    modifier: Modifier = Modifier,
    title: String = "",
    titleError: String = "",
    onTitleChange: (String) -> Unit = {},
    description: String = "",
    descriptionError: String = "",
    onDescriptionChange: (String) -> Unit = {},
    isPublic: Boolean = false,
    onIsPublicChange: (Boolean) -> Unit = {},
    onDoneClick: () -> Unit = {},
    onNavigateBack: () -> Unit = {}
) {
    Scaffold(
        containerColor = colorScheme.background,
        modifier = modifier,
        topBar = {
            FolderAppBar(
                onNavigateBack = onNavigateBack,
                onDoneClick = onDoneClick,
                title = "New Folder"
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            FolderTextField(
                value = title,
                title = "Folder Title",
                valueError = titleError,
                onValueChange = onTitleChange,
                placeholder = "Enter Folder Title"
            )
            FolderTextField(
                value = description,
                title = "Description (Optional)",
                valueError = descriptionError,
                onValueChange = onDescriptionChange,
                placeholder = "Enter Description"
            )
            FolderPublicSwitch(
                isPublic = isPublic,
                onIsPublicChange = onIsPublicChange
            )
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun CreateFolderScreenPreview() {
    CreateFolder()
}