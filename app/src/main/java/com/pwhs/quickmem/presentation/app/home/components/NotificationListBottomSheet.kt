package com.pwhs.quickmem.presentation.app.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pwhs.quickmem.R
import com.pwhs.quickmem.domain.model.notification.GetNotificationResponseModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationListBottomSheet(
    onDismissRequest: () -> Unit,
    sheetState: SheetState,
    notifications: List<GetNotificationResponseModel>,
    onNotificationClicked: (String) -> Unit,
) {

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = colorScheme.surface,
        dragHandle = {}
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxHeight(0.95f)
        ) {
            Text(
                text = stringResource(R.string.txt_activity),
                style = typography.titleLarge.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorScheme.onSurface,
                ),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 16.dp)
            )
            when {
                notifications.isEmpty() -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.restriction),
                            contentDescription = stringResource(R.string.txt_no_notifications),
                            modifier = Modifier.size(120.dp),
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            text = stringResource(R.string.txt_there_are_no_notifications),
                            style = typography.bodyMedium.copy(
                                color = colorScheme.onSurface,
                            ),
                        )
                    }
                }

                else -> {
                    LazyColumn {
                        items(notifications, key = { it.id }) { notification ->
                            NotificationItem(
                                notification = notification,
                                onMarkAsRead = {
                                    onNotificationClicked(notification.id)
                                }
                            )
                            HorizontalDivider(
                                modifier = Modifier.padding(horizontal = 10.dp),
                                thickness = 1.dp,
                                color = colorScheme.onSurface.copy(alpha = 0.2f)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(60.dp))
                }
            }
        }
    }
}
