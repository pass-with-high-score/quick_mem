package com.pwhs.quickmem.presentation.app.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Feedback
import androidx.compose.material.icons.filled.ReportProblem
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pwhs.quickmem.presentation.app.settings.component.SettingsItem
import com.pwhs.quickmem.presentation.app.settings.component.SettingsSection
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import kotlinx.coroutines.flow.collectLatest



@OptIn(ExperimentalMaterial3Api::class)
@Destination<RootGraph>
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {},
    onLogout: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collectLatest { event ->
            when (event) {
                is SettingsUiEvent.ConfirmLogout -> {
                    // Điều hướng đến màn hình đăng nhập khi đăng xuất
                    onLogout()
                }
                is SettingsUiEvent.ShowFeedbackDialog -> {
                    // Xử lý các sự kiện khác nếu cần
                }
                is SettingsUiEvent.ShowReportDialog -> {
                    // Xử lý các sự kiện khác nếu cần
                }
                is SettingsUiEvent.ConfirmDeleteAccount -> {
                    // Xử lý khi xóa tài khoản (có thể là hiển thị thông báo hoặc điều hướng)
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings", fontSize = 20.sp) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SettingsSection(
                title = "Appearance",
                selectedOption = uiState.appearance,
                onClick = { /* Mở bottom sheet để chọn tùy chọn mới */ }
            )

            SettingsSection(
                title = "Language",
                selectedOption = uiState.language,
                onClick = { /* Mở bottom sheet để chọn ngôn ngữ mới */ }
            )

            Divider(modifier = Modifier.padding(vertical = 16.dp))

            SettingsItem(
                title = "Give feedback",
                icon = Icons.Default.Feedback,
                onClick = { viewModel.onEvent(SettingsUiAction.GiveFeedback) }
            )

            SettingsItem(
                title = "Report a problem",
                icon = Icons.Default.ReportProblem,
                onClick = { viewModel.onEvent(SettingsUiAction.ReportProblem) }
            )

            SettingsItem(
                title = "Logout",
                icon = Icons.Default.ExitToApp,
                onClick = { viewModel.onEvent(SettingsUiAction.Logout) }
            )

            SettingsItem(
                title = "Delete account",
                icon = Icons.Default.Delete,
                onClick = { viewModel.onEvent(SettingsUiAction.DeleteAccount) },
                textColor = Color.Red,
                iconColor = Color.Red
            )
        }
    }
}


