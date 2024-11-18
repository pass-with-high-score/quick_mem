package com.pwhs.quickmem.data.dto.study_set

import com.google.gson.annotations.SerializedName
import com.pwhs.quickmem.data.dto.color.ColorResponseDto
import com.pwhs.quickmem.data.dto.flashcard.StudySetFlashCardResponseDto
import com.pwhs.quickmem.data.dto.subject.SubjectResponseDto
import com.pwhs.quickmem.data.dto.user.UserResponseDto

data class GetMakeACopyResponseDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("isPublic")
    val isPublic: Boolean,
    @SerializedName("isAIGenerated")
    val isAIGenerated: Boolean,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("linkShareCode")
    val linkShareCode: String? = null,
    @SerializedName("subject")
    val subject: SubjectResponseDto? = null,
    @SerializedName("owner")
    val owner: UserResponseDto,
    @SerializedName("color")
    val color: ColorResponseDto? = null,
    @SerializedName("flashcardCount")
    val flashcardCount: Int,
    @SerializedName("flashcards")
    val flashcards: List<StudySetFlashCardResponseDto>? = null
)
