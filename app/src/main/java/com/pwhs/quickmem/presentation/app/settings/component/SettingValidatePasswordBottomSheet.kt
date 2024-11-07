package com.pwhs.quickmem.presentation.app.settings.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pwhs.quickmem.ui.theme.QuickMemTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingValidatePasswordBottomSheet(
    modifier: Modifier = Modifier,
    showVerifyPasswordBottomSheet: Boolean = false,
    bottomSheetState: SheetState = rememberModalBottomSheetState(),
    password: String = "",
    errorMessage: String = "",
    onChangePassword: (String) -> Unit = {},
    onSubmitClick: () -> Unit = {},
    onDismissRequest: () -> Unit = {}
) {
    if (showVerifyPasswordBottomSheet) {
        ModalBottomSheet(
            modifier = modifier,
            onDismissRequest = onDismissRequest,
            sheetState = bottomSheetState,
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    "First, verify your account",
                    style = typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    "To confirm it's really you, please enter your QuickMem password",
                    style = typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
                SettingTextField(
                    modifier = Modifier.padding(top = 16.dp),
                    value = password,
                    onValueChange = onChangePassword,
                    errorMessage = errorMessage,
                    placeholder = "Password",
                    isSecure = true
                )
                Button(
                    enabled = password.isNotEmpty() && password.length >= 8,
                    onClick = onSubmitClick,
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = shapes.medium
                ) {
                    Text(
                        "Submit", style = typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
                OutlinedButton(
                    onClick = onDismissRequest,
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = colorScheme.onSurface.copy(alpha = 0.6f)
                    ),
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth(),
                    shape = shapes.medium
                ) {
                    Text(
                        "Cancel",
                        style = typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true)
@Composable
private fun SettingValidatePasswordBottomSheetPreview() {
    QuickMemTheme {
        SettingValidatePasswordBottomSheet(
            showVerifyPasswordBottomSheet = true,
            password = "password",
            errorMessage = "Error message",
            onChangePassword = { },
            onSubmitClick = { },
            onDismissRequest = { }
        )
    }
}