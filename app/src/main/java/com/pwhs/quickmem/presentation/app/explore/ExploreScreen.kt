package com.pwhs.quickmem.presentation.app.explore

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
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
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination<RootGraph>
fun ExploreScreen(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator,
    viewModel: ExploreViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {

                else -> {}
            }
        }
    }

    Explore(modifier = modifier)
}

@Composable
fun Explore(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = modifier.padding(innerPadding),
        ) {
            Text("Explore Screen")
        }
    }
}

@Preview
@Composable
private fun ExploreScreenPreview() {
    MaterialTheme {
        Explore()
    }
}