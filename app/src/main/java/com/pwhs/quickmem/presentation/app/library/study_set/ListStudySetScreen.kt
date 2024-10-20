package com.pwhs.quickmem.presentation.app.library.study_set

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.pwhs.quickmem.domain.model.study_set.GetStudySetResponseModel
import com.pwhs.quickmem.presentation.ads.BannerAds
import com.pwhs.quickmem.util.toColor

@Composable
fun ListStudySetScreen(
    modifier: Modifier = Modifier,
    studySets: List<GetStudySetResponseModel> = emptyList(),
    onStudySetClick: (String) -> Unit = {}
) {
    Scaffold(
        modifier = modifier
    ) { innerPadding ->
        when {
            studySets.isEmpty() -> {
                Column(modifier = Modifier.padding(innerPadding)) {
                    Text("No study sets found")
                }
            }

            else -> {
                Column(modifier = Modifier.padding(innerPadding)) {
                    LazyColumn {
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
                                    color = studySet.color!!.hexValue.toColor()
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
                                        color = studySet.color.hexValue.toColor()
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
                                        studySet.subject!!.name,
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
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
                                            modifier = Modifier.size(18.dp)
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