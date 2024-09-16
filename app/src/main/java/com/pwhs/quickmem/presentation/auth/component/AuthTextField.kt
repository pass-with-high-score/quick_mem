package com.pwhs.quickmem.presentation.auth.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.pwhs.quickmem.R
import com.pwhs.quickmem.core.data.TextFieldType
import com.pwhs.quickmem.util.upperCaseFirstLetter
import timber.log.Timber

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
    onClick: (() -> Unit)? = null,
    error: String? = null
) {
    var showPassword by rememberSaveable { mutableStateOf(false) }
    Timber.d("Is Error: ${error.isNullOrEmpty().not()}")
    Column {
        TextField(
            value = value,
            isError = error.isNullOrEmpty().not(),
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = label,
                    style = typography.bodyLarge.copy(
                        color = colorScheme.onSurface.copy(alpha = 0.6f),
                        fontWeight = FontWeight.Bold
                    )
                )
            },
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
            trailingIcon = {
                if (type == TextFieldType.PASSWORD) {
                    Icon(
                        painter = painterResource(id = if (showPassword) R.drawable.ic_eye else R.drawable.ic_eye_off),
                        contentDescription = "Show password",
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                showPassword = !showPassword
                            },
                        tint = colorScheme.onSurface,
                    )
                }
            },
            visualTransformation = if (type == TextFieldType.PASSWORD && !showPassword) {
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
                errorContainerColor = Color.Transparent,
                disabledIndicatorColor = if (!error.isNullOrEmpty()) colorScheme.error else colorScheme.onSurface,
            ),

            modifier = modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .then(
                    if (onClick != null) Modifier.clickable { onClick() } else Modifier
                ),
            textStyle = typography.bodyLarge.copy(
                color = colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )
        )

        Text(
            text = error?.upperCaseFirstLetter() ?: type.name.lowercase().upperCaseFirstLetter(),
            style = typography.bodyMedium.copy(
                color = error?.let { colorScheme.error }
                    ?: colorScheme.onSurface.copy(alpha = 0.6f),
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

