package com.pwhs.quickmem.presentation.app.explore

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.pwhs.quickmem.domain.model.streak.GetTopStreakResponseModel
import com.pwhs.quickmem.presentation.app.explore.ai_chat.AIChatScreen
import com.pwhs.quickmem.presentation.app.explore.top_streak.TopStreakScreen
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph

@Composable
@Destination<RootGraph>
fun ExploreScreen(
    modifier: Modifier = Modifier,
    viewModel: ExploreViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is ExploreUiEvent.Error -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Explore(
        modifier = modifier,
        isLoading = uiState.isLoading,
        topStreaks = uiState.topStreaks,
        rankOwner = uiState.rankOwner,
        streakOwner = uiState.streakOwner,
        onTopStreakRefresh = { viewModel.onEvent(ExploreUiAction.RefreshTopStreaks) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Explore(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    rankOwner: Int? = null,
    streakOwner: GetTopStreakResponseModel? = null,
    topStreaks: List<GetTopStreakResponseModel> = emptyList(),
    onTopStreakRefresh: () -> Unit = {}
) {
    var tabIndex by remember { mutableIntStateOf(0) }
    val tabTitles = listOf(
        "Top Streak",
        "AI Chat"
    )

    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Explore",
                        style = typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                actions = {

                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier.padding(innerPadding),
        ) {
            TabRow(
                selectedTabIndex = tabIndex,
                indicator = { tabPositions ->
                    SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[tabIndex]),
                        color = colorScheme.primary,
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
                    )
                }
            }
            when (tabIndex) {
                ExploreTabEnum.TOP_STREAK.index -> TopStreakScreen(
                    isLoading = isLoading,
                    rankOwner = rankOwner,
                    topStreaks = topStreaks,
                    streakOwner = streakOwner,
                    onTopStreakRefresh = onTopStreakRefresh,
                )

                ExploreTabEnum.AI_CHAT.index -> AIChatScreen()
            }
        }
    }
}

@Preview
@Composable
private fun ExploreScreenPreview() {
    MaterialTheme {
        Explore()
    }
}