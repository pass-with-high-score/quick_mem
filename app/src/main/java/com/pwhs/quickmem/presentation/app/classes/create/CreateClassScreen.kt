package com.pwhs.quickmem.presentation.app.classes.create

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pwhs.quickmem.presentation.app.classes.component.ClassesTextField
import com.pwhs.quickmem.presentation.app.classes.component.ClassesTopBar
import com.pwhs.quickmem.presentation.app.classes.component.MemberManagementSwitch
import com.pwhs.quickmem.presentation.app.folder.component.FolderPublicSwitch
import com.pwhs.quickmem.presentation.app.folder.component.FolderTextField
import com.pwhs.quickmem.presentation.app.folder.create.CreateFolderUiAction
import com.pwhs.quickmem.presentation.component.LoadingOverlay
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination<RootGraph>
fun CreateClassScreen(
    modifier: Modifier = Modifier,
    viewModel: CreateClassViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is CreateClassUiEvent.ClassesCreated -> {
                    Toast.makeText(context, "Class Created", Toast.LENGTH_SHORT).show()
                    navigator.navigateUp()
                }

                is CreateClassUiEvent.ShowError -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    CreateClass(
        modifier = modifier,
        title = uiState.title,
        isLoading = uiState.isLoading,
        titleError = uiState.titleError,
        onTitleChange = { viewModel.onEvent(CreateClassUiAction.TitleChanged(it)) },
        description = uiState.description,
        onDescriptionChange = { viewModel.onEvent(CreateClassUiAction.DescriptionChanged(it)) },
        allowSetManagement = uiState.allowSetManagement,
        onAllowSetManagementChange = {
            viewModel.onEvent(
                CreateClassUiAction.SetManagementChanged(
                    it
                )
            )
        },
        allowMemberManagement = uiState.allowMemberManagement,
        onAllowMemberManagementChange = {
            viewModel.onEvent(
                CreateClassUiAction.MemberManagementChanged(it)
            )
        },
        onDoneClick = { viewModel.onEvent(CreateClassUiAction.SaveClicked) },
        onNavigateBack = { navigator.navigateUp() }
    )

}

@Composable
fun CreateClass(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
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
    onDoneClick: () -> Unit = {},
    onNavigateBack: () -> Unit = {}
) {
    Scaffold(
        containerColor = colorScheme.background,
        modifier = modifier,
        topBar = {
            ClassesTopBar(
                onDoneClick = onDoneClick,
                onNavigateBack = onNavigateBack,
                title = "New Class"
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            ClassesTextField(
                value = title,
                title = "Class Title",
                valueError = titleError,
                onValueChange = onTitleChange,
                placeholder = "Enter Class Title"
            )
            ClassesTextField(
                value = description,
                title = "Description (Optional)",
                valueError = descriptionError,
                onValueChange = onDescriptionChange,
                placeholder = "Enter Class Description"
            )
            MemberManagementSwitch(
                allowMemberManagement = allowMemberManagement,
                onAllowMemberManagementChange = onAllowMemberManagementChange,
                allowSetManagement = allowSetManagement,
                onAllowSetManagementChange = onAllowSetManagementChange
            )
        }

        LoadingOverlay(
            isLoading = isLoading
        )
    }
}


@Preview
@Composable
private fun CreateClassesPreview() {
    MaterialTheme {
        CreateClass()
    }
}