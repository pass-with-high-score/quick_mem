package com.pwhs.quickmem.presentation.app.search

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pwhs.quickmem.R
import com.pwhs.quickmem.presentation.app.library.component.SearchTextField
import com.pwhs.quickmem.ui.theme.QuickMemTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.SearchResultScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<RootGraph>
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = viewModel(),
    navigator: DestinationsNavigator
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is SearchUiEvent.NavigateToResult -> {
                    navigator.navigate(
                        SearchResultScreenDestination(
                            query = event.query
                        )
                    )
                }

                is SearchUiEvent.ShowError -> {
                    Toast.makeText(context, event.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    Search(
        modifier = modifier,
        query = uiState.query,
        listResult = uiState.listResult,
        onQueryChange = { viewModel.onEvent(SearchUiAction.OnQueryChanged(it)) },
        onNavigateBack = {
            navigator.navigateUp()
        },
        onSearch = { viewModel.onEvent(SearchUiAction.Search) }
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Search(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit = {},
    listResult: List<String> = emptyList(),
    query: String = "",
    onQueryChange: (String) -> Unit = {},
    onSearch: () -> Unit = {}
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            LargeTopAppBar(
                title = {
                    SearchTextField(
                        searchQuery = query,
                        onSearchQueryChange = onQueryChange,
                        placeholder = stringResource(R.string.txt_study_sets_folder_classes),
                        onSearch = onSearch
                    )
                },
                expandedHeight = 140.dp,
                collapsedHeight = 64.dp,
                actions = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = stringResource(R.string.txt_close)
                        )
                    }
                },
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.TopCenter
        ) {
            if (listResult.isEmpty()) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Text(
                        text = stringResource(R.string.txt_enter_a_topic_or_keywords),
                        style = typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    )
                    Text(
                        text = stringResource(R.string.txt_tip_the_more_specific_the_better),
                        style = typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    )
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(listResult.asReversed()) { result ->
                        Text(
                            text = result,
                            style = typography.bodyLarge,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun SearchScreenPreview() {
    QuickMemTheme {
        Search()
    }
}