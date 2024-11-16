package com.pwhs.quickmem.presentation.app.search_result.classes

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pwhs.quickmem.R
import com.pwhs.quickmem.domain.model.classes.GetClassByOwnerResponseModel
import com.pwhs.quickmem.presentation.ads.BannerAds
import com.pwhs.quickmem.presentation.app.library.classes.component.ClassItem
import com.pwhs.quickmem.presentation.app.library.component.SearchTextField
import com.pwhs.quickmem.ui.theme.QuickMemTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListResultClassesScreen(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    classes: List<GetClassByOwnerResponseModel> = emptyList(),
    onClassClicked: (GetClassByOwnerResponseModel) -> Unit = {},
    onClassRefresh: () -> Unit = {},
) {
    val refreshState = rememberPullToRefreshState()
    var searchQuery by remember { mutableStateOf("") }

    val filterClass = classes.filter {
        searchQuery.trim().takeIf { query -> query.isNotEmpty() }?.let { query ->
            it.title.contains(query, ignoreCase = true)
        } ?: true
    }
    Scaffold(
        modifier = modifier.fillMaxSize(),
    ) { innerPadding ->
        PullToRefreshBox(
            modifier = Modifier.fillMaxSize(),
            state = refreshState,
            isRefreshing = isLoading,
            onRefresh = {
                onClassRefresh()
            }
        ) {
            when {
                classes.isEmpty() -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(top = 40.dp)
                            .padding(horizontal = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_group),
                            contentDescription = stringResource(R.string.txt_empty_class),
                        )
                        Text(
                            text = stringResource(R.string.txt_no_classes_found),
                            style = typography.titleLarge,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                else -> {
                    LazyColumn {
                        item {
                            if (classes.isNotEmpty()) {
                                SearchTextField(
                                    searchQuery = searchQuery,
                                    onSearchQueryChange = { searchQuery = it },
                                    placeholder = stringResource(R.string.txt_search_classes),
                                )
                            }
                        }
                        items(filterClass) { classItem ->
                            ClassItem(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                classItem = classItem,
                                onClick = { onClassClicked(classItem) }
                            )
                        }
                        item {
                            if (filterClass.isEmpty() && searchQuery.trim().isNotEmpty()) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = stringResource(R.string.txt_no_classes_found),
                                        style = typography.bodyLarge,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                        item {
                            BannerAds(
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                        item {
                            Spacer(modifier = Modifier.padding(60.dp))
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ListResultClassesScreenPreview() {
    QuickMemTheme {
        ListResultClassesScreen()
    }
}