package com.pwhs.quickmem.presentation.app.classes.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pwhs.quickmem.presentation.app.classes.detail.component.ClassDetailTopAppBar
import com.pwhs.quickmem.presentation.app.classes.detail.folders.FoldersTabScreen
import com.pwhs.quickmem.presentation.app.classes.detail.members.MembersTabScreen
import com.pwhs.quickmem.presentation.app.classes.detail.sets.SetsTabScreen
import com.pwhs.quickmem.presentation.component.LoadingOverlay
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.WelcomeScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination<RootGraph>(
    navArgs = ClassDetailArgs::class
)
fun ClassDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: ClassDetailViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                ClassDetailUiEvent.NavigateToWelcome -> {
                    navigator.navigate(WelcomeScreenDestination) {
                        popUpTo(WelcomeScreenDestination) {
                            inclusive = true
                        }
                    }
                }

                ClassDetailUiEvent.OnJoinClass -> {

                }

                is ClassDetailUiEvent.ShowError -> {

                }
            }
        }
    }

    ClassDetail(
        modifier = modifier,
        code = uiState.joinClassCode,
        onNavigateBack = {
            navigator.navigateUp()
        },
        onMoreClicked = {

        },
        title = uiState.title,
        isLoading = uiState.isLoading,
        description = uiState.description
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassDetail(
    modifier: Modifier = Modifier,
    title: String = "",
    isLoading: Boolean = false,
    description: String = "",
    code: String,
    onNavigateBack: () -> Unit = {},
    onMoreClicked: () -> Unit = {},
    onRefresh: () -> Unit = {},
) {
    var tabIndex by remember { mutableIntStateOf(0) }
    val tabTitles = listOf("SETS", "FOLDERS", "MEMBERS")
    var showMoreBottomSheet by remember { mutableStateOf(false) }
    val sheetShowMoreState = rememberModalBottomSheetState()
    val refreshState = rememberPullToRefreshState()

    Scaffold(
        topBar = {
            ClassDetailTopAppBar(
                title = title,
                onNavigateBack = onNavigateBack,
                onMoreClicked = onMoreClicked,
                modifier = modifier,
                description = description
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .background(androidx.compose.ui.graphics.Color.Transparent)
                .padding(innerPadding)
        ) {
            PullToRefreshBox(
                onRefresh = onRefresh,
                isRefreshing = isLoading,
                state = refreshState
            ) {
                Column {
                    Text(
                        "Code: ${code}",
                        style = typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        modifier= modifier.padding(start = 15.dp)
                    )
                    TabRow(
                        selectedTabIndex = tabIndex,
                        indicator = { tabPositions ->
                            SecondaryIndicator(
                                Modifier.tabIndicatorOffset(tabPositions[tabIndex]),
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
                                            color = if (tabIndex == index) androidx.compose.ui.graphics.Color.Black else androidx.compose.ui.graphics.Color.Gray
                                        )
                                    )
                                },
                                selected = tabIndex == index,
                                onClick = { tabIndex = index },
                                icon = {
                                    when (index) {
                                        ClassDetailEnums.SETS.index -> {}
                                        ClassDetailEnums.FOLDER.index -> {}
                                        ClassDetailEnums.MEMBERS.index -> {}
                                    }
                                }
                            )
                        }
                    }
                    when (tabIndex) {
                        ClassDetailEnums.SETS.index -> SetsTabScreen(

                        )

                        ClassDetailEnums.FOLDER.index -> FoldersTabScreen(

                        )

                        ClassDetailEnums.MEMBERS.index -> MembersTabScreen()
                    }

                }
            }

            LoadingOverlay(
                isLoading = isLoading
            )
        }
    }
}

@Preview
@Composable
private fun ClassDetailScreenPreview() {
    MaterialTheme {
        ClassDetail(code = "123")
    }
}