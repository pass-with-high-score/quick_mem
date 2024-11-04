package com.pwhs.quickmem.presentation.app.settings.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons.AutoMirrored
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pwhs.quickmem.ui.theme.QuickMemTheme

@Composable
fun SettingItem(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String? = null,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 8.dp, horizontal = 8.dp)
                .clip(shapes.medium)
        ) {
            Text(
                text = title,
                style = typography.titleMedium.copy(
                    fontWeight = FontWeight.Medium,
                    color = colorScheme.onSurface
                )
            )
            subtitle?.let {
                Text(
                    text = it,
                    style = typography.bodySmall.copy(
                        color = colorScheme.onSurfaceVariant
                    )
                )
            }
        }
        Icon(
            imageVector = AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = "Navigate to $title",
            modifier = Modifier.size(30.dp),
            tint = colorScheme.onSurfaceVariant
        )
    }

}

@Preview(showBackground = true)
@Composable
private fun SettingItemPreview() {
    QuickMemTheme {
        SettingItem(title = "Title", subtitle = "Subtitle")
    }

}
