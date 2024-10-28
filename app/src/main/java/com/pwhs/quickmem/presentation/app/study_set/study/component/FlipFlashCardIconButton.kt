package com.pwhs.quickmem.presentation.app.study_set.study.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun FlipFlashCardIconButton(
    modifier: Modifier = Modifier,
    knownColor: Color,
    contentDescription: String,
    onClick: () -> Unit,
    imageVector: ImageVector
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .border(1.dp, Color.Gray, CircleShape)
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            tint = knownColor,
            modifier = Modifier
                .padding(10.dp)
        )
    }
}