package com.pwhs.quickmem.presentation.app.library.folder

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pwhs.quickmem.domain.model.folder.GetFolderResponseModel
import com.pwhs.quickmem.presentation.ads.BannerAds
import com.pwhs.quickmem.presentation.app.library.folder.component.FolderItem
import com.pwhs.quickmem.ui.theme.QuickMemTheme

@Composable
fun ListFolderScreen(
    modifier: Modifier = Modifier,
    folders: List<GetFolderResponseModel> = emptyList(),
    onFolderClick: (String) -> Unit = {},
    onAddFolderClick: () -> Unit = {}
) {
    Scaffold(
        modifier = modifier
    ) { innerPadding ->
        when (folders.isEmpty()) {
            true -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(top = 40.dp)
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Folder,
                        contentDescription = "Folder",
                        modifier = Modifier.size(60.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Organize your study sets in folders by subject, teacher, course, or any other way you like.",
                        style = typography.bodyLarge.copy(
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center
                        ),
                    )
                    Button(
                        shape = MaterialTheme.shapes.medium,
                        onClick = onAddFolderClick,
                        modifier = Modifier
                            .width(150.dp)
                    ) {
                        Text(
                            "Create a folder",
                            style = typography.bodyMedium.copy(
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                }
            }

            false -> {
                LazyColumn(
                    modifier = Modifier.padding(innerPadding)
                ) {
                    items(folders) { folder ->
                        FolderItem(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            title = folder.title,
                            numOfStudySets = folder.studySetCount,
                            onClick = { onFolderClick(folder.id) },
                            userResponseModel = folder.user
                        )
                    }
                    item {
                        BannerAds(
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.padding(60.dp))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ListFolderScreenPreview() {
    QuickMemTheme {
        ListFolderScreen()
    }

}