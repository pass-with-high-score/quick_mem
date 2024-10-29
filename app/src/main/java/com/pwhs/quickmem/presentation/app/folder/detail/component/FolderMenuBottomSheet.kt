package com.pwhs.quickmem.presentation.app.folder.detail.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.Icons.Outlined
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.IosShare
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Report
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.pwhs.quickmem.presentation.app.study_set.detail.component.ItemMenuBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FolderMenuBottomSheet(
    modifier: Modifier = Modifier,
    onEditFolder: () -> Unit,
    onDeleteFolder: () -> Unit,
    showMoreBottomSheet: Boolean,
    sheetShowMoreState: SheetState,
    onDismissRequest: () -> Unit,
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
                    onClick = onEditFolder,
                    icon = Outlined.Edit,
                    title = "Edit"
                )
                ItemMenuBottomSheet(
                    onClick = {},
                    icon = Default.IosShare,
                    title = "Report study set"
                )
                ItemMenuBottomSheet(
                    onClick = {},
                    icon = Outlined.Report,
                    title = "Report study set"
                )
                ItemMenuBottomSheet(
                    onClick = onDeleteFolder,
                    icon = Default.DeleteOutline,
                    title = "Delete study set",
                    color = Color.Red
                )
            }
        }
    }
}