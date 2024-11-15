package com.pwhs.quickmem.presentation.app.notification.component

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pwhs.quickmem.R
import com.pwhs.quickmem.domain.model.notification.GetNotificationResponseModel

@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
fun NotificationItem(
    notification: GetNotificationResponseModel,
    isSelected: Boolean,
    onSelected: (String) -> Unit,
    onDelete: () -> Unit,
    onMarkAsRead: () -> Unit,
) {
    var swipeOffset by remember { mutableFloatStateOf(0f) }
    val animatedOffset by animateFloatAsState(
        targetValue = if (isSelected) swipeOffset else 0f,
        animationSpec = tween(durationMillis = 300),
        label = "Delete"
    )

    val backgroundColor = if (notification.isRead) {
        MaterialTheme.colorScheme.surface
    } else {
        MaterialTheme.colorScheme.background
    }

    val textColor = if (notification.isRead) {
        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
    } else {
        MaterialTheme.colorScheme.onSurface
    }

    val timeAgo = notification.createdAt.calculateTimeAgo(onDelete)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(Color.Red)
            .clickable {
                onDelete()
                swipeOffset = 0f
            }
    ) {
        Text(
            text = "Delete",
            color = MaterialTheme.colorScheme.surface,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 25.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .offset(x = animatedOffset.dp)
                .background(backgroundColor)
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = { onSelected(notification.id) },
                        onDrag = { _, dragAmount ->
                            swipeOffset = (swipeOffset + dragAmount.x).coerceIn(-100f, 0f)
                        },
                        onDragEnd = {
                            swipeOffset = if (swipeOffset < -50f) {
                                -100f
                            } else {
                                0f
                            }
                        }
                    )
                }
                .clickable { onMarkAsRead() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
            ) {
                Text(
                    text = notification.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )
                Text(
                    text = if (timeAgo != "N/A") "${notification.message}  $timeAgo" else "Invalid date format",
                    fontSize = 14.sp,
                    color = textColor
                )
            }

            if (!notification.isRead) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_circle),
                    contentDescription = "Unread",
                    modifier = Modifier
                        .size(24.dp)
                        .padding(end = 16.dp)
                )
            }
        }
    }
}
