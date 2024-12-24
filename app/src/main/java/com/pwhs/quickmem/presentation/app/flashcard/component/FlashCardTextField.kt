package com.pwhs.quickmem.presentation.app.flashcard.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

@Composable
fun FlashCardTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    color: Color,
) {
    val maxChar = 200
    TextField(
        value = value,
        onValueChange = {
            if (it.length <= maxChar) {
                onValueChange(it.take(maxChar))
            }
        },
        placeholder = {
            Text(
                text = hint,
                fontWeight = FontWeight.Bold,
                color = Color.Gray.copy(alpha = 0.5f)
            )
        },
        supportingText = {
            Text(
                text = "${value.length}/$maxChar",
                color = Color.Gray.copy(alpha = 0.5f),
                textAlign = TextAlign.End
            )
        },
        modifier = modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            focusedIndicatorColor = color,
            cursorColor = color,
            unfocusedContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Gray.copy(alpha = 0.5f),
        ),
        maxLines = 4
    )
}