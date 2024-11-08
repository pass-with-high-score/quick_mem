package com.pwhs.quickmem.presentation.app.library.study_set

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme.colorScheme
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.pwhs.quickmem.R
import com.pwhs.quickmem.domain.model.study_set.GetStudySetResponseModel
import com.pwhs.quickmem.presentation.ads.BannerAds
import com.pwhs.quickmem.presentation.app.library.component.SearchTextField
import com.pwhs.quickmem.presentation.app.library.study_set.component.StudySetItem
import com.pwhs.quickmem.ui.theme.QuickMemTheme
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListStudySetScreen(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    studySets: List<GetStudySetResponseModel> = emptyList(),
    onStudySetClick: (GetStudySetResponseModel) -> Unit = {},
    onStudySetRefresh: () -> Unit = {},
    avatarUrl: String = "",
    username: String = "",
    isOwner: Boolean = false
) {
    val refreshState = rememberPullToRefreshState()
    var searchQuery by remember { mutableStateOf("") }

    val filterStudySets = studySets.filter {
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
                onStudySetRefresh()
            }
        ) {
            Timber.d("ListStudySetScreen: studySets: $studySets")
            when {
                studySets.isEmpty() -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(top = 40.dp)
                            .padding(horizontal = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        if (!isOwner) {
                            AsyncImage(
                                model = avatarUrl,
                                contentDescription = "User avatar",
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                            Text(
                                text = "Hello, $username",
                                style = typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 24.sp
                                )
                            )
                            HorizontalDivider(
                                thickness = 1.dp,
                                color = colorScheme.onSurface.copy(alpha = 0.1f),
                            )
                            Text(
                                text = "Get started by searching for a study set or creating your own",
                                textAlign = TextAlign.Center,
                                style = typography.bodyMedium.copy(
                                    color = colorScheme.onSurface.copy(alpha = 0.6f),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp
                                ),
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                        } else {
                            Image(
                                painter = painterResource(id = R.drawable.ic_flashcards),
                                contentDescription = "Empty study set",
                            )
                            Text(
                                text = "No study sets found",
                                style = typography.titleLarge,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                studySets.isNotEmpty() -> {
                    LazyColumn {
                        item {
                            SearchTextField(
                                searchQuery = searchQuery,
                                onSearchQueryChange = { searchQuery = it },
                                placeholder = "Search study sets"
                            )

                        }
                        items(filterStudySets) { studySet ->
                            StudySetItem(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                studySet = studySet,
                                onStudySetClick = { onStudySetClick(studySet) }
                            )
                        }
                        item {
                            if (filterStudySets.isEmpty() && searchQuery.trim().isNotEmpty()) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "No study sets found",
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
private fun ListStudySetScreenPreview() {
    QuickMemTheme {
        ListStudySetScreen(
            isLoading = false,
            studySets = emptyList(),
            onStudySetClick = {},
            onStudySetRefresh = {},
            avatarUrl = "",
            username = ""
        )
    }
}