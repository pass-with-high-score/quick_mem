package com.pwhs.quickmem.presentation.app.classes.create

import android.widget.Toast
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
import com.ramcosta.composedestinations.generated.destinations.ClassDetailScreenDestination
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
                    navigator.navigate(
                        ClassDetailScreenDestination(
                            code = "",
                            id = event.id,
                            title = uiState.title,
                            description = uiState.description
                        )
                    )
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
            CreateTopAppBar(
                onDoneClick = onDoneClick,
                onNavigateBack = onNavigateBack,
                title = stringResource(R.string.txt_create_new_class)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            CreateTextField(
                value = title,
                title = stringResource(R.string.txt_class_title),
                valueError = titleError,
                onValueChange = onTitleChange,
                placeholder = stringResource(R.string.txt_enter_class_title)
            )
            CreateTextField(
                value = description,
                title = stringResource(R.string.txt_description_optional),
                valueError = descriptionError,
                onValueChange = onDescriptionChange,
                placeholder = stringResource(R.string.txt_enter_class_description)
            )
            SwitchContainer(
                text = stringResource(R.string.txt_allow_class_members_to_send_invites_to_other_people),
                checked = allowMemberManagement,
                onCheckedChange = onAllowMemberManagementChange
            )
            SwitchContainer(
                text = stringResource(R.string.txt_allow_class_members_to_add_study_set_and_folders),
                checked = allowSetManagement,
                onCheckedChange = onAllowSetManagementChange
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
    QuickMemTheme {
        CreateClass()
    }
}