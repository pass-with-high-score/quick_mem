package com.pwhs.quickmem.presentation.app.classes.detail.folders

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pwhs.quickmem.R
import com.pwhs.quickmem.domain.model.folder.GetFolderResponseModel
import com.pwhs.quickmem.presentation.app.classes.detail.component.ClassDetailEmpty
import com.pwhs.quickmem.presentation.app.library.folder.component.FolderItem
import com.pwhs.quickmem.ui.theme.QuickMemTheme

@Composable
fun FoldersTabScreen(
    modifier: Modifier = Modifier,
    isOwner: Boolean,
    folder: List<GetFolderResponseModel> = emptyList(),
    onAddFoldersClicked: () -> Unit = {},
    onFolderItemClicked: (GetFolderResponseModel) -> Unit = {},
    onDeleteFolderClicked: (String) -> Unit = {}
) {
    Scaffold { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
        ) {
            when {
                folder.isEmpty() -> {
                    ClassDetailEmpty(
                        modifier = Modifier.padding(innerPadding),
                        title = stringResource(R.string.txt_this_class_has_no_folders),
                        subtitle = stringResource(R.string.txt_add_folders_to_share_them_with_your_class),
                        buttonTitle = stringResource(R.string.txt_add_folders),
                        onAddClick = onAddFoldersClicked,
                        isOwner = isOwner
                    )
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize(),
                    ) {
                        items(items = folder, key = {it.id}) { folders ->
                            FolderItem(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                title = folders.title,
                                onClick = { onFolderItemClicked(folders) },
                                numOfStudySets = folders.studySetCount,
                                userResponseModel = folders.owner,
                                onDeleteClick = {folderId ->
                                    onDeleteFolderClicked(folderId)
                                },
                                folder = folders,
                                isOwner = isOwner
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun FoldersTabScreenPreview() {
    QuickMemTheme {
        FoldersTabScreen(isOwner = false)
    }
}