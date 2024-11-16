package com.pwhs.quickmem.presentation.app.search_result.study_set

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.pwhs.quickmem.R
import com.pwhs.quickmem.domain.model.study_set.GetStudySetResponseModel
import com.pwhs.quickmem.presentation.ads.BannerAds
import com.pwhs.quickmem.presentation.app.library.study_set.component.StudySetItem
import com.pwhs.quickmem.ui.theme.QuickMemTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListResultStudySetScreen(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    studySets: LazyPagingItems<GetStudySetResponseModel>? = null,
    onStudySetClick: (GetStudySetResponseModel?) -> Unit = {},
    onStudySetRefresh: () -> Unit = {},
    onResetClick: () -> Unit = {}
) {
    val refreshState = rememberPullToRefreshState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
    ) { innerPadding ->
        PullToRefreshBox(
            modifier = Modifier.fillMaxSize(),
            state = refreshState,
            isRefreshing = isLoading,
            onRefresh = {
                onStudySetRefresh()
            }
        ) {
            LazyColumn {
                item {
                    BannerAds(
                        modifier = Modifier.padding(8.dp)
                    )
                }
                items(studySets?.itemCount ?: 0) {
                    val studySet = studySets?.get(it)
                    StudySetItem(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        studySet = studySet,
                        onStudySetClick = { onStudySetClick(studySet) }
                    )
                }
                item {
                    if (studySets?.itemCount == 0) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = stringResource(R.string.txt_no_study_sets_found),
                                style = typography.bodyLarge,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
                item {
                    studySets?.apply {
                        when {
                            loadState.refresh is LoadState.Loading -> {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .align(Alignment.Center),
                                    color = colorScheme.primary
                                )
                            }

                            loadState.refresh is LoadState.Error -> {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(innerPadding)
                                        .padding(top = 40.dp)
                                        .padding(horizontal = 16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    Image(
                                        imageVector = Icons.Default.Error,
                                        contentDescription = stringResource(R.string.txt_error),
                                    )
                                    Text(
                                        text = stringResource(R.string.txt_error_occurred),
                                        style = typography.titleLarge,
                                        textAlign = TextAlign.Center
                                    )
                                    Button(
                                        onClick = onStudySetRefresh,
                                        modifier = Modifier.padding(top = 16.dp)
                                    ) {
                                        Text(text = "Retry")
                                    }
                                }
                            }

                            loadState.append is LoadState.Loading -> {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .align(Alignment.Center),
                                    color = colorScheme.primary
                                )
                            }

                            loadState.append is LoadState.Error -> {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(innerPadding)
                                        .padding(top = 40.dp)
                                        .padding(horizontal = 16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    Image(
                                        imageVector = Icons.Default.Error,
                                        contentDescription = stringResource(R.string.txt_error),
                                    )
                                    Text(
                                        text = stringResource(R.string.txt_error_occurred),
                                        style = typography.titleLarge,
                                        textAlign = TextAlign.Center
                                    )
                                    Button(
                                        onClick = { retry() },
                                        modifier = Modifier.padding(top = 16.dp)
                                    ) {
                                        Text(text = "Retry")
                                    }
                                }
                            }
                        }
                    }
                }
                item {
                    Spacer(modifier = Modifier.padding(60.dp))
                }
            }
        }
    }
}

@Preview
@Composable
private fun ListResultStudySetScreenPreview() {
    QuickMemTheme {
        ListResultStudySetScreen(
            isLoading = false,
            studySets = null,
            onStudySetClick = {},
            onStudySetRefresh = {},
        )
    }
}