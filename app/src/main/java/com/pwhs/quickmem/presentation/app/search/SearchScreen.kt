package com.pwhs.quickmem.presentation.app.search

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.pwhs.quickmem.R
import com.pwhs.quickmem.domain.model.search.SearchQueryModel
import com.pwhs.quickmem.presentation.app.library.component.SearchTextField
import com.pwhs.quickmem.presentation.app.search.component.SearchResentList
import com.pwhs.quickmem.presentation.component.LoadingOverlay
import com.pwhs.quickmem.ui.theme.QuickMemTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.SearchResultScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<RootGraph>
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
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
                SearchUiEvent.ClearAllSearchResent -> {
                    Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show()
                }
                SearchUiEvent.Loading -> {
                    // Nếu cần xử lý thêm, có thể thêm logic tại đây
                }
            }
        }
    }

    Search(
        modifier = modifier,
        query = uiState.query,
        isLoading = uiState.isLoading,
        listResult = uiState.listResult,
        onQueryChange = { viewModel.onEvent(SearchUiAction.OnQueryChanged(it)) },
        onNavigateBack = { navigator.navigateUp() },
        onSearch = { viewModel.onEvent(SearchUiAction.Search) },
        onClearAll = { viewModel.onEvent(SearchUiAction.DeleteAllSearch) },
        onSearchResentClick = { query -> viewModel.onEvent(SearchUiAction.SearchWithQueryResent(query)) },
        onRefresh = { viewModel.onEvent(SearchUiAction.OnRefresh) },
        onDeleteQuery = { query -> viewModel.onEvent(SearchUiAction.DeleteSearch(query.query)) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Search(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit = {},
    isLoading: Boolean = false,
    onRefresh: () -> Unit = {},
    listResult: List<SearchQueryModel> = emptyList(),
    query: String = "",
    onClearAll: () -> Unit = {},
    onQueryChange: (String) -> Unit = {},
    onSearch: () -> Unit = {},
    onSearchResentClick: (String) -> Unit = {},
    onDeleteQuery: (SearchQueryModel) -> Unit = {}
) {
    val refreshState = rememberPullToRefreshState()

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
        PullToRefreshBox(
            modifier = modifier.fillMaxSize(),
            isRefreshing = isLoading,
            onRefresh = onRefresh,
            state = refreshState
        ) {
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
                    SearchResentList(
                        listResult = listResult,
                        onSearchResent = { query -> onSearchResentClick(query) },
                        onDelete = { query -> onDeleteQuery(query) },
                        onClearAll = onClearAll
                    )
                }
            }
            LoadingOverlay(isLoading = isLoading)
        }
    }
}

@Preview
@Composable
private fun SearchScreenPreview() {
    QuickMemTheme {
        Search(
            query = "",
            listResult = emptyList(),
            isLoading = false,
            onQueryChange = {},
            onSearch = {},
            onClearAll = {},
            onSearchResentClick = {},
            onNavigateBack = {},
            onDeleteQuery = {}
        )
    }
}