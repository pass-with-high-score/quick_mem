package com.pwhs.quickmem.presentation.app.settings.about.open_source_licenses.component

import androidx.compose.material.icons.Icons.AutoMirrored
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults.iconButtonColors
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.pwhs.quickmem.ui.theme.QuickMemTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OpenSourceTopAppBar(
    modifier: Modifier = Modifier,
    title: String = "",
    onNavigateBack: () -> Unit = {},
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                title, style = typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = colorScheme.onSurface
                ),
                overflow = TextOverflow.Ellipsis
            )
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
        },
        colors = topAppBarColors(
            containerColor = Color.Transparent,
        )
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewOpenSourceTopBar(){
    QuickMemTheme {
        OpenSourceTopAppBar(
            title = "Open Source Licenses"
        )
    }
}