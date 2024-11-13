package com.pwhs.quickmem.presentation.app.classes.detail.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.Icons.Outlined
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
import androidx.compose.ui.res.stringResource
import com.pwhs.quickmem.R
import com.pwhs.quickmem.presentation.app.study_set.detail.component.ItemMenuBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassDetailBottomSheet(
    modifier: Modifier = Modifier,
    onAddStudySetToClass: () -> Unit = {},
    onAddFolderToClass: () -> Unit = {},
    onEditClass: () -> Unit = {},
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
                ItemMenuBottomSheet(
                    onClick = onAddStudySetToClass,
                    icon = Outlined.Add,
                    title = stringResource(R.string.txt_add_study_set_to_class)
                )
                ItemMenuBottomSheet(
                    onClick = onAddFolderToClass,
                    icon = Outlined.Add,
                    title = stringResource(R.string.txt_add_folder_to_class)
                )
                ItemMenuBottomSheet(
                    onClick = onEditClass,
                    icon = Outlined.Edit,
                    title = stringResource(R.string.txt_save_and_edit_2)
                )
                ItemMenuBottomSheet(
                    onClick = onShareClass,
                    icon = Default.IosShare,
                    title = stringResource(R.string.txt_share_class)
                )
                ItemMenuBottomSheet(
                    onClick = onReportClass,
                    icon = Outlined.Report,
                    title = stringResource(R.string.txt_report_class)
                )
                ItemMenuBottomSheet(
                    onClick = onDeleteClass,
                    icon = Default.DeleteOutline,
                    title = stringResource(R.string.txt_delete_class),
                    color = Color.Red
                )
            }
        }
    }
}