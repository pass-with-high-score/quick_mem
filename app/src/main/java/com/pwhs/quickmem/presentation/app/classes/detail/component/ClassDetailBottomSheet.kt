package com.pwhs.quickmem.presentation.app.classes.detail.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.Icons.Outlined
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.IosShare
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Report
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.pwhs.quickmem.presentation.app.study_set.detail.component.ItemMenuBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassDetailBottomSheet(
    modifier: Modifier = Modifier,
    isOwner: Boolean,
    isMember: Boolean,
    onAddStudySetToClass: () -> Unit = {},
    onAddFolderToClass: () -> Unit = {},
    onEditClass: () -> Unit = {},
    onExitClass: () -> Unit = {},
    onDeleteClass: () -> Unit = {},
    onShareClass: () -> Unit = {},
    onReportClass: () -> Unit = {},
    showMoreBottomSheet: Boolean = false,
    sheetShowMoreState: SheetState = rememberModalBottomSheetState(),
    onDismissRequest: () -> Unit = {},
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
                if (isOwner) {
                    ItemMenuBottomSheet(
                        onClick = onAddStudySetToClass,
                        icon = Outlined.Add,
                        title = "Add study set to class"
                    )
                    ItemMenuBottomSheet(
                        onClick = onAddFolderToClass,
                        icon = Outlined.Add,
                        title = "Add folder to class"
                    )
                    ItemMenuBottomSheet(
                        onClick = onEditClass,
                        icon = Outlined.Edit,
                        title = "Edit Class"
                    )
                }
                if (!isOwner && isMember) {
                    ItemMenuBottomSheet(
                        onClick = onExitClass,
                        icon = Icons.AutoMirrored.Filled.ExitToApp,
                        title = "Exit Class",
                        color = Color.Red
                    )
                }
                ItemMenuBottomSheet(
                    onClick = onShareClass,
                    icon = Default.IosShare,
                    title = "Share Class"
                )
                ItemMenuBottomSheet(
                    onClick = onReportClass,
                    icon = Outlined.Report,
                    title = "Report Class"
                )
                if (isOwner) {
                    ItemMenuBottomSheet(
                        onClick = onDeleteClass,
                        icon = Default.DeleteOutline,
                        title = "Delete Class",
                        color = Color.Red
                    )
                }

            }
        }
    }
}