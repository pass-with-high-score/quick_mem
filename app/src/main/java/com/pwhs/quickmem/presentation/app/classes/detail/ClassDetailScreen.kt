package com.pwhs.quickmem.presentation.app.classes.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.WelcomeScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination<RootGraph>(
    navArgs = ClassDetailArgs::class
)
fun ClassDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: ClassDetailViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                ClassDetailUiEvent.NavigateToWelcome -> {
                    navigator.navigate(WelcomeScreenDestination) {
                        popUpTo(WelcomeScreenDestination) {
                            inclusive = true
                        }
                    }
                }

                ClassDetailUiEvent.OnJoinClass -> TODO()
            }
        }
    }

    ClassDetail(
        modifier = modifier,
        code = uiState.joinClassCode
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassDetail(
    modifier: Modifier = Modifier,
    code: String
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Classes") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier.padding(innerPadding)
        ) {
            Text(text = "Classes Screen")
            Text(text = "Code: $code")
        }
    }
}

@Preview
@Composable
private fun ClassDetailScreenPreview() {
    MaterialTheme {
        ClassDetail(code = "123")
    }
}