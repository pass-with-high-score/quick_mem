package com.pwhs.quickmem.presentation.app.study_set.studies.flip.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun FlipFlashCardButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    studySetColor: Color,
    title: String
) {
    ElevatedButton(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = studySetColor.copy(alpha = 0.6f)
        ),
        elevation = ButtonDefaults.elevatedButtonElevation(
            defaultElevation = 3.dp
        ),
        border = BorderStroke(1.dp, Color.White)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Lightbulb,
                contentDescription = title,
            )
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}