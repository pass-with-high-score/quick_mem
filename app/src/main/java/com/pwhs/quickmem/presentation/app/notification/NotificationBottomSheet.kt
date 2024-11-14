package com.pwhs.quickmem.presentation.app.notification

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import java.time.Duration
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

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

    val snackBarHostState = remember { SnackbarHostState() }

    var selectedItemId by remember { mutableStateOf<String?>(null) }

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
        Scaffold(
            snackbarHost = { SnackbarHost(snackBarHostState) },
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
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
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(bottom = 16.dp)
                            ) {
                                items(uiState.notifications.filter {
                                    calculateTimeAgo(it.createdAt, onDelete = {
                                        viewModel.handleAction(NotificationUiAction.DeleteNotification(it.id))
                                        coroutineScope.launch {
                                            snackBarHostState.showSnackbar("Notification deleted successfully!")
                                        }
                                    }) != "Expired"
                                }, key = { it.id }) { notification ->
                                    NotificationItem(
                                        notification = notification,
                                        selectedItemId = selectedItemId,
                                        onSelected = { id -> selectedItemId = id },
                                        onDelete = {
                                            viewModel.handleAction(NotificationUiAction.DeleteNotification(notification.id))
                                            selectedItemId = null
                                        },
                                        onMarkAsRead = {
                                            viewModel.handleAction(NotificationUiAction.MarkAsRead(notification.id))
                                        }
                                    )
                                    HorizontalDivider(
                                        modifier = Modifier.padding(horizontal = 10.dp),
                                        thickness = 1.dp,
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                                    )
                                }

                                item {
                                    Spacer(modifier = Modifier.height(60.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


fun calculateTimeAgo(createdAt: String, onDelete: () -> Unit): String {
    return try {
        val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
        val createdDateTime = OffsetDateTime.parse(createdAt, formatter)
        val now = OffsetDateTime.now()
        val duration = Duration.between(createdDateTime, now)

        if (duration.toDays() > 14) {
            onDelete()
            return "Expired"
        } else {
            when {
                duration.toMinutes() < 1 -> "${duration.seconds}s"
                duration.toHours() < 1 -> "${duration.toMinutes()}m"
                duration.toDays() < 1 -> "${duration.toHours()}h"
                else -> "${duration.toDays()}d"
            }
        }
    } catch (e: Exception) {
        "N/A"
    }
}

@SuppressLint("UseOfNonLambdaOffsetOverload", "AutoboxingStateCreation")
@Composable
fun NotificationItem(
    notification: GetNotificationResponseModel,
    selectedItemId: String?,
    onSelected: (String) -> Unit,
    onDelete: () -> Unit,
    onMarkAsRead: () -> Unit,
) {
    var swipeOffset by remember { mutableStateOf(0f) }
    val animatedOffset by animateFloatAsState(
        targetValue = if (selectedItemId == notification.id) swipeOffset else 0f,
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

    val timeAgo = calculateTimeAgo(notification.createdAt, onDelete)

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
                    text = "${notification.message}  $timeAgo",
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
