package com.pwhs.quickmem.presentation.app.explore.top_streak

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pwhs.quickmem.domain.model.streak.GetTopStreakResponseModel
import com.pwhs.quickmem.presentation.app.explore.top_streak.component.StreakItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopStreakScreen(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    topStreaks: List<GetTopStreakResponseModel> = emptyList(),
    onTopStreakRefresh: () -> Unit = {}
) {
    val refreshState = rememberPullToRefreshState()

    PullToRefreshBox(
        modifier = Modifier.fillMaxSize(),
        state = refreshState,
        isRefreshing = isLoading,
        onRefresh = {
            onTopStreakRefresh()
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(
                    text = "Bạn đã đạt vị trí top #1 trên Top Streak!",
                    style = typography.titleLarge.copy(
                        color = colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Center
                )
            }
            items(topStreaks.size) { index ->
                val topStreak = topStreaks[index]
                StreakItem(
                    rank = index + 1,
                    avatarUrl = topStreak.avatarUrl,
                    username = topStreak.username,
                    streakCount = topStreak.streakCount
                )
            }
        }
    }
}

@Preview
@Composable
private fun TopStreakScreenPreview() {
    MaterialTheme {
        TopStreakScreen()
    }
}