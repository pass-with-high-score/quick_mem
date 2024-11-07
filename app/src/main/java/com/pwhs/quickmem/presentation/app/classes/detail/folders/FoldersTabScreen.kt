package com.pwhs.quickmem.presentation.app.classes.detail.folders

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pwhs.quickmem.domain.model.folder.GetFolderResponseModel
import com.pwhs.quickmem.presentation.app.classes.detail.component.ClassDetailEmpty
import com.pwhs.quickmem.presentation.app.library.folder.component.FolderItem
import com.pwhs.quickmem.ui.theme.QuickMemTheme

@Composable
fun FoldersTabScreen(
    modifier: Modifier = Modifier,
    folder: List<GetFolderResponseModel> = emptyList(),
    onAddFoldersClicked: () -> Unit = {},
    onFolderItemClicked: (GetFolderResponseModel) -> Unit = {},
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
                        title = "This class has no folders",
                        subtitle = "Add folders to share them with your class.",
                        buttonTitle = "Add Folders",
                        onAddMembersClicked = onAddFoldersClicked
                    )
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(folder) { folders ->
                            FolderItem(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                title = folders.title,
                                onClick = { onFolderItemClicked(folders) },
                                numOfStudySets = folders.studySetCount,
                                userResponseModel = folders.owner
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
        FoldersTabScreen()
    }
}