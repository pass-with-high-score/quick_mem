package com.pwhs.quickmem.presentation.app.classes.edit

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
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
import com.pwhs.quickmem.presentation.component.CreateTextField
import com.pwhs.quickmem.presentation.component.CreateTopAppBar
import com.pwhs.quickmem.presentation.component.LoadingOverlay
import com.pwhs.quickmem.presentation.component.SwitchContainer
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.result.ResultBackNavigator

@Destination<RootGraph>(
    navArgs = EditClassScreenArgs::class
)

@Composable
fun EditClassScreen(
    modifier: Modifier = Modifier,
    viewModel: EditClassViewModel = hiltViewModel(),
    resultNavigator: ResultBackNavigator<Boolean>,
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                EditClassUiEvent.ClassesUpdated -> TODO()
                is EditClassUiEvent.ShowError -> {
                    Toast.makeText(context, "Update class error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    EditClass(
        modifier = modifier,
        isLoading = uiState.isLoading,
        title = uiState.title,
        description = uiState.description,
        allowSetManagement = uiState.allowSetManagement,
        allowMemberManagement = uiState.allowMemberManagement,
        onNavigateBack = {
            resultNavigator.navigateBack(false)
        },
        onDoneClick = {
            viewModel.onEvent(EditClassUiAction.SaveClicked)
            resultNavigator.navigateBack(true)
        },
        onAllowMemberManagementChange = {
            viewModel.onEvent(EditClassUiAction.OnAllowMemberChanged(it))
        },
        onAllowSetManagementChange = {
            viewModel.onEvent(EditClassUiAction.OnAllowSetChanged(it))
        },
        onTitleChange = {
            viewModel.onEvent(EditClassUiAction.TitleChanged(it))
        },
        onDescriptionChange = {
            viewModel.onEvent(EditClassUiAction.DescriptionChanged(it))
        }
    )

}

@Composable
fun EditClass(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    onDoneClick: () -> Unit = {},
    onNavigateBack: () -> Unit = {},
    title: String = "",
    titleError: String = "",
    onTitleChange: (String) -> Unit = {},
    description: String = "",
    descriptionError: String = "",
    onDescriptionChange: (String) -> Unit = {},
    allowSetManagement: Boolean = false,
    onAllowSetManagementChange: (Boolean) -> Unit = {},
    allowMemberManagement: Boolean = false,
    onAllowMemberManagementChange: (Boolean) -> Unit = {},
) {
    Scaffold(
        containerColor = colorScheme.background,
        modifier = modifier,
        topBar = {
            CreateTopAppBar(
                onNavigateBack = onNavigateBack,
                onDoneClick = onDoneClick,
                title = "Edit this class"
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
                    title = "Folder Title",
                    valueError = titleError,
                    onValueChange = onTitleChange,
                    placeholder = "Enter Folder Title"
                )
                CreateTextField(
                    value = description,
                    title = "Description (Optional)",
                    valueError = descriptionError,
                    onValueChange = onDescriptionChange,
                    placeholder = "Enter Description"
                )
                SwitchContainer(
                    text = "Allow class members to send invites to other people",
                    checked = allowMemberManagement,
                    onCheckedChange = onAllowMemberManagementChange
                )
                SwitchContainer(
                    text = "Allow class members to add study set and folders",
                    checked = allowSetManagement,
                    onCheckedChange = onAllowSetManagementChange
                )
            }

            LoadingOverlay(
                isLoading = isLoading
            )
        }
    }
}

@Preview
@Composable
private fun EditClassPreview() {
    MaterialTheme {
        EditClass()
    }

}