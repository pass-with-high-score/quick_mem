package com.pwhs.quickmem.presentation.app.home.search_by_subject.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons.AutoMirrored
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults.iconButtonColors
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pwhs.quickmem.R
import com.pwhs.quickmem.ui.theme.QuickMemTheme
import com.pwhs.quickmem.util.gradientBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchStudySetBySubjectTopAppBar(
    modifier: Modifier = Modifier,
    name: String = "",
    description: String = "",
    color: Color,
    onNavigateBack: () -> Unit,
) {
    LargeTopAppBar(
        modifier = modifier.background(color.gradientBackground()),
        colors = topAppBarColors(
            containerColor = Color.Transparent,
        ),
        title = {
            Column {
                Row(
                    modifier = modifier,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = name,
                        style = typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = colorScheme.onSurface,
                            fontSize = 20.sp
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Text(
                    text = description,
                    style = typography.bodyMedium.copy(
                        color = colorScheme.secondary
                    ),
                    maxLines = 2
                )
            }
        },
        expandedHeight = 100.dp,
        collapsedHeight = 56.dp,
        navigationIcon = {
            IconButton(
                onClick = onNavigateBack,
                colors = iconButtonColors(
                    contentColor = colorScheme.onSurface
                )
            ) {
                Icon(
                    imageVector = AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.txt_back),
                )
            }
        }
    )
}

@PreviewLightDark
@Composable
fun TopBarSearchPreview() {
    QuickMemTheme {
        Scaffold(
            topBar = {
                SearchStudySetBySubjectTopAppBar(
                    name = "Anthropology",
                    description = "Agriculture is the study of farming and cultivation of land.",
                    onNavigateBack = {},
                    color = Color(0xFF7f60f9)
                )
            }
        ) {
            Column (
                modifier = Modifier.padding(it)
            ){  }
        }
    }
}