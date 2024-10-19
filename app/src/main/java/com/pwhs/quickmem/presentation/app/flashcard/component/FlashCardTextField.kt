package com.pwhs.quickmem.presentation.app.flashcard.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

@Composable
fun FlashCardTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    hint: String
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                hint,
                fontWeight = FontWeight.Bold,
                color = Color.Gray.copy(alpha = 0.5f)
            )
        },
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Gray.copy(alpha = 0.5f),
        ),
        maxLines = 4
    )
}