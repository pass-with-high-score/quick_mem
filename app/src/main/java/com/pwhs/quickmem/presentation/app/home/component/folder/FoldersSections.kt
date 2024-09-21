package com.pwhs.quickmem.presentation.app.home.component.folder

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FoldersSections() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 15.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Folders",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            )
            Text(
                text = "View more",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.Blue,
                    fontSize = 14.sp
                ),
                modifier = Modifier
                    .padding(end = 18.dp)
                    .clickable {
                        // Xử lý khi nhấn "View more"
                    }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(end = 16.dp, start = 15.dp)
        ) {
            items(folderList) { folder ->
                FolderCard(
                    title = folder.title,
                    sets = folder.sets,
                    author = folder.author
                )
            }
        }
    }
}

data class FolderInfo(
    val title: String,
    val sets: String,
    val author: String
)

val folderList = listOf(
    FolderInfo("Animal", "2 sets", "Hadao1204"),
    FolderInfo("Plants", "3 sets", "Hana1210"),
    FolderInfo("Physics", "5 sets", "Thinguyen")
)