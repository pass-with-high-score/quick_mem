package com.pwhs.quickmem.presentation.app.folder.detail.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pwhs.quickmem.presentation.app.folder.detail.SortOptionEnum

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FolderSortOptionBottomSheet(
    modifier: Modifier = Modifier,
    onSortOptionClicked: (SortOptionEnum) -> Unit,
    sortOptionBottomSheet: Boolean,
    sheetSortOptionState: SheetState,
    onDismissRequest: () -> Unit,
) {
    if (sortOptionBottomSheet) {
        ModalBottomSheet(
            modifier = modifier,
            onDismissRequest = onDismissRequest,
            sheetState = sheetSortOptionState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                ItemSortOptionBottomSheet(
                    onClick = { onSortOptionClicked(SortOptionEnum.RECENT) },
                    title = SortOptionEnum.RECENT.sortOption
                )
                ItemSortOptionBottomSheet(
                    onClick = { onSortOptionClicked(SortOptionEnum.TITLE) },
                    title = SortOptionEnum.TITLE.sortOption
                )
            }
        }
    }
}