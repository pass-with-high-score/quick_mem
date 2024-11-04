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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pwhs.quickmem.ui.theme.QuickMemTheme

@Composable
fun SettingItem(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String? = null,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 8.dp)
                .clickable(onClick = onClick)
        ) {
            Text(text = title, fontWeight = FontWeight.Medium)
            subtitle?.let {
                Text(
                    text = it,
                    fontSize = 12.sp,
                    color = colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
        }
        Icon(
            imageVector = AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = "Navigate to $title",
            modifier = Modifier.size(30.dp)
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
