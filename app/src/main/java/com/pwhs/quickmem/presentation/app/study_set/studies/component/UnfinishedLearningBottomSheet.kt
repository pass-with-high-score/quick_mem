package com.pwhs.quickmem.presentation.app.study_set.studies.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnfinishedLearningBottomSheet(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {},
    onKeepLearningClick: () -> Unit = {},
    onEndSessionClick: () -> Unit = {},
    showUnfinishedLearningBottomSheet: Boolean = false,
    sheetShowMoreState: SheetState = rememberModalBottomSheetState(),
) {
    if (showUnfinishedLearningBottomSheet) {
        ModalBottomSheet(
            modifier = modifier,
            onDismissRequest = onDismissRequest,
            sheetState = sheetShowMoreState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Wait, don't go yet! You'll miss out on completing this study set!",
                    style = typography.titleLarge,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Button(
                    onClick = onKeepLearningClick,
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = shapes.medium
                ) {
                    Text(
                        text = "Keep Learning",
                        style = typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
                OutlinedButton(
                    onClick = onEndSessionClick,
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = colorScheme.onSurface.copy(alpha = 0.6f)
                    ),
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .fillMaxWidth(),
                    shape = shapes.medium
                ) {
                    Text(
                        text = "End Session",
                        style = typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
    }
}