package com.pwhs.quickmem.presentation.app.folder.detail.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pwhs.quickmem.domain.model.color.ColorModel
import com.pwhs.quickmem.domain.model.study_set.GetStudySetResponseModel
import com.pwhs.quickmem.domain.model.subject.SubjectModel
import com.pwhs.quickmem.domain.model.users.UserResponseModel
import com.pwhs.quickmem.presentation.app.library.study_set.component.StudySetItem
import com.pwhs.quickmem.ui.theme.QuickMemTheme

@Composable
fun FolderDetailStudySetList(
    isOwner:Boolean,
    modifier: Modifier = Modifier,
    studySets: List<GetStudySetResponseModel> = emptyList(),
    onStudySetClick: (String) -> Unit = {},
    onAddFlashCardClick: () -> Unit = {},
) {
    Box(modifier = modifier) {
        when {
            studySets.isEmpty() -> {
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
                    if (isOwner){
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
            }

            else -> {
                LazyColumn(
                    horizontalAlignment = CenterHorizontally,
                ) {
                    items(studySets) { studySet ->
                        StudySetItem(
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
            FolderDetailStudySetList(
                modifier = Modifier
                    .padding(it),
                isOwner = true,
                studySets = listOf(
                    GetStudySetResponseModel(
                        id = "1",
                        title = "Study Set 1",
                        flashCardCount = 10,
                        color = ColorModel.defaultColors[0],
                        subject = SubjectModel.defaultSubjects[0],
                        owner = UserResponseModel(
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
            FolderDetailStudySetList(
                modifier = Modifier
                    .padding(it),
                studySets = emptyList(),
                isOwner = true
            )
        }
    }
}