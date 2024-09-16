package com.pwhs.quickmem.presentation.auth.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.pwhs.quickmem.core.data.TextFieldType

@Composable
fun AuthTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    iconId: Int,
    contentDescription: String,
    readOnly: Boolean = false,
    enabled: Boolean = true,
    type: TextFieldType = TextFieldType.TEXT,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    Column {
        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(label) },
            readOnly = readOnly,
            enabled = enabled,
            maxLines = 1,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next,
                keyboardType = when (type) {
                    TextFieldType.EMAIL -> KeyboardType.Email
                    TextFieldType.PASSWORD -> KeyboardType.Password
                    else -> KeyboardType.Text
                }
            ),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = iconId),
                    contentDescription = contentDescription,
                    modifier = Modifier.size(24.dp),
                    tint = colorScheme.onSurface
                )
            },
            visualTransformation = if (type == TextFieldType.PASSWORD) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            shape = shapes.medium,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                disabledTextColor = colorScheme.onSurface,
                disabledPlaceholderColor = colorScheme.onSurface,
                focusedTextColor = colorScheme.onSurface,
                focusedPlaceholderColor = colorScheme.onSurface,
                cursorColor = colorScheme.onSurface,

                ),

            modifier = modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .then(
                    if (onClick != null) Modifier.clickable { onClick() } else Modifier
                )
        )
    }
}

