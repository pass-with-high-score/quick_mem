package com.pwhs.quickmem.presentation.app.folder.detail.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ButtonFolder(
    modifier: Modifier = Modifier,
    title: String,
    onFolderClicked: () -> Unit
) {
    Button(
        onClick = onFolderClicked,
        modifier = modifier
            .fillMaxWidth()
            .height(33.dp)
            .border(
                BorderStroke(2.dp, colorScheme.outline),
                shape = RoundedCornerShape(10.dp)
            ),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        Text(
            text = title,
            style = typography.titleMedium.copy(
                color = colorScheme.onSurface
            )
        )
    }
}