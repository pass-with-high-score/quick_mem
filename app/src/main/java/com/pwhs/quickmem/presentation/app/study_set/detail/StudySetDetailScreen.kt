package com.pwhs.quickmem.presentation.app.study_set.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.IosShare
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pwhs.quickmem.presentation.app.study_set.detail.material.MaterialTabScreen
import com.pwhs.quickmem.presentation.app.study_set.detail.progress.ProgressTabScreen
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
        title = uiState.title,
        color = uiState.color
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudySetDetail(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit = {},
    title: String = "",
    color: Color = Color.Blue
) {
    var tabIndex by remember { mutableIntStateOf(0) }
    val tabTitles = listOf("Material", "Progress")
    var showMoreBottomSheet by remember { mutableStateOf(false) }
    val sheetShowMoreState = rememberModalBottomSheetState()
    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(
                        title, style = typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = colorScheme.onSurface
                        )
                    )
                },
                modifier = modifier.background(color.gradientBackground()),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                ),
                expandedHeight = 120.dp,
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
                        onClick = { }
                    ) {
                        Icon(
                            imageVector = Icons.Default.IosShare,
                            contentDescription = "Share"
                        )
                    }
                    IconButton(
                        onClick = { }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add"
                        )
                    }
                    IconButton(
                        onClick = {
                            showMoreBottomSheet = true
                        }
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
            TabRow(
                selectedTabIndex = tabIndex,
                indicator = { tabPositions ->
                    SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[tabIndex]),
                        color = color,
                    )
                },
                contentColor = colorScheme.onSurface,
            ) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        text = {
                            Text(
                                title, style = typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = if (tabIndex == index) Color.Black else Color.Gray
                                )
                            )
                        },
                        selected = tabIndex == index,
                        onClick = { tabIndex = index },
                        icon = {
                            when (index) {
                                0 -> {}
                                1 -> {}
                            }
                        }
                    )
                }
            }
            when (tabIndex) {
                0 -> MaterialTabScreen()
                1 -> ProgressTabScreen()
            }
        }
    }

    if (showMoreBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showMoreBottomSheet = false },
            sheetState = sheetShowMoreState
        ) {
            Column {
                Row {
                    Text("Edit")
                }
                Row {
                    Text("Delete")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StudySetDetailScreenPreview() {
    StudySetDetail()
}