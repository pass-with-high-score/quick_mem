package com.pwhs.quickmem.presentation.app.profile.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * A top bar component for the profile screen.
 * Note: Uses experimental Material3 API which may change in future releases.
 *
 * @param onUpgradeClick Callback when upgrade button is clicked
 * @param backgroundColor Background color of the top bar, defaults to theme background
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileTopBar(
    onUpgradeClick: () -> Unit,
    backgroundColor: Color = MaterialTheme.colorScheme.background
) {
    TopAppBar(
        title = {},
        actions = {
            Button(
                onClick = onUpgradeClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier.padding(end = 10.dp)
            ) {
                Text(
                    text = "Try premium",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = backgroundColor
        )
    )
}

