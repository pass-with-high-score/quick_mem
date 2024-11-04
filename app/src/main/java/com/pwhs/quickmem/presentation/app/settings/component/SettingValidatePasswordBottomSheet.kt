package com.pwhs.quickmem.presentation.app.settings.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
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
    var showPassword by remember {
        mutableStateOf(false)
    }
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
                TextField(
                    value = password,
                    onValueChange = onChangePassword,
                    placeholder = {
                        Text(
                            "Password",
                            style = typography.bodyMedium.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                    },
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth(),
                    visualTransformation = if (showPassword) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    colors = TextFieldDefaults.colors(
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedContainerColor = colorScheme.surface,
                        unfocusedContainerColor = colorScheme.surface,
                        errorContainerColor = colorScheme.surface,
                    ),
                    shape = shapes.medium,
                    isError = errorMessage.isNotEmpty(),
                    supportingText = {
                        if (errorMessage.isNotEmpty()) {
                            Text(
                                errorMessage,
                                style = typography.bodySmall.copy(
                                    color = colorScheme.error
                                )
                            )
                        }
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                showPassword = !showPassword
                            }
                        ) {
                            Icon(
                                imageVector = if (showPassword) {
                                    Icons.Default.Visibility
                                } else {
                                    Icons.Default.VisibilityOff
                                },
                                contentDescription = "Toggle password visibility",
                                tint = colorScheme.onSurface.copy(alpha = 0.6f)
                            )
                        }
                    }
                )
                Button(
                    enabled = password.isNotEmpty() && password.length >= 8,
                    onClick = onSubmitClick,
                    modifier = Modifier
                        .padding(top = 16.dp)
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