package com.pwhs.quickmem.presentation.app.folder.add_study_set.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.pwhs.quickmem.R
import com.pwhs.quickmem.domain.model.color.ColorModel
import com.pwhs.quickmem.domain.model.study_set.GetStudySetResponseModel
import com.pwhs.quickmem.domain.model.subject.SubjectModel
import com.pwhs.quickmem.domain.model.users.UserResponseModel
import com.pwhs.quickmem.ui.theme.QuickMemTheme
import com.pwhs.quickmem.util.toColor

@Composable
fun AddStudySetToFolderItem(
    modifier: Modifier = Modifier,
    studySet: GetStudySetResponseModel,
    isAdded: Boolean = false,
    onAddStudySetToFolder: (String) -> Unit = {},
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 4.dp
        ),
        border = BorderStroke(
            width = 1.dp,
            color = studySet.color?.hexValue?.toColor()
                ?: ColorModel.defaultColors[0].hexValue.toColor()
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
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
                        color = colorScheme.onSurface.copy(
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
                        model = studySet.owner.avatarUrl,
                        contentDescription = "User avatar",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(18.dp)
                            .clip(CircleShape)
                    )
                    Text(
                        studySet.owner.username,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
            Box(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .size(36.dp)
                    .clip(CircleShape)
                    .clickable {
                        onAddStudySetToFolder(studySet.id)
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(if (isAdded) R.drawable.ic_check_circle else R.drawable.ic_add_circle),
                    contentDescription = if (isAdded) "Check Icon" else "Add Icon",
                    modifier = Modifier.size(26.dp),
                    tint = colorScheme.onSurface
                )
            }
        }
    }
}

@Preview
@Composable
private fun StudySetItemPreview() {
    QuickMemTheme {
        Scaffold {
            LazyColumn(
                modifier = Modifier
                    .padding(it)
                    .padding(horizontal = 16.dp)
            ) {
                item {
                    repeat(10) {
                        AddStudySetToFolderItem(
                            modifier = Modifier
                                .fillMaxWidth(),
                            studySet = GetStudySetResponseModel(
                                id = "1",
                                title = "Study Set Title",
                                flashCardCount = 10,
                                color = ColorModel.defaultColors[0],
                                subject = SubjectModel.defaultSubjects[0],
                                owner = UserResponseModel(
                                    id = "1",
                                    username = "User",
                                    avatarUrl = "https://www.example.com/avatar.jpg"
                                ),
                                description = "Study Set Description",
                                isPublic = true,
                                createdAt = "2021-01-01T00:00:00Z",
                                updatedAt = "2021-01-01T00:00:00Z",
                                flashcards = emptyList()
                            )
                        )
                    }
                }
            }
        }
    }
}