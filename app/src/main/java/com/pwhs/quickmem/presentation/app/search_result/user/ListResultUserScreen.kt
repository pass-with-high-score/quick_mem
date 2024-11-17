package com.pwhs.quickmem.presentation.app.search_result.user

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.pwhs.quickmem.domain.model.users.SearchUserResponseModel
import com.pwhs.quickmem.presentation.ads.BannerAds
import com.pwhs.quickmem.presentation.app.search_result.user.component.SearchUserResultItem
import com.pwhs.quickmem.ui.theme.QuickMemTheme


@Composable
fun ListResultUserScreen(
    modifier: Modifier = Modifier,
    users: LazyPagingItems<SearchUserResponseModel>? = null,
    onMembersItemClicked: (SearchUserResponseModel?) -> Unit = {},
    onUserRefresh: () -> Unit = {}
) {
    Scaffold { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
        ) {
            LazyColumn {
                item {
                    BannerAds(
                        modifier = Modifier.padding(8.dp)
                    )
                }
                items(users?.itemCount ?: 0) {
                    val user = users?.get(it)
                    SearchUserResultItem(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        searchMemberModel = user,
                        onClicked = onMembersItemClicked
                    )
                }
                item {
                    if (users?.itemCount == 0) {
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
                                text = "No members",
                                style = typography.titleLarge,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
                item {
                    users?.apply {
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
                                        onClick = onUserRefresh,
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
            }
        }
    }
}

@Preview
@Composable
private fun ListResultUserScreenPreview() {
    QuickMemTheme {
        ListResultUserScreen()
    }

}