package com.pwhs.quickmem.presentation.app.explore.top_streak

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import com.pwhs.quickmem.ui.theme.borderTopStreakTextColor
import com.pwhs.quickmem.ui.theme.premiumColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopStreakScreen(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    topStreaks: List<GetTopStreakResponseModel> = emptyList(),
    onTopStreakRefresh: () -> Unit = {}
) {
    val refreshState = rememberPullToRefreshState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
    ) { innerPadding ->
        PullToRefreshBox(
            modifier = Modifier.fillMaxSize(),
            state = refreshState,
            isRefreshing = isLoading,
            onRefresh = {
                onTopStreakRefresh()
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Bạn đã đạt vị trí top #1 trên Top Streak!",
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = premiumColor,
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                LazyColumn(
                    modifier = Modifier
                        .padding(12.dp)
                        .border(2.dp, borderTopStreakTextColor, RoundedCornerShape(16.dp))
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
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
    }
}


@Preview
@Composable
private fun TopStreakScreenPreview() {
    MaterialTheme {
        TopStreakScreen()
    }
}