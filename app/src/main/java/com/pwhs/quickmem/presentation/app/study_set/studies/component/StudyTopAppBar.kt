package com.pwhs.quickmem.presentation.app.study_set.studies.component

import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.pwhs.quickmem.ui.theme.QuickMemTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudyTopAppBar(
    modifier: Modifier = Modifier,
    currentCardIndex: Int,
    totalCards: Int,
    onBackClicked: () -> Unit,
    isEnOfSet: Boolean = false,
    onRestartClicked: () -> Unit = {},
    shouldShowRestart: Boolean = true
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = if (isEnOfSet) "Congratulations!" else "Card $currentCardIndex of $totalCards",
                style = typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                )
            )
        },
        navigationIcon = {
            IconButton(
                onClick = onBackClicked
            ) {
                Icon(
                    imageVector = Default.Clear,
                    contentDescription = "Back"
                )
            }
        },
        actions = {
            if (shouldShowRestart) {
                IconButton(
                    onClick = onRestartClicked
                ) {
                    Icon(
                        imageVector = Default.RestartAlt,
                        contentDescription = "Restart"
                    )
                }
            }
        }
    )
}

@PreviewLightDark
@Composable
fun StudyTopAppBarPreview() {
    QuickMemTheme {
        StudyTopAppBar(
            currentCardIndex = 0,
            totalCards = 10,
            onBackClicked = {},
        )
    }
}