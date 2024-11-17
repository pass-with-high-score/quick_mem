package com.pwhs.quickmem.presentation.app.search_result.folder

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pwhs.quickmem.R
import com.pwhs.quickmem.domain.model.folder.GetFolderResponseModel
import com.pwhs.quickmem.presentation.ads.BannerAds
import com.pwhs.quickmem.presentation.app.library.folder.component.FolderItem
import com.pwhs.quickmem.ui.theme.QuickMemTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListResultFolderScreen(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    folders: List<GetFolderResponseModel> = emptyList(),
    onFolderClick: (GetFolderResponseModel) -> Unit = {},
    onFolderRefresh: () -> Unit = {}
) {
    val refreshState = rememberPullToRefreshState()
    Scaffold(
        modifier = modifier
    ) { innerPadding ->
        PullToRefreshBox(
            modifier = Modifier.fillMaxSize(),
            state = refreshState,
            isRefreshing = isLoading,
            onRefresh = {
                onFolderRefresh()
            }
        ) {
            when (folders.isEmpty()) {
                true -> {
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
                            painter = painterResource(id = R.drawable.ic_folder),
                            contentDescription = stringResource(R.string.txt_empty_folder),
                        )
                        Text(
                            text = stringResource(R.string.txt_no_folders_found),
                            style = typography.titleLarge,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                false -> {
                    LazyColumn {
                        items(folders) { folder ->
                            FolderItem(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                title = folder.title,
                                numOfStudySets = folder.studySetCount,
                                onClick = { onFolderClick(folder) },
                                userResponseModel = folder.owner
                            )
                        }
                        item {
                            if (folders.isEmpty()) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = stringResource(R.string.txt_no_folders_found),
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
private fun ListResultFolderScreenPreview() {
    QuickMemTheme {
        ListResultFolderScreen()
    }

}