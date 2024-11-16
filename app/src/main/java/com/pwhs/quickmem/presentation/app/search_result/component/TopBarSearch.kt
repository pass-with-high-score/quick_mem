package com.pwhs.quickmem.presentation.app.search_result.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons.AutoMirrored
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults.iconButtonColors
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.sp
import com.pwhs.quickmem.R
import com.pwhs.quickmem.ui.theme.QuickMemTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarSearchResult(
    modifier: Modifier = Modifier,
    title: String,
    onNavigateBack: () -> Unit,
    onClickFilter: () -> Unit = {},
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        colors = topAppBarColors(
            containerColor = Color.Transparent,
        ),
        title = {
            Text(
                text = title,
                style = typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = colorScheme.onSurface,
                    fontSize = 20.sp
                )
            )
        },
        actions = {
            IconButton(
                onClick = onClickFilter,
                colors = iconButtonColors(
                    contentColor = colorScheme.onSurface
                )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_filter),
                    contentDescription = "Filter",
                )
            }
        },
        navigationIcon = {
            IconButton(
                onClick = onNavigateBack,
                colors = iconButtonColors(
                    contentColor = colorScheme.onSurface
                )
            ) {
                Icon(
                    imageVector = AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                )
            }
        }
    )
}

@PreviewLightDark
@Composable
fun CreateTopAppBarPreview() {
    QuickMemTheme {
        Scaffold(
            topBar = {
                TopBarSearchResult(
                    title = "Result",
                    onNavigateBack = {},
                )
            }
        ) {
            Column (
                modifier = Modifier.padding(it)
            ){  }
        }
    }
}