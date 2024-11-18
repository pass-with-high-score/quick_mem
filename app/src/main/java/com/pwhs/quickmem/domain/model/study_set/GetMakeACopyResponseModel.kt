package com.pwhs.quickmem.domain.model.study_set

import com.pwhs.quickmem.domain.model.color.ColorModel
import com.pwhs.quickmem.domain.model.flashcard.StudySetFlashCardResponseModel
import com.pwhs.quickmem.domain.model.subject.SubjectModel
import com.pwhs.quickmem.domain.model.users.UserResponseModel

data class GetMakeACopyResponseModel(
    val id: String,
    val title: String,
    val description: String? = null,
    val isPublic: Boolean,
    val isAIGenerated: Boolean,
    val createdAt: String,
    val updatedAt: String,
    val linkShareCode: String? = null,
    val subject: SubjectModel? = null,
    val owner: UserResponseModel,
    val color: ColorModel? = null,
    val flashcardCount: Int,
    val flashcards: List<StudySetFlashCardResponseModel> = emptyList()
)
