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
import androidx.hilt.navigation.compose.hiltViewModel
import com.pwhs.quickmem.domain.model.users.UserResponseModel
import com.pwhs.quickmem.presentation.app.classes.detail.component.ClassDetailTopAppBar
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

                ClassDetailUiEvent.OnJoinClass -> TODO()
            }
        }
    }

    ClassDetail(
        modifier = modifier,
        code = uiState.joinClassCode
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassDetail(
    modifier: Modifier = Modifier,
    color: androidx.compose.ui.graphics.Color = androidx.compose.ui.graphics.Color.Blue,
    title: String = "",
    isLoading: Boolean = false,
    setCount: Int = 0,
    code: String,
    onNavigateBack: () -> Unit = {},
    onMoreClicked: () -> Unit = {},
    onRefresh: () -> Unit = {},
) {
    var tabIndex by remember { mutableIntStateOf(0) }
    val tabTitles = listOf("SETS", "MEMBERS")
    var showMoreBottomSheet by remember { mutableStateOf(false) }
    val sheetShowMoreState = rememberModalBottomSheetState()
    val refreshState = rememberPullToRefreshState()

    Scaffold(
        topBar = {
            ClassDetailTopAppBar(
                title = title,
                userResponse = UserResponseModel(),
                color = color,
                onNavigateBack = onNavigateBack,
                onMoreClicked = onMoreClicked,
                modifier = modifier,
                setCount = setCount
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
                    Text("Code: ${code}")
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
                                            color = if (tabIndex == index) androidx.compose.ui.graphics.Color.Black else androidx.compose.ui.graphics.Color.Gray
                                        )
                                    )
                                },
                                selected = tabIndex == index,
                                onClick = { tabIndex = index },
                                icon = {
                                    when (index) {
                                        ClassDetailEnums.SETS.index -> {}
                                        ClassDetailEnums.MEMBERS.index -> {}
                                    }
                                }
                            )
                        }
                    }
                    when (tabIndex) {
                        ClassDetailEnums.SETS.index -> {

                        }

                        ClassDetailEnums.MEMBERS.index -> {

                        }
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