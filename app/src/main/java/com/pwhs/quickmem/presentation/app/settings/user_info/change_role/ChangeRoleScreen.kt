package com.pwhs.quickmem.presentation.app.settings.user_info.change_role

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pwhs.quickmem.R
import com.pwhs.quickmem.presentation.app.settings.component.SettingTopAppBar
import com.pwhs.quickmem.ui.theme.QuickMemTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.result.ResultBackNavigator

@Destination<RootGraph>(navArgs = ChangeRoleArgs::class)
@Composable
fun ChangeRoleScreen(
    modifier: Modifier = Modifier,
    viewModel: ChangeRoleViewModel = hiltViewModel(),
    resultNavigator: ResultBackNavigator<Boolean>
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is ChangeRoleUiEvent.ShowUnderageDialog -> {
                    dialogMessage = context.getString(
                        R.string.dialog_message_underage,
                        event.message
                    )
                    showDialog = true
                }
                is ChangeRoleUiEvent.ShowError -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
                is ChangeRoleUiEvent.RoleChangedSuccessfully -> {
                    Toast.makeText(context, context.getString(R.string.txt_role_updated_success), Toast.LENGTH_SHORT).show()
                    resultNavigator.navigateBack(true)
                }
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(stringResource(R.string.dialog_title_not_eligible)) },
            text = { Text(dialogMessage) },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("OK")
                }
            }
        )
    }

    ChangeRole(
        modifier = modifier,
        role = uiState.role,
        isLoading = uiState.isLoading,
        onRoleChanged = { role ->
            viewModel.onEvent(ChangeRoleUiAction.SelectRole(role))
        },
        onNavigateBack = {
            resultNavigator.navigateBack(false)
        },
        onSaved = {
            viewModel.onEvent(ChangeRoleUiAction.SaveRole)
        }
    )
}

@Composable
private fun ChangeRole(
    modifier: Modifier = Modifier,
    role: String,
    isLoading: Boolean = false,
    onRoleChanged: (String) -> Unit,
    onNavigateBack: () -> Unit,
    onSaved: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            SettingTopAppBar(
                title = stringResource(R.string.txt_change_role),
                onNavigateBack = onNavigateBack,
                onSaved = onSaved,
                enabled = role.isNotEmpty() && !isLoading
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Card(
                modifier = Modifier.padding(16.dp)
            ) {
                Column {
                    RoleItem(
                        role = "STUDENT",
                        currentRole = role,
                        onRoleChanged = onRoleChanged
                    )
                    HorizontalDivider()
                    RoleItem(
                        role = "TEACHER",
                        currentRole = role,
                        onRoleChanged = onRoleChanged
                    )
                }
            }
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}


@Composable
private fun RoleItem(
    role: String,
    currentRole: String,
    onRoleChanged: (String) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onRoleChanged(role) }
            .padding(8.dp)
    ) {
        RadioButton(
            selected = currentRole == role,
            onClick = { onRoleChanged(role) }
        )
        Text(
            text = role,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
    }
}

@Preview
@Composable
private fun ChangeRoleScreenPreview() {
    QuickMemTheme {
        ChangeRole(
            role = "STUDENT",
            onRoleChanged = {},
            onNavigateBack = {},
            onSaved = {}
        )
    }
}
