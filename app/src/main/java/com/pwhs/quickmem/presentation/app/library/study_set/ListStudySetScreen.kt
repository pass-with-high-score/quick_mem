package com.pwhs.quickmem.presentation.app.library.study_set

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.pwhs.quickmem.domain.model.color.ColorModel
import com.pwhs.quickmem.domain.model.study_set.GetStudySetResponseModel
import com.pwhs.quickmem.domain.model.subject.SubjectModel
import com.pwhs.quickmem.presentation.ads.BannerAds
import com.pwhs.quickmem.util.toColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListStudySetScreen(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    studySets: List<GetStudySetResponseModel> = emptyList(),
    onStudySetClick: (String) -> Unit = {},
    onStudySetRefresh: () -> Unit = {},
    avatarUrl: String = "",
    username: String = "",
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
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp
                            )
                        )
                        HorizontalDivider(
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                        )
                        Text(
                            text = "Get started by searching for a study set or creating your own",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            ),
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    }
                }

                else -> {
                    Column(modifier = Modifier.padding(innerPadding)) {
                        LazyColumn {
                            item {
                                // TODO: Add search bar
                            }
                            items(studySets) { studySet ->
                                Card(
                                    onClick = { onStudySetClick(studySet.id) },
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color.White,
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                        .padding(horizontal = 8.dp),
                                    elevation = CardDefaults.elevatedCardElevation(
                                        defaultElevation = 4.dp
                                    ),
                                    border = BorderStroke(
                                        width = 1.dp,
                                        color = studySet.color?.hexValue?.toColor()
                                            ?: ColorModel.defaultColors[0].hexValue.toColor()
                                    )
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .padding(8.dp)
                                            .background(Color.Transparent)
                                    ) {
                                        Text(
                                            studySet.title,
                                            style = MaterialTheme.typography.titleMedium.copy(
                                                fontWeight = FontWeight.Bold
                                            ),
                                            maxLines = 2,
                                            overflow = TextOverflow.Ellipsis,
                                            color = studySet.color?.hexValue?.toColor()
                                                ?.copy(alpha = 0.8f)
                                                ?: ColorModel.defaultColors[0].hexValue.toColor()
                                                    .copy(alpha = 0.8f),
                                        )
                                        Text(
                                            buildAnnotatedString {
                                                withStyle(
                                                    style = MaterialTheme.typography.bodySmall.toSpanStyle()
                                                        .copy(
                                                            fontWeight = FontWeight.Bold
                                                        )
                                                ) {
                                                    append("${studySet.flashCardCount}")
                                                    withStyle(
                                                        style = MaterialTheme.typography.bodySmall.toSpanStyle()
                                                            .copy(
                                                                fontWeight = FontWeight.Normal
                                                            )
                                                    ) {
                                                        append(" Flashcards")
                                                    }
                                                }
                                            }
                                        )
                                        Text(
                                            studySet.subject?.name
                                                ?: SubjectModel.defaultSubjects[0].name,
                                            style = MaterialTheme.typography.bodySmall.copy(
                                                color = MaterialTheme.colorScheme.onSurface.copy(
                                                    alpha = 0.6f
                                                )
                                            )
                                        )
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                                            modifier = Modifier.padding(top = 8.dp)
                                        ) {
                                            AsyncImage(
                                                model = studySet.user.avatarUrl,
                                                contentDescription = "User avatar",
                                                contentScale = ContentScale.Crop,
                                                modifier = Modifier
                                                    .size(18.dp)
                                                    .clip(CircleShape)
                                            )
                                            Text(
                                                studySet.user.username,
                                                style = MaterialTheme.typography.bodySmall
                                            )
                                        }
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
}