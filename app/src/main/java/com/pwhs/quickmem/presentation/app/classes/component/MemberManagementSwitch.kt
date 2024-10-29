package com.pwhs.quickmem.presentation.app.classes.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MemberManagementSwitch(
    modifier: Modifier = Modifier,
    allowMemberManagement: Boolean = false,
    onAllowMemberManagementChange: (Boolean) -> Unit = {},
    allowSetManagement: Boolean = false,
    onAllowSetManagementChange: (Boolean) -> Unit = {},
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorScheme.surfaceContainerLow
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Allow class members to invite new members",
                    style = typography.bodySmall.copy(
                        color = colorScheme.onSurface.copy(alpha = 0.6f),
                        fontSize = 14.sp
                    ),
                    modifier = Modifier.padding(top = 8.dp, end = 10.dp).weight(1f)
                )
                Switch(
                    checked = allowMemberManagement,
                    onCheckedChange = onAllowMemberManagementChange,
                )
            }

        }
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorScheme.surfaceContainerLow
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Allow class members to add sets",
                    style = typography.bodySmall.copy(
                        color = colorScheme.onSurface.copy(alpha = 0.6f),
                        fontSize = 14.sp
                    ),
                    modifier = Modifier.padding(top = 8.dp, end = 10.dp).weight(1f)
                )
                Switch(
                    checked = allowSetManagement,
                    onCheckedChange = onAllowSetManagementChange,
                )
            }

        }
    }
}
