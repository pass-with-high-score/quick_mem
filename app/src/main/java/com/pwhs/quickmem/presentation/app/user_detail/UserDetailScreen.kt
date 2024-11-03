package com.pwhs.quickmem.presentation.app.user_detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.pwhs.quickmem.ui.theme.QuickMemTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<RootGraph>(
    navArgs = UserDetailArgs::class
)
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
    UserDetail(
        modifier = modifier,
        userId = uiState.userId
    )
}

@Composable
fun UserDetail(
    modifier: Modifier = Modifier,
    userId: String = ""
) {
    Scaffold { innerPadding ->
        Column(
            modifier = modifier.padding(innerPadding),
        ) {
            Text(text = "User Detail: $userId")
        }
    }
}

@Preview
@Composable
private fun UserDetailPreview() {
    QuickMemTheme {
        UserDetail()
    }
}