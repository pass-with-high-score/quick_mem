package com.pwhs.quickmem.presentation.app.folder.detail.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.pwhs.quickmem.domain.model.color.ColorModel
import com.pwhs.quickmem.domain.model.study_set.GetStudySetResponseModel
import com.pwhs.quickmem.domain.model.subject.SubjectModel
import com.pwhs.quickmem.util.toColor

@Composable
fun ListStudySetInnerFolder(
    modifier: Modifier = Modifier,
    studySet: List<GetStudySetResponseModel> = emptyList(),
    onStudySetClick: (String) -> Unit,
    onAddFlashCardClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth(),
        contentAlignment = Center
    ) {
        when {
            studySet.isEmpty() -> {
                Column(
                    horizontalAlignment = CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        "Add your study set to this folder",
                        style = typography.titleLarge.copy(
                            fontWeight = Bold,
                            color = colorScheme.onSurface
                        ),
                        modifier = Modifier.padding(16.dp),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        "This Folder can contain multiple study sets, each with multiple flashcards",
                        textAlign = TextAlign.Center,
                        style = typography.bodyMedium.copy(
                            color = colorScheme.onSurface,
                        ),
                    )
                    Button(
                        onClick = onAddFlashCardClick,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Row(
                            verticalAlignment = CenterVertically
                        ) {
                            Icon(
                                Icons.Filled.Add,
                                contentDescription = "Add",
                                tint = colorScheme.background,
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            Text(
                                "Add study Set",
                                style = typography.titleMedium.copy(
                                    color = colorScheme.background
                                )
                            )
                        }
                    }
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = CenterHorizontally,
                ) {
                    items(studySet) { studySetItem ->
                        Card(
                            onClick = { onStudySetClick(studySetItem.id) },
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
                                color = studySetItem.color?.hexValue?.toColor()
                                    ?: ColorModel.defaultColors[0].hexValue.toColor()
                            )
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .background(Color.Transparent)
                            ) {
                                Text(
                                    studySetItem.title,
                                    style = typography.titleMedium.copy(
                                        fontWeight = Bold
                                    ),
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis,
                                    color = studySetItem.color?.hexValue?.toColor()
                                        ?.copy(alpha = 0.8f)
                                        ?: ColorModel.defaultColors[0].hexValue.toColor()
                                            .copy(alpha = 0.8f),
                                )
                                Text(
                                    buildAnnotatedString {
                                        withStyle(
                                            style = typography.bodySmall.toSpanStyle()
                                                .copy(
                                                    fontWeight = Bold
                                                )
                                        ) {
                                            append("${studySetItem.flashCardCount}")
                                            withStyle(
                                                style = typography.bodySmall.toSpanStyle()
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
                                    studySetItem.subject?.name
                                        ?: SubjectModel.defaultSubjects[0].name,
                                    style = typography.bodySmall.copy(
                                        color = colorScheme.onSurface.copy(
                                            alpha = 0.6f
                                        )
                                    )
                                )
                                Row(
                                    verticalAlignment = CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    modifier = Modifier.padding(top = 8.dp)
                                ) {
                                    AsyncImage(
                                        model = studySetItem.user.avatarUrl,
                                        contentDescription = "User avatar",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .size(18.dp)
                                            .clip(CircleShape)
                                    )
                                    Text(
                                        studySetItem.user.username,
                                        style = typography.bodySmall
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}