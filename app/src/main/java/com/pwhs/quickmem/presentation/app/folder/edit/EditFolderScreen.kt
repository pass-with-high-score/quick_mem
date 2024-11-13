package com.pwhs.quickmem.presentation.app.folder.edit

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pwhs.quickmem.R
import com.pwhs.quickmem.presentation.component.CreateTextField
import com.pwhs.quickmem.presentation.component.CreateTopAppBar
import com.pwhs.quickmem.presentation.component.LoadingOverlay
import com.pwhs.quickmem.presentation.component.SwitchContainer
import com.pwhs.quickmem.ui.theme.QuickMemTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.result.ResultBackNavigator


@Destination<RootGraph>(
    navArgs = EditFolderScreenArgs::class
)
@Composable
fun EditFolderScreen(
    modifier: Modifier = Modifier,
    viewModel: EditFolderViewModel = hiltViewModel(),
    resultNavigator: ResultBackNavigator<Boolean>
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is EditFolderUiEvent.FolderEdited -> {
                    Toast.makeText(context,
                        context.getString(R.string.txt_folder_edited), Toast.LENGTH_SHORT).show()
                    resultNavigator.navigateBack(true)
                }

                is EditFolderUiEvent.ShowError -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    EditFolder(
        modifier = modifier,
        isLoading = uiState.isLoading,
        title = uiState.title,
        titleError = uiState.titleError,
        onTitleChange = { viewModel.onEvent(EditFolderUiAction.TitleChanged(it)) },
        description = uiState.description,
        onDescriptionChange = { viewModel.onEvent(EditFolderUiAction.DescriptionChanged(it)) },
        isPublic = uiState.isPublic,
        onIsPublicChange = { viewModel.onEvent(EditFolderUiAction.IsPublicChanged(it)) },
        onDoneClick = {
            viewModel.onEvent(EditFolderUiAction.SaveClicked)
        },
        onNavigateBack = {
            resultNavigator.navigateBack(false)
        }
    )

}

@Composable
fun EditFolder(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
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
            CreateTopAppBar(
                onNavigateBack = onNavigateBack,
                onDoneClick = onDoneClick,
                title = stringResource(R.string.txt_edit_folder)
            )
        }
    ) { innerPadding ->
        Box {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp)
            ) {
                CreateTextField(
                    value = title,
                    title = stringResource(R.string.txt_folder_title),
                    valueError = titleError,
                    onValueChange = onTitleChange,
                    placeholder = stringResource(R.string.txt_enter_folder_title)
                )
                CreateTextField(
                    value = description,
                    title = stringResource(R.string.txt_description_optional),
                    valueError = descriptionError,
                    onValueChange = onDescriptionChange,
                    placeholder = stringResource(R.string.txt_enter_description)
                )
                SwitchContainer(
                    text = stringResource(R.string.txt_when_you_make_a_folder_public_anyone_can_see_it_and_use_it),
                    checked = isPublic,
                    onCheckedChange = onIsPublicChange
                )
            }

            LoadingOverlay(
                isLoading = isLoading
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
    QuickMemTheme {
        EditFolder()
    }
}