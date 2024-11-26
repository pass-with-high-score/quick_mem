package com.pwhs.quickmem.presentation.app.explore.top_streak.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.pwhs.quickmem.R

@Composable
fun StreakItem(
    rank: Int,
    avatarUrl: String,
    username: String,
    streakCount: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier.size(40.dp),
            contentAlignment = Alignment.Center
        ) {
            when (rank) {
                1 -> Image(
                    painter = painterResource(id = R.drawable.top1),
                    contentDescription = "Gold Medal",
                    modifier = Modifier.size(40.dp)
                )
                2 -> Image(
                    painter = painterResource(id = R.drawable.top2),
                    contentDescription = "Silver Medal",
                    modifier = Modifier.size(40.dp)
                )
                3 -> Image(
                    painter = painterResource(id = R.drawable.top3),
                    contentDescription = "Bronze Medal",
                    modifier = Modifier.size(40.dp)
                )
                else -> Text(
                    text = rank.toString(),
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    textAlign = TextAlign.Center,
                    color = Color.Gray,
                    fontSize = 16.sp
                )
            }
        }

        // Avatar
        AsyncImage(
            model = avatarUrl,
            contentDescription = stringResource(R.string.txt_user_avatar),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(18.dp)
                .clip(CircleShape)
        )

        // Username
        Text(
            text = username,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f),
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )

        // Streak Count
        Text(
            text = "$streakCount ST",
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}