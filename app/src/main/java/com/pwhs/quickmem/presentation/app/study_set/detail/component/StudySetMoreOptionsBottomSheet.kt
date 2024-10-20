package com.pwhs.quickmem.presentation.app.study_set.detail.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.Icons.Outlined
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.outlined.Report
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudySetMoreOptionsBottomSheet(
    modifier: Modifier = Modifier,
    onEditStudySet: () -> Unit,
    onDeleteStudySet: () -> Unit,
    onInfoStudySet: () -> Unit,
    showMoreBottomSheet: Boolean,
    sheetShowMoreState: SheetState,
    onDismissRequest: () -> Unit,
    onResetProgress: () -> Unit
) {
    if (showMoreBottomSheet) {
        ModalBottomSheet(
            modifier = modifier,
            onDismissRequest = onDismissRequest,
            sheetState = sheetShowMoreState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                ItemMenuBottomSheet(
                    onClick = onEditStudySet,
                    icon = Outlined.Edit,
                    title = "Edit"
                )
                ItemMenuBottomSheet(
                    onClick = { },
                    icon = Outlined.Folder,
                    title = "Add to folder"
                )
                ItemMenuBottomSheet(
                    onClick = { },
                    icon = Outlined.Group,
                    title = "Add to class"
                )
                ItemMenuBottomSheet(
                    onClick = { },
                    icon = Outlined.ContentCopy,
                    title = "Save and edit"
                )
                ItemMenuBottomSheet(
                    onClick = onResetProgress,
                    icon = Outlined.Refresh,
                    title = "Reset progress"
                )
                ItemMenuBottomSheet(
                    onClick = onInfoStudySet,
                    icon = Outlined.Info,
                    title = "Study set info"
                )
                ItemMenuBottomSheet(
                    onClick = {},
                    icon = Outlined.Report,
                    title = "Report study set"
                )
                ItemMenuBottomSheet(
                    onClick = onDeleteStudySet,
                    icon = Default.DeleteOutline,
                    title = "Delete study set",
                    color = Color.Red
                )
            }
        }
    }
}