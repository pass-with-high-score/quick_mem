package com.pwhs.quickmem.presentation.app.settings.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pwhs.quickmem.ui.theme.QuickMemTheme

@Composable
fun SettingTitleSection(title: String) {
    Text(
        text = title,
        style = typography.titleSmall.copy(
            fontWeight = FontWeight.Medium,
            color = colorScheme.onSurface.copy(alpha = 0.5f)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )
}

@Preview(showBackground = true)
@Composable
private fun SettingTitleSectionPreview() {
    QuickMemTheme {
        SettingTitleSection(title = "Title")
    }
}
