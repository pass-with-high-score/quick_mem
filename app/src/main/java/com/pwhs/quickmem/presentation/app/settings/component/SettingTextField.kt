package com.pwhs.quickmem.presentation.app.settings.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pwhs.quickmem.ui.theme.QuickMemTheme

@Composable
fun SettingTextField(
    modifier: Modifier = Modifier,
    placeholder: String = "",
    value: String = "",
    onValueChange: (String) -> Unit = {},
    errorMessage: String = ""
) {
    TextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .padding(horizontal = 16.dp),
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
        isError = errorMessage.isNotEmpty(),
        supportingText = {
            Text(
                text = errorMessage,
                color = colorScheme.error,
                style = typography.bodyMedium
            )
        }
    )
}

@Preview(showSystemUi = true)
@Composable
private fun SettingTextFieldPreview() {
    QuickMemTheme {
        Scaffold {
            SettingTextField(
                placeholder = "Placeholder",
                value = "Value",
                onValueChange = { },
                errorMessage = "Error message",
                modifier = Modifier.padding(it)
            )
        }
    }
}