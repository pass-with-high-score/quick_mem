package com.pwhs.quickmem.presentation.app.classes.detail.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight

@Composable
fun ClassTextField(
    modifier: Modifier = Modifier,
    placeholder: String = "",
    value: String = "",
    onValueChange: (String) -> Unit = {},
    @StringRes errorMessage: Int? = null,
) {
    TextField(
        modifier = modifier
            .fillMaxWidth(),
        value = value,
        maxLines = 1,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeholder,
                style = typography.bodyMedium.copy(
                    color = colorScheme.onSurface.copy(alpha = 0.6f),
                    fontWeight = FontWeight.Bold
                )
            )
        },
        colors = TextFieldDefaults.colors(
            unfocusedIndicatorColor = Color.Transparent,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            errorContainerColor = Color.White,
        ),
        shape = shapes.medium,
        isError = errorMessage != null,
        supportingText = {
            errorMessage?.let {
                Text(
                    text = stringResource(it),
                    color = colorScheme.error,
                    style = typography.bodyMedium
                )
            }
        }
    )
}