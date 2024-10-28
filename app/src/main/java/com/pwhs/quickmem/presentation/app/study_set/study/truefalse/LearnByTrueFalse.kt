package com.pwhs.quickmem.presentation.app.study_set.study.truefalse

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.pwhs.quickmem.domain.model.flashcard.FlashCardResponseModel
import com.pwhs.quickmem.ui.theme.QuickMemTheme

@Composable
fun LearnByTrueFalse(
    modifier: Modifier = Modifier,
    flashCard: FlashCardResponseModel
) {
    Text(text = "LearnByTrueFalse")
}

@Preview
@Composable
private fun LearnByTrueFalsePreview() {
    QuickMemTheme {
        LearnByTrueFalse(
            flashCard = FlashCardResponseModel(
                id = "",
                term = "Term",
                definitionImageURL = "",
                definition = "Definition",
                isStarred = false,
                rating = "",
                flipStatus = "",
                studySetId = "",
                hint = "",
                explanation = "",
                createdAt = "",
                updatedAt = ""
            )
        )
    }
}