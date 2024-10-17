package com.pwhs.quickmem.domain.model.study_set

import com.pwhs.quickmem.domain.model.color.ColorModel
import com.pwhs.quickmem.domain.model.flashcard.StudySetFlashCardResponseModel
import com.pwhs.quickmem.domain.model.subject.SubjectModel
import com.pwhs.quickmem.domain.model.users.UserResponseModel

data class GetStudySetResponseModel(
    val id: String,
    val title: String,
    val description: String,
    val isPublic: Boolean,
    val ownerId: String,
    val subject: SubjectModel? = null,
    val color: ColorModel? = null,
    val user: UserResponseModel,
    val flashCardCount: Int,
    val flashcards: List<StudySetFlashCardResponseModel>,
    val createdAt: String,
    val updatedAt: String,
)
