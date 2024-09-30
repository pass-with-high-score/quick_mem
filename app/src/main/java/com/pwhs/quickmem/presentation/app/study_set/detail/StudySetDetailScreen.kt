package com.pwhs.quickmem.presentation.app.study_set.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.IosShare
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.pwhs.quickmem.util.gradientBackground
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph

@Destination<RootGraph>(
    navArgs = StudySetDetailArgs::class
)
@Composable
fun StudySetDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: StudySetDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    StudySetDetail(
        modifier = modifier,
        onNavigateBack = { },
        title = uiState.title
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudySetDetail(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit = {},
    title: String = ""
) {
    Scaffold(
        modifier = modifier.gradientBackground(),
        containerColor = colorScheme.surface,
        topBar = {
            LargeTopAppBar(
                title = { Text(title) },
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
                        onClick = { }
                    ) {
                        Icon(
                            imageVector = Icons.Default.IosShare,
                            contentDescription = "More"
                        )
                    }
                    IconButton(
                        onClick = { }
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "More"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .background(Color.Transparent)
                .padding(innerPadding)

        ) {

        }
    }
}

@Preview(showBackground = true)
@Composable
fun StudySetDetailScreenPreview() {
    StudySetDetail()
}