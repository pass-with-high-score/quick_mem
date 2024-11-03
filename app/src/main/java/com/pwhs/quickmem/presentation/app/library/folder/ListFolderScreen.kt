package com.pwhs.quickmem.presentation.app.library.folder

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pwhs.quickmem.domain.model.folder.GetFolderResponseModel

@Composable
fun ListFolderScreen(
    modifier: Modifier = Modifier,
    folders: List<GetFolderResponseModel> = emptyList(),
    onFolderClick: (String) -> Unit = {}
) {
    Scaffold(
        modifier = modifier
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding)
        ) {
            items(folders) { folder ->
                Card(
                    onClick = { onFolderClick(folder.id) }
                ) {
                    Column(
                        modifier = Modifier.padding(innerPadding)
                    ) { Text(text = folder.title) }
                }
            }
        }
    }
}