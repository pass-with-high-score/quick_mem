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
import androidx.compose.ui.res.stringResource
import com.pwhs.quickmem.R
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
                        title = stringResource(R.string.txt_add_study_set_to_class)
                    )
                    ItemMenuBottomSheet(
                        onClick = onAddFolderToClass,
                        icon = Outlined.Folder,
                        title = stringResource(R.string.txt_add_folder_to_class)
                    )
                }
                if (isOwner) {
                    ItemMenuBottomSheet(
                        onClick = onEditClass,
                        icon = Outlined.Edit,
                        title = stringResource(R.string.txt_edit_class)
                    )
                }

                if (!isMember && isAllowMember) {
                    ItemMenuBottomSheet(
                        onClick = onJoinClass,
                        icon = Outlined.GroupAdd,
                        title = stringResource(R.string.txt_join_class)
                    )
                }
                ItemMenuBottomSheet(
                    onClick = onShareClass,
                    icon = Default.IosShare,
                    title = stringResource(R.string.txt_share_class)
                )
                if (!isOwner && isMember) {
                    ItemMenuBottomSheet(
                        onClick = onExitClass,
                        icon = Icons.AutoMirrored.Filled.ExitToApp,
                        title = stringResource(R.string.txt_exit_class),
                    )
                }
                if (isOwner) {
                    ItemMenuBottomSheet(
                        onClick = onDeleteClass,
                        icon = Default.DeleteOutline,
                        title = stringResource(R.string.txt_delete_class),
                        color = Color.Red
                    )
                }
                if (!isOwner) {
                    ItemMenuBottomSheet(
                        onClick = onReportClass,
                        icon = Outlined.Report,
                        title = stringResource(R.string.txt_report_class)
                    )
                }
            }
        }
    }
}