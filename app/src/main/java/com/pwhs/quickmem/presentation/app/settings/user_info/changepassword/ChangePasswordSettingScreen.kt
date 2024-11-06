package com.pwhs.quickmem.presentation.app.settings.user_info.changepassword

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pwhs.quickmem.presentation.app.settings.component.SettingTextField
import com.pwhs.quickmem.presentation.app.settings.component.SettingTopAppBar
import com.pwhs.quickmem.presentation.component.LoadingOverlay
import com.pwhs.quickmem.ui.theme.QuickMemTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.result.ResultBackNavigator

@Destination<RootGraph>(navArgs = ChangePasswordSettingArgs::class)
@Composable
fun ChangePasswordSettingScreen(
    modifier: Modifier = Modifier,
    viewModel: ChangePasswordSettingViewModel = hiltViewModel(),
    resultNavigator: ResultBackNavigator<Boolean>
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is ChangePasswordSettingUiEvent.OnError -> {
                    Toast.makeText(context, event.errorMessage, Toast.LENGTH_SHORT).show()
                }
                ChangePasswordSettingUiEvent.OnPasswordChanged -> {
                    Toast.makeText(context, "Password changed successfully", Toast.LENGTH_SHORT).show()
                    resultNavigator.navigateBack(true)
                }
            }
        }
    }

    ChangePasswordSetting(
        modifier = modifier,
        currentPassword = uiState.currentPassword,
        newPassword = uiState.newPassword,
        confirmPassword = uiState.confirmPassword,
        errorMessage = uiState.errorMessage,
        isLoading = uiState.isLoading,
        onCurrentPasswordChanged = { password ->
            viewModel.onEvent(ChangePasswordSettingUiAction.OnCurrentPasswordChanged(password))
        },
        onNewPasswordChanged = { password ->
            viewModel.onEvent(ChangePasswordSettingUiAction.OnNewPasswordChanged(password))
        },
        onConfirmPasswordChanged = { password ->
            viewModel.onEvent(ChangePasswordSettingUiAction.OnConfirmPasswordChanged(password))
        },
        onNavigateBack = {
            resultNavigator.navigateBack(false)
        },
        onSaved = {
            viewModel.onEvent(ChangePasswordSettingUiAction.OnSaveClicked)
        }
    )
}

@Composable
fun ChangePasswordSetting(
    modifier: Modifier = Modifier,
    currentPassword: String = "",
    newPassword: String = "",
    confirmPassword: String = "",
    errorMessage: String = "",
    isLoading: Boolean = false,
    onCurrentPasswordChanged: (String) -> Unit = {},
    onNewPasswordChanged: (String) -> Unit = {},
    onConfirmPasswordChanged: (String) -> Unit = {},
    onNavigateBack: () -> Unit = {},
    onSaved: () -> Unit = {}
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            SettingTopAppBar(
                title = "Change Password",
                onNavigateBack = onNavigateBack,
                onSaved = onSaved,
                enabled = currentPassword.isNotEmpty() && newPassword.isNotEmpty() && confirmPassword.isNotEmpty()
            )
        }
    ) { innerPadding ->
        Box {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                SettingTextField(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .padding(horizontal = 16.dp),
                    value = currentPassword,
                    onValueChange = onCurrentPasswordChanged,
                    placeholder = "Current Password",
                    errorMessage = if (errorMessage.isNotEmpty()) errorMessage else ""
                )
                SettingTextField(
                    modifier = Modifier
                        .padding(horizontal = 16.dp),
                    value = newPassword,
                    onValueChange = onNewPasswordChanged,
                    placeholder = "New Password",
                    errorMessage = if (errorMessage.isNotEmpty()) errorMessage else ""
                )
                SettingTextField(
                    modifier = Modifier
                        .padding(horizontal = 16.dp),
                    value = confirmPassword,
                    onValueChange = onConfirmPasswordChanged,
                    placeholder = "Confirm New Password",
                    errorMessage = if (errorMessage.isNotEmpty()) errorMessage else ""
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
private fun ChangePasswordSettingScreenPreview() {
    QuickMemTheme {
        ChangePasswordSetting()
    }
}
