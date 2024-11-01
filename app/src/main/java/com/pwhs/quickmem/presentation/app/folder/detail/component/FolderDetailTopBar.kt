package com.pwhs.quickmem.presentation.app.folder.detail.component

import androidx.compose.foundation.background
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pwhs.quickmem.R
import com.pwhs.quickmem.ui.theme.QuickMemTheme
import com.pwhs.quickmem.util.gradientBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FolderDetailTopAppBar(
    modifier: Modifier = Modifier,
    title: String = "",
    dateLabel: String = "",
    onNavigateBack: () -> Unit = {},
    onMoreClicked: () -> Unit = {},
    onAddStudySet: () -> Unit = {}
) {
    LargeTopAppBar(
        modifier = modifier.background(colorScheme.primary.gradientBackground()),
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
                    modifier = Modifier.padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_history),
                        contentDescription = null,
                        modifier = Modifier.size(22.dp),
                        tint = colorScheme.onSurface
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
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
        ),
        expandedHeight = 140.dp,
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

@Preview(showBackground = true)
@Composable
fun FolderDetailTopAppBarPreview() {
    QuickMemTheme {
        Scaffold(
            topBar = {
                FolderDetailTopAppBar(
                    title = "Folder Title",
                    dateLabel = "Created 2021-09-01",
                    onNavigateBack = {},
                    onMoreClicked = {},
                    onAddStudySet = {}
                )
            }
        ) {
            Column(modifier = Modifier.padding(it)) {
                Text("Content")
            }
        }
    }
}