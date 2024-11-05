package com.pwhs.quickmem.presentation.app.folder.add_study_set.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.pwhs.quickmem.domain.model.color.ColorModel
import com.pwhs.quickmem.domain.model.study_set.GetStudySetResponseModel
import com.pwhs.quickmem.domain.model.subject.SubjectModel
import com.pwhs.quickmem.domain.model.users.UserResponseModel
import com.pwhs.quickmem.ui.theme.QuickMemTheme

@Composable
fun AddStudySetList(
    modifier: Modifier = Modifier,
    studySets: List<GetStudySetResponseModel> = emptyList(),
    onStudySetClick: (String) -> Unit = {},
    avatarUrl: String = "",
    username: String = "",
) {
    Box(modifier = modifier) {
        when {
            studySets.isEmpty() -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 40.dp)
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = CenterHorizontally,
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
                        text = "There are no owned study sets, create one to get started!",
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
                LazyColumn(
                    horizontalAlignment = CenterHorizontally,
                ) {
                    items(studySets) { studySet ->
                        AddStudySetItem(
                            studySet = studySet,
                            onStudySetClick = { onStudySetClick(studySet.id) }
                        )
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
            AddStudySetList(
                modifier = Modifier
                    .padding(it),
                studySets = listOf(
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
            AddStudySetList(
                modifier = Modifier
                    .padding(it),
                studySets = emptyList()
            )
        }
    }
}