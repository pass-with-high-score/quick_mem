package com.pwhs.quickmem.presentation.app.search_result.user

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.pwhs.quickmem.R
import com.pwhs.quickmem.domain.model.users.SearchUserResponseModel
import com.pwhs.quickmem.presentation.app.search_result.user.component.SearchUserResultItem
import com.pwhs.quickmem.ui.theme.QuickMemTheme


@Composable
fun ListResultUserScreen(
    modifier: Modifier = Modifier,
    users: List<SearchUserResponseModel> = emptyList(),
    onMembersItemClicked: (SearchUserResponseModel) -> Unit = {},
) {
    Scaffold { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
        ) {
            when {
                users.isEmpty() -> {
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

                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(users) { user ->
                            SearchUserResultItem(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                searchMemberModel = user,
                                onClicked = onMembersItemClicked
                            )
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