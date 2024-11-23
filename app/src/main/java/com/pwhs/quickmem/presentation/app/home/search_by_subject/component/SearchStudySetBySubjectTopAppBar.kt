package com.pwhs.quickmem.presentation.app.home.search_by_subject.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.Icons.AutoMirrored
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.ui.res.painterResource
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
    studySetCount: Int = 0,
    @DrawableRes icon: Int = R.drawable.ic_all,
    onNavigateBack: () -> Unit,
    onAddStudySet: () -> Unit = {}
) {
    LargeTopAppBar(
        modifier = modifier.background(color.gradientBackground()),
        colors = topAppBarColors(
            containerColor = Color.Transparent,
        ),
        title = {
            Column {
                Row(
                    modifier = Modifier.padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = null,
                        tint = colorScheme.onSurface,
                        modifier = Modifier
                            .size(30.dp)
                            .padding(end = 8.dp)
                    )
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
                    text = "$studySetCount sets",
                    style = typography.bodyMedium.copy(
                        color = colorScheme.secondary
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = description,
                    style = typography.bodyMedium.copy(
                        color = colorScheme.secondary
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        expandedHeight = 150.dp,
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
        },
        actions = {
            IconButton(
                onClick = onAddStudySet,
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.txt_add_study_set),
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
            Column(
                modifier = Modifier.padding(it)
            ) { }
        }
    }
}