package com.pwhs.quickmem.presentation.app.profile.component

import androidx.compose.foundation.layout.Row

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight

@Composable
fun TeacherTextField(
    modifier: Modifier = Modifier,
    title: String,
    role: String,
    textStyle: androidx.compose.ui.text.TextStyle = typography.bodySmall.copy(
        color = MaterialTheme.colorScheme.secondary,
        fontWeight = FontWeight.Bold
    )
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val text = if (role == "TEACHER") {
            "$title (Teacher)"
        } else {
            title
        }

        Text(
            text = text,
            style = textStyle
        )
    }
}


