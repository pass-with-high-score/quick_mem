package com.pwhs.quickmem.presentation.app.flashcard.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlashCardTopAppBar(
    modifier: Modifier = Modifier,
    onNavigationBack: () -> Unit,
    onSaveFlashCardClicked: () -> Unit,
    enableSaveButton: Boolean,
    onSettingsClicked: () -> Unit
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = "Create Flashcard",
                style = typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                )
            )
        },
        navigationIcon = {
            IconButton(onClick = onNavigationBack) {
                Icon(imageVector = Icons.Filled.Clear, contentDescription = "Back")
            }
        },
        actions = {
            IconButton(onClick = onSettingsClicked) {
                Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings")
            }
            IconButton(
                onClick = onSaveFlashCardClicked,
                enabled = enableSaveButton
            ) {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = "Save",
                    tint = if (enableSaveButton) {
                        colorScheme.primary
                    } else {
                        colorScheme.onSurface.copy(alpha = 0.5f)
                    }
                )
            }
        }
    )
}