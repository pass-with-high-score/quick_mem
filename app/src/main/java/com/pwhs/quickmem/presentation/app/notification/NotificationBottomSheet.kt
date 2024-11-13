package com.pwhs.quickmem.presentation.app.notification

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
import androidx.hilt.navigation.compose.hiltViewModel
import com.pwhs.quickmem.R
import com.pwhs.quickmem.domain.model.notification.GetNotificationResponseModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationBottomSheet(
    userId: String,
    onDismissRequest: () -> Unit,
    viewModel: NotificationViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val modalBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(userId) {
        viewModel.setArgs(NotificationArgs(userId))
        coroutineScope.launch {
            modalBottomSheetState.show()
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = modalBottomSheetState,
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.95f)
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                IconButton(
                    onClick = onDismissRequest,
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_close),
                        contentDescription = "Close",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }

                Text(
                    text = "Activity",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            when {
                uiState.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
                    }
                }
                uiState.error != null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = uiState.error ?: "An error occurred",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
                uiState.notifications.isEmpty() -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.restriction),
                            contentDescription = "No Notifications",
                            modifier = Modifier.size(120.dp),
                            tint = Color.Unspecified
                        )
                        Spacer(modifier = Modifier.height(21.dp))
                        Text(
                            text = "There are no notifications",
                            fontSize = 18.sp,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                }
                else -> {
                    uiState.notifications.forEach { notification ->
                        NotificationItem(
                            notification = notification,
                            onDelete = {
                                viewModel.handleAction(NotificationUiAction.DeleteNotification(notification.id))
                            },
                            onMarkAsRead = {
                                viewModel.handleAction(NotificationUiAction.MarkAsRead(notification.id))
                            }
                        )
                    }
                }
            }
        }
    }
}

@SuppressLint("UseOfNonLambdaOffsetOverload", "AutoboxingStateCreation")
@Composable
fun NotificationItem(
    notification: GetNotificationResponseModel,
    onDelete: () -> Unit,
    onMarkAsRead: () -> Unit,
) {
    val swipeOffset = remember { mutableStateOf(0f) }
    val animatedOffset by animateFloatAsState(
        targetValue = swipeOffset.value,
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

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(Color.Red)
            .clickable {
                onDelete()
                swipeOffset.value = 0f
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
                .drag(
                    onDrag = { deltaX ->
                        swipeOffset.value = (swipeOffset.value + deltaX).coerceIn(-100f, 0f)
                    },
                    onDragEnd = {
                        if (swipeOffset.value < -50f) {
                            swipeOffset.value = -100f
                        } else {
                            swipeOffset.value = 0f
                        }
                    }
                )
                .clickable { onMarkAsRead() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (!notification.isRead) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_circle),
                    contentDescription = "Unread",
                    modifier = Modifier
                        .size(24.dp)
                        .padding(start = 16.dp)
                )
            }

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
                    text = notification.message,
                    fontSize = 14.sp,
                    color = textColor
                )
            }
        }
    }
}

private fun Modifier.drag(
    onDrag: (Float) -> Unit,
    onDragEnd: () -> Unit
) = pointerInput(Unit) {
    detectDragGestures(
        onDragEnd = { onDragEnd() },
        onDrag = { _, dragAmount -> onDrag(dragAmount.x) }
    )
}
