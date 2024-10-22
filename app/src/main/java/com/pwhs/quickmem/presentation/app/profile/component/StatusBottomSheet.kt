package com.pwhs.quickmem.presentation.app.profile.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.pwhs.quickmem.R
import com.pwhs.quickmem.domain.model.status.StatusModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatusBottomSheet(
    modifier: Modifier = Modifier,
    showBottomSheet: Boolean,
    sheetState: SheetState,
    statuses: List<StatusModel>,
    onStatusSelected: (StatusModel) -> Unit,
    onDismissRequest: () -> Unit
) {
    if (showBottomSheet) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = onDismissRequest,
            containerColor = Color.White,
            modifier = modifier
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    "Select Status",
                    style = typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                )
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                ) {
                    items(statuses) { status ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp)
                                .clickable {
                                    onStatusSelected(status)
                                },
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                painter = painterResource(id = status.iconRes ?: R.drawable.ic_school),
                                contentDescription = status.name,
                                tint = status.color ?: Color.Black,
                                modifier = Modifier.size(24.dp)
                            )

                            Text(
                                text = status.name,
                                style = typography.bodyMedium.copy(
                                    color = colorScheme.onSurface,
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 10.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

