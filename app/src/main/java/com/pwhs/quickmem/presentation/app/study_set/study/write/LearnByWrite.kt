package com.pwhs.quickmem.presentation.app.study_set.study.write

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.pwhs.quickmem.domain.model.flashcard.FlashCardResponseModel
import com.pwhs.quickmem.ui.theme.QuickMemTheme

@Composable
fun LearnByWrite(
    modifier: Modifier = Modifier,
    flashCard: FlashCardResponseModel
) {
    Text(text = "LearnByWrite")
}

@Preview
@Composable
private fun LearnByWritePreview() {
    QuickMemTheme {
        LearnByWrite(
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