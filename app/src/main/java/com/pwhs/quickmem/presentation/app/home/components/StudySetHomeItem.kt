package com.pwhs.quickmem.presentation.app.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.CardDefaults.elevatedCardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.pwhs.quickmem.R
import com.pwhs.quickmem.domain.model.color.ColorModel
import com.pwhs.quickmem.domain.model.study_set.StudySetModel
import com.pwhs.quickmem.domain.model.subject.SubjectModel
import com.pwhs.quickmem.ui.theme.QuickMemTheme
import com.pwhs.quickmem.util.toColor

@Composable
fun StudySetHomeItem(
    modifier: Modifier = Modifier,
    studySet: StudySetModel?,
    onStudySetClick: (String) -> Unit = {},
) {
    val borderColor = studySet?.colorHex?.toColor()
        ?: ColorModel.defaultColors[0].hexValue.toColor()

    val titleColor = borderColor.copy(alpha = 0.8f)

    Card(
        onClick = { onStudySetClick(studySet?.id ?: "") },
        colors = cardColors(containerColor = Color.White),
        modifier = modifier
            .padding(vertical = 8.dp)
            .padding(start = 16.dp)
            .width(width = 290.dp),
        elevation = elevatedCardElevation(defaultElevation = 4.dp),
        border = BorderStroke(width = 1.dp, color = borderColor)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = studySet?.title.orEmpty(),
                    style = typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = titleColor
                )
                Text(
                    buildAnnotatedString {
                        withStyle(
                            style = typography.bodySmall.toSpanStyle()
                                .copy(fontWeight = FontWeight.Bold)
                        ) {
                            append(studySet?.flashcardCount.toString())
                            withStyle(
                                style = typography.bodySmall.toSpanStyle()
                                    .copy(fontWeight = FontWeight.Normal)
                            ) {
                                append(" Flashcards")
                            }
                        }
                    }
                )
                Text(
                    text = studySet?.subjectName ?: SubjectModel.defaultSubjects[0].name,
                    style = typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    AsyncImage(
                        model = studySet?.ownerAvatarUrl,
                        contentDescription = stringResource(R.string.txt_user_avatar),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(18.dp)
                            .clip(CircleShape)
                    )
                    Text(
                        text = studySet?.ownerUsername.orEmpty(),
                        style = typography.bodySmall
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun StudySetItemPreview() {
    QuickMemTheme {
        Scaffold {
            LazyRow(
                modifier = Modifier
                    .padding(it),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(2) {
                    StudySetHomeItem(
                        studySet = StudySetModel(
                            id = "1",
                            title = "Study Set Title",
                            description = "Study Set Description",
                            flashcardCount = 10,
                            colorHex = "#FF0000",
                            subjectName = "Subject Name",
                            ownerId = "1",
                            ownerUsername = "Owner Username",
                            ownerAvatarUrl = "https://example.com/avatar.jpg",
                            isPublic = true
                        ),
                    )
                }
            }
        }
    }
}
