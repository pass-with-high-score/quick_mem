package com.pwhs.quickmem.presentation.app.notification

import androidx.compose.ui.graphics.Color
import com.pwhs.quickmem.presentation.app.notification.component.NotificationItem
import com.pwhs.quickmem.presentation.app.notification.component.calculateTimeAgo
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pwhs.quickmem.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationBottomSheet(
    userId: String,
    onDismissRequest: () -> Unit,
    viewModel: NotificationViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val modalBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    var selectedItemId by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(userId) {
        viewModel.handleAction(NotificationUiAction.SetArgs(userId))
        modalBottomSheetState.show()
    }

    BoxWithConstraints {
        val screenHeight = maxHeight
        val desiredHeight = screenHeight * 0.95f

        ModalBottomSheet(
            onDismissRequest = onDismissRequest,
            sheetState = modalBottomSheetState,
            containerColor = MaterialTheme.colorScheme.surface,
            modifier = Modifier
                .fillMaxWidth()
                .height(desiredHeight)
                .align(Alignment.BottomCenter)
        ) {
            Scaffold(
                snackbarHost = { SnackbarHost(snackBarHostState) },
                modifier = Modifier.fillMaxSize()
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(10.dp)
                    ) {
                        Text(
                            text = "Activity",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )

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
                                        .fillMaxWidth()
                                        .weight(1f)
                                        .padding(bottom = 16.dp)
                                ) {
                                    items(uiState.notifications.filter {
                                        it.createdAt.calculateTimeAgo {
                                            viewModel.handleAction(
                                                NotificationUiAction.DeleteNotification(
                                                    it.id
                                                )
                                            )
                                        } != "Expired"
                                    }, key = { it.id }) { notification ->
                                        NotificationItem(
                                            notification = notification,
                                            isSelected = notification.id == selectedItemId,
                                            onSelected = { id -> selectedItemId = id },
                                            onDelete = {
                                                viewModel.handleAction(
                                                    NotificationUiAction.DeleteNotification(
                                                        notification.id
                                                    )
                                                )
                                                selectedItemId = null
                                                coroutineScope.launch {
                                                    snackBarHostState.showSnackbar("Notification deleted successfully!")
                                                }
                                            },
                                            onMarkAsRead = {
                                                viewModel.handleAction(
                                                    NotificationUiAction.MarkAsRead(
                                                        notification.id
                                                    )
                                                )
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
}
