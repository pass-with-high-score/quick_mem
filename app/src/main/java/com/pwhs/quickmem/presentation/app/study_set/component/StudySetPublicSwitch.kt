package com.pwhs.quickmem.presentation.app.study_set.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun StudySetPublicSwitch(
    modifier: Modifier = Modifier,
    isPublic: Boolean,
    onIsPublicChange: (Boolean) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorScheme.surfaceContainerLow
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    "Make Study Set public",
                    style = typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Switch(
                    checked = isPublic,
                    onCheckedChange = onIsPublicChange,
                )
            }

            Text(
                text = "When you make a study set public, anyone can see it and use it.",
                style = typography.bodySmall.copy(
                    color = colorScheme.onSurface.copy(alpha = 0.6f)
                ),
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}