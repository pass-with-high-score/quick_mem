package com.pwhs.quickmem.presentation.app.folder.detail.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.pwhs.quickmem.domain.model.color.ColorModel
import com.pwhs.quickmem.domain.model.study_set.GetStudySetResponseModel
import com.pwhs.quickmem.domain.model.subject.SubjectModel
import com.pwhs.quickmem.domain.model.users.UserResponseModel
import com.pwhs.quickmem.ui.theme.QuickMemTheme
import com.pwhs.quickmem.util.toColor

@Composable
fun FolderDetailStudySetList(
    modifier: Modifier = Modifier,
    studySet: List<GetStudySetResponseModel> = emptyList(),
    onStudySetClick: (String) -> Unit = {},
    onAddFlashCardClick: () -> Unit = {},
    onStudyFolderClick: () -> Unit = {}
) {
    Box(modifier = modifier) {
        when {
            studySet.isEmpty() -> {
                Column(
                    horizontalAlignment = CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 100.dp)
                ) {
                    Text(
                        "The folder has no study sets",
                        style = typography.titleLarge.copy(
                            fontWeight = Bold,
                            color = colorScheme.onSurface
                        ),
                        modifier = Modifier.padding(16.dp),
                        textAlign = TextAlign.Center
                    )
                    Button(
                        onClick = onAddFlashCardClick
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
                                "Add study set",
                                style = typography.titleMedium.copy(
                                    color = colorScheme.background,
                                    fontWeight = Bold
                                )
                            )
                        }
                    }
                }
            }

            else -> {
                LazyColumn(
                    horizontalAlignment = CenterHorizontally,
                ) {
                    item {
                        OutlinedButton(
                            onClick = onStudyFolderClick,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            shape = MaterialTheme.shapes.medium,
                            border = BorderStroke(
                                width = 2.dp,
                                color = colorScheme.primary
                            )
                        ) {
                            Text(
                                text = "Study",
                                style = typography.titleMedium.copy(
                                    fontWeight = Bold,
                                    color = colorScheme.primary
                                ),
                                modifier = Modifier.padding(4.dp)
                            )
                        }
                    }
                    items(studySet) { studySetItem ->
                        Card(
                            onClick = { onStudySetClick(studySetItem.id) },
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White,
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ListStudySetInnerFolderPreview() {
    QuickMemTheme {
        Scaffold {
            FolderDetailStudySetList(
                modifier = Modifier
                    .padding(it),
                studySet = listOf(
                    GetStudySetResponseModel(
                        id = "1",
                        title = "Study Set 1",
                        flashCardCount = 10,
                        color = ColorModel.defaultColors[0],
                        subject = SubjectModel.defaultSubjects[0],
                        user = UserResponseModel(
                            id = "1",

                            ),
                        description = "Description",
                        isPublic = true,
                        ownerId = "1",
                        linkShareCode = "123",
                        flashcards = emptyList(),
                        createdAt = "2021-01-01",
                        updatedAt = "2021-01-01"
                    )
                )
            )
        }
    }
}


@Preview
@Composable
private fun ListStudySetInnerFolderPreviewEmpty() {
    QuickMemTheme {
        Scaffold {
            FolderDetailStudySetList(
                modifier = Modifier
                    .padding(it),
                studySet = emptyList()
            )
        }
    }
}