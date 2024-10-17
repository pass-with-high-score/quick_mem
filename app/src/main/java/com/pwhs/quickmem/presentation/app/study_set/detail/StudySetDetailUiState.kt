package com.pwhs.quickmem.presentation.app.study_set.detail

import androidx.compose.ui.graphics.Color
import com.pwhs.quickmem.domain.model.flashcard.StudySetFlashCardResponseModel
import com.pwhs.quickmem.domain.model.subject.SubjectModel
import com.pwhs.quickmem.domain.model.users.UserResponseModel

data class StudySetDetailUiState(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val color: Color = Color.White,
    val subject: SubjectModel = SubjectModel(),
    val flashCardCount: Int = 0,
    val flashCards: List<StudySetFlashCardResponseModel> = emptyList(),
    val isPublic: Boolean = false,
    val user: UserResponseModel = UserResponseModel(),
    val createdAt: String = "",
    val updatedAt: String = "",
)