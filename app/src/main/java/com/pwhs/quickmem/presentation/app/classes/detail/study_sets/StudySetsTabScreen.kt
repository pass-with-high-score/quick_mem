package com.pwhs.quickmem.presentation.app.classes.detail.study_sets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pwhs.quickmem.R
import com.pwhs.quickmem.domain.model.study_set.GetStudySetResponseModel
import com.pwhs.quickmem.presentation.app.classes.detail.study_sets.component.ClassDetailEmptyStudySet
import com.pwhs.quickmem.presentation.app.library.study_set.component.StudySetItem
import com.pwhs.quickmem.ui.theme.QuickMemTheme

@Composable
fun StudySetsTabScreen(
    modifier: Modifier = Modifier,
    isOwner: Boolean = false,
    studySets: List<GetStudySetResponseModel> = emptyList(),
    onAddStudySetClicked: () -> Unit = {},
    onStudySetItemClicked: (GetStudySetResponseModel) -> Unit = {},
    onDeleteStudySetClicked: (String) -> Unit = {}
) {
    Scaffold { innerPadding ->
        Box(
            modifier = modifier
                .padding(top = 10.dp)
                .fillMaxSize()
        ) {
            when {
                studySets.isEmpty() -> {
                    ClassDetailEmptyStudySet(
                        modifier = Modifier.padding(innerPadding),
                        title = stringResource(R.string.txt_this_class_has_no_sets),
                        subtitle = stringResource(R.string.txt_add_flashcard_sets_to_share_them_with_your_class),
                        buttonTitle = stringResource(R.string.txt_add_study_sets),
                        onAddStudySetClicked = onAddStudySetClicked,
                        isOwner = isOwner
                    )
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize(),
                    ) {
                        items(items = studySets, key = {it.id}) { studySet ->
                            StudySetItem(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                studySet = studySet,
                                onStudySetClick = { onStudySetItemClicked(studySet) },
                                isOwner = isOwner,
                                onDeleteClick = { studySetId ->
                                    onDeleteStudySetClicked(studySetId)
                                }
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
fun StudySetsTabPreview() {
    QuickMemTheme {
        StudySetsTabScreen(isOwner = true)
    }
}