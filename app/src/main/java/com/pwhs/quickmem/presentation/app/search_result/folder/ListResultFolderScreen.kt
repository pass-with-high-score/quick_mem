package com.pwhs.quickmem.presentation.app.search_result.folder

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.pwhs.quickmem.R
import com.pwhs.quickmem.domain.model.folder.GetFolderResponseModel
import com.pwhs.quickmem.domain.model.users.UserResponseModel
import com.pwhs.quickmem.presentation.ads.BannerAds
import com.pwhs.quickmem.presentation.app.library.folder.component.FolderItem
import com.pwhs.quickmem.ui.theme.QuickMemTheme

@Composable
fun ListResultFolderScreen(
    modifier: Modifier = Modifier,
    folders: LazyPagingItems<GetFolderResponseModel>? = null,
    onFolderClick: (GetFolderResponseModel?) -> Unit = {},
    onFolderRefresh: () -> Unit = {}
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
    ) { innerPadding ->
        LazyColumn {
            item {
                BannerAds(
                    modifier = Modifier.padding(8.dp)
                )
            }
            items(folders?.itemCount ?: 0) {
                val folder = folders?.get(it)
                FolderItem(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    title = folder?.title ?: "",
                    numOfStudySets = folder?.studySetCount ?: 0,
                    onClick = { onFolderClick(folder) },
                    userResponseModel = folder?.owner ?: UserResponseModel(),
                    folder = folder
                )
            }
            item {
                if (folders?.itemCount == 0) {
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
                folders?.apply {
                    when {
                        loadState.refresh is LoadState.Loading -> {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(innerPadding)
                                    .padding(top = 40.dp)
                                    .padding(horizontal = 16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .size(36.dp),
                                    color = colorScheme.primary
                                )
                            }
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
                                    onClick = onFolderRefresh,
                                    modifier = Modifier.padding(top = 16.dp)
                                ) {
                                    Text(text = "Retry")
                                }
                            }
                        }

                        loadState.append is LoadState.Loading -> {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(innerPadding)
                                    .padding(top = 40.dp)
                                    .padding(horizontal = 16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .size(36.dp),
                                    color = colorScheme.primary
                                )
                            }
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

@Preview
@Composable
private fun ListResultFolderScreenPreview() {
    QuickMemTheme {
        ListResultFolderScreen(
            folders = null,
            onFolderClick = {},
            onFolderRefresh = {}
        )
    }

}