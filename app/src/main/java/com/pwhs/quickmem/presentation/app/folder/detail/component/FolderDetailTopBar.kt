package com.pwhs.quickmem.presentation.app.folder.detail.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pwhs.quickmem.R
import com.pwhs.quickmem.presentation.app.folder.detail.SortOptionEnum

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FolderDetailTopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    dateLabel: String,
    onNavigateBack: () -> Unit,
    onMoreClicked: () -> Unit,
    onAddStudySet: () -> Unit,
    onStudyFolderDetailClicked: () -> Unit,
    currentSortOption: SortOptionEnum,
    onSortOptionClicked: () -> Unit
) {
    LargeTopAppBar(
        title = {
            Column(
                modifier = Modifier.padding(end = 16.dp)
            ) {
                Text(
                    title, style = typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = colorScheme.onSurface
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    modifier = Modifier.padding(top = 8.dp, bottom = 26.dp),
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_history),
                        contentDescription = null,
                        modifier = Modifier.size(22.dp),
                        tint = Color.Black
                    )
                    Text(
                        dateLabel, style = typography.bodyMedium.copy(
                            color = colorScheme.onSurface,
                        ),
                        modifier = Modifier.padding(start = 8.dp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                ButtonFolder(
                    title = "Study",
                    onFolderClicked = onStudyFolderDetailClicked
                )
                FolderSortOptionTextButton(
                    currentSortOption = currentSortOption,
                    onSortOptionClicked = onSortOptionClicked
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
        ),
        expandedHeight = 240.dp,
        collapsedHeight = 56.dp,
        navigationIcon = {
            IconButton(
                onClick = onNavigateBack,
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = colorScheme.onSurface
                )
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                )
            }
        },
        actions = {
            IconButton(
                onClick = onAddStudySet
            ) {
                Icon(
                    imageVector = Default.Add,
                    contentDescription = "Add"
                )
            }
            IconButton(
                onClick = onMoreClicked
            ) {
                Icon(
                    imageVector = Default.MoreVert,
                    contentDescription = "More"
                )
            }
        }
    )
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Composable
fun FolderDetailTopAppBarPreview() {
    FolderDetailTopAppBar(
        title = "Folder Title",
        dateLabel = "Date Label",
        onNavigateBack = {},
        onMoreClicked = {},
        onAddStudySet = {},
        onStudyFolderDetailClicked = {},
        currentSortOption = SortOptionEnum.RECENT,
        onSortOptionClicked = {}
    )
}