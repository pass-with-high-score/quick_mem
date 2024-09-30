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
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pwhs.quickmem.R

@Composable
fun FolderSections(
    modifier: Modifier,
    onSaveFolder: () -> Unit = {},
    onViewAllFolder: () -> Unit = {},
    onDetailFolder: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Folders",
                style = typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            )
            Text(
                text = "View more",
                style = typography.bodyMedium.copy(
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
        Spacer(modifier = Modifier.height(10.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(folderList) { folder ->
                FolderCard(
                    folderInfo = folder,
                    imgFolder = R.drawable.ic_folder,
                    imgAvt = R.drawable.ic_bear
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