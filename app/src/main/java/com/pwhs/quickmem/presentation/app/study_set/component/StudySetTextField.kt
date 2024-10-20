package com.pwhs.quickmem.presentation.app.study_set.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun StudySetTextField(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    onValueChange: (String) -> Unit,
    valueError: String
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
    ) {
        Text(
            text = title,
            style = typography.bodyMedium.copy(
                fontWeight = FontWeight.Bold
            )
        )
        OutlinedTextField(
            shape = RoundedCornerShape(10.dp),
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text("Title") },
            isError = valueError.isNotEmpty(),
            supportingText = {
                valueError.isNotEmpty().let {
                    Text(valueError)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = colorScheme.primary,
                focusedSupportingTextColor = colorScheme.error,
                unfocusedSupportingTextColor = colorScheme.error,
                errorContainerColor = Color.Transparent,
                unfocusedIndicatorColor = colorScheme.onSurface.copy(alpha = 0.12f),
            )
        )
    }
}