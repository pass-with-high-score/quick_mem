package com.pwhs.quickmem.presentation.app.explore.top_streak.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.pwhs.quickmem.R

@Composable
fun StreakItem(
    rank: Int,
    avatarUrl: String,
    username: String,
    streakCount: Int,
    isCurrentUser: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (isCurrentUser) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f) else Color.Transparent)
            .clip(RoundedCornerShape(12.dp))
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier.size(40.dp),
            contentAlignment = Alignment.Center
        ) {
            when (rank) {
                1 -> Image(
                    painter = painterResource(id = R.drawable.top1),
                    contentDescription = "Gold Medal",
                    modifier = Modifier.size(32.dp)
                )

                2 -> Image(
                    painter = painterResource(id = R.drawable.top2),
                    contentDescription = "Silver Medal",
                    modifier = Modifier.size(32.dp)
                )

                3 -> Image(
                    painter = painterResource(id = R.drawable.top3),
                    contentDescription = "Bronze Medal",
                    modifier = Modifier.size(32.dp)
                )

                else -> Text(
                    text = rank.toString(),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    ),
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp
                )
            }
        }

        AsyncImage(
            model = avatarUrl,
            contentDescription = "User Avatar",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        )

        Text(
            text = username,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            fontSize = 16.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )

        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$streakCount ",
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.primary
            )
            Column(
                modifier = Modifier.padding(bottom = 6.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_fire),
                    contentDescription = stringResource(R.string.txt_streak),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(20.dp)
                )
            }
        }
    }
}
