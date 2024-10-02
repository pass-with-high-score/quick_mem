package com.pwhs.quickmem.presentation.app.flashcard.create.draw.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun RowScope.MenuItems(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    desc: String,
    colorTint: Color,
    border: Boolean = false,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick, modifier = Modifier.weight(1f, true)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = desc,
            tint = colorTint,
            modifier = if (border) modifier.border(
                0.5.dp,
                Color.Black,
                shape = CircleShape
            ) else modifier.size(24.dp)
        )
    }
}