@file:Suppress("UNUSED_EXPRESSION")

package com.pwhs.quickmem.presentation.app.user_detail

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pwhs.quickmem.presentation.app.user_detail.component.UserDetail
import com.pwhs.quickmem.ui.theme.QuickMemTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<RootGraph>(navArgs = UserDetailArgs::class)
@Composable
fun UserDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: UserDetailViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                else -> {}
            }
        }
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        UserDetail(
            modifier = Modifier.fillMaxWidth(),
            isLoading = uiState.isLoading,
            userName = uiState.userName,
            avatarUrl = uiState.avatarUrl,
            onBackClick = { navigator.popBackStack() }
        )
    }
}

@Preview
@Composable
private fun UserDetailPreview() {
    QuickMemTheme {
        UserDetail(
            userName = "John Doe",
            avatarUrl = "https://example.com/avatar.jpg",
            onBackClick = {}
        )
    }
}
