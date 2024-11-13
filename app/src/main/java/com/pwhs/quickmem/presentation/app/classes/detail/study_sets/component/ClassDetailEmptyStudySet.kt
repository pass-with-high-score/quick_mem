package com.pwhs.quickmem.presentation.app.classes.detail.study_sets.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
fun ClassDetailEmptyStudySet(
    modifier: Modifier = Modifier,
    title: String = "",
    subtitle: String = "",
    buttonTitle: String = "",
    onAddStudySetClicked: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
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
            Button(
                onClick = onAddStudySetClicked,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Filled.Add,
                        contentDescription = buttonTitle,
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
        ClassDetailEmptyStudySet(
            title = stringResource(R.string.txt_this_class_has_no_sets),
            subtitle = stringResource(R.string.txt_add_flashcard_sets_to_share_them_with_your_class),
            buttonTitle = stringResource(R.string.txt_add_study_sets),
        )
    }
}