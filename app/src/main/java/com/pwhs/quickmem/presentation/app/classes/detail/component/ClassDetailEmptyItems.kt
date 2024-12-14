package com.pwhs.quickmem.presentation.app.classes.detail.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pwhs.quickmem.R
import com.pwhs.quickmem.ui.theme.QuickMemTheme

@Composable
fun ClassDetailEmptyItems(
    modifier: Modifier = Modifier,
    isOwner: Boolean,
    title: String = "",
    subtitle: String = "",
    buttonTitle: String = "",
    onAddClick: () -> Unit = {},
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 40.dp)
    ) {
        Text(
            text = title,
            style = typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = subtitle,
            style = typography.bodyMedium.copy(
                color = colorScheme.onSurface.copy(alpha = 0.6f)
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        if (isOwner) {
            Button(
                onClick = onAddClick,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Filled.Add,
                        contentDescription = stringResource(R.string.txt_add_new_study_set_to_class),
                        tint = colorScheme.background,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(
                        text = buttonTitle,
                        style = typography.titleMedium.copy(
                            color = colorScheme.background,
                            fontWeight = Bold
                        )
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ClassDetailEmptyPreview() {
    QuickMemTheme {
        ClassDetailEmptyItems(
            title = "This class has no sets",
            subtitle = "Add flashcard sets to share them with your class.",
            buttonTitle = "Add study sets",
            isOwner = true
        )
    }
}