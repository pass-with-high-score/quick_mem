package com.pwhs.quickmem.presentation.app.classes.detail.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.Icons.Outlined
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.IosShare
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.GroupAdd
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
    isAllowMember: Boolean,
    onAddStudySetToClass: () -> Unit = {},
    onAddFolderToClass: () -> Unit = {},
    onEditClass: () -> Unit = {},
    onExitClass: () -> Unit = {},
    onDeleteClass: () -> Unit = {},
    onShareClass: () -> Unit = {},
    onReportClass: () -> Unit = {},
    onJoinClass: () -> Unit = {},
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
                if (isOwner || isMember) {
                    ItemMenuBottomSheet(
                        onClick = onAddStudySetToClass,
                        icon = Outlined.ContentCopy,
                        title = "Add study set to class"
                    )
                    ItemMenuBottomSheet(
                        onClick = onAddFolderToClass,
                        icon = Outlined.Folder,
                        title = "Add folder to class"
                    )
                }
                if (isOwner) {
                    ItemMenuBottomSheet(
                        onClick = onEditClass,
                        icon = Outlined.Edit,
                        title = "Edit Class"
                    )
                }

                if (!isMember && isAllowMember) {
                    ItemMenuBottomSheet(
                        onClick = onJoinClass,
                        icon = Outlined.GroupAdd,
                        title = "Join Class"
                    )
                }
                ItemMenuBottomSheet(
                    onClick = onShareClass,
                    icon = Default.IosShare,
                    title = "Share Class"
                )
                if (!isOwner && isMember) {
                    ItemMenuBottomSheet(
                        onClick = onExitClass,
                        icon = Icons.AutoMirrored.Filled.ExitToApp,
                        title = "Exit Class",
                    )
                }
                if (isOwner) {
                    ItemMenuBottomSheet(
                        onClick = onDeleteClass,
                        icon = Default.DeleteOutline,
                        title = "Delete Class",
                        color = Color.Red
                    )
                }
                if (!isOwner) {
                    ItemMenuBottomSheet(
                        onClick = onReportClass,
                        icon = Outlined.Report,
                        title = "Report Class"
                    )
                }
            }
        }
    }
}