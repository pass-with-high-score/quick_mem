package com.pwhs.quickmem.presentation.auth.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AuthTextField(
    modifier: Modifier = Modifier,
    label: String,
    isError: Boolean = false,
    errorMessage: String = "",
    leadingIcon: ImageVector = Icons.Outlined.Email,
    value: String,
    onValueChange: (String) -> Unit,
    isSecure: Boolean = false
) {
    Column {
        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(text = label) },
            leadingIcon = {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null,
                    tint = colorScheme.onSurface
                )
            },
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            isError = isError,
            colors = TextFieldDefaults.run {
                colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,

                    errorContainerColor = Color.Transparent,

                    focusedIndicatorColor = colorScheme.primary,
                    unfocusedIndicatorColor = colorScheme.onSurface,
                )
            },
            visualTransformation = if (isSecure) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done
            )
        )
        if (isError) {
            Text(
                text = errorMessage,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = colorScheme.error,
                    fontWeight = FontWeight.Normal
                ),
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun AuthTextFieldPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), contentAlignment = Alignment.Center
    ) {
        AuthTextField(
            label = "Email",
            value = "",
            onValueChange = {},
            leadingIcon = Icons.Outlined.Email,
            errorMessage = "Invalid email",
            isError = true
        )
    }
}