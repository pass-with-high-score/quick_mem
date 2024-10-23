package com.pwhs.quickmem.presentation.app.profile.settings

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph


@OptIn(ExperimentalMaterial3Api::class)
@Destination<RootGraph>
@Composable
fun SettingsScreen(
    onBackClick: () -> Unit = {},
    onFeedbackClick: () -> Unit = {},
    onReportClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {},
    onDeleteAccountClick: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings", fontWeight = FontWeight.Bold, fontSize = 20.sp) },
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
                selectedOption = "Device Theme",
                onClick = { /* Mở bottom sheet để chọn tùy chọn mới */ }
            )

            SettingsSection(
                title = "Language",
                selectedOption = "English (US)",
                onClick = { /* Mở bottom sheet để chọn ngôn ngữ mới */ }
            )

            Divider(modifier = Modifier.padding(vertical = 16.dp))

            SettingsItem(
                title = "Give feedback",
                icon = Icons.Default.Feedback,
                onClick = onFeedbackClick
            )

            SettingsItem(
                title = "Report a problem",
                icon = Icons.Default.ReportProblem,
                onClick = onReportClick
            )

            SettingsItem(
                title = "Logout",
                icon = Icons.Default.ExitToApp,
                onClick = onLogoutClick
            )

            SettingsItem(
                title = "Delete account",
                icon = Icons.Default.Delete,
                onClick = onDeleteAccountClick,
                textColor = Color.Red,
                iconColor = Color.Red
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    SettingsScreen()
}