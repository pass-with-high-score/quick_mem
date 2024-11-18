package com.pwhs.quickmem.data.mapper.study_set

import com.pwhs.quickmem.data.dto.study_set.GetMakeACopyResponseDto
import com.pwhs.quickmem.data.mapper.color.toColorModel
import com.pwhs.quickmem.data.mapper.color.toColorResponseDto
import com.pwhs.quickmem.data.mapper.flashcard.toDto
import com.pwhs.quickmem.data.mapper.flashcard.toModel
import com.pwhs.quickmem.data.mapper.subject.toSubjectModel
import com.pwhs.quickmem.data.mapper.subject.toSubjectResponseDto
import com.pwhs.quickmem.data.mapper.user.toDto
import com.pwhs.quickmem.data.mapper.user.toModel
import com.pwhs.quickmem.domain.model.study_set.GetMakeACopyResponseModel

fun GetMakeACopyResponseDto.toModel() = GetMakeACopyResponseModel(
    id = id,
    title = title,
    description = description,
    isPublic = isPublic,
    isAIGenerated = isAIGenerated,
    createdAt = createdAt,
    updatedAt = updatedAt,
    linkShareCode = linkShareCode,
    subject = subject?.toSubjectModel(),
    owner = owner.toModel(),
    color = color?.toColorModel(),
    flashcardCount = flashcardCount,
    flashcards = flashcards?.map { it.toModel() } ?: emptyList()
)

fun GetMakeACopyResponseModel.toDto() = GetMakeACopyResponseDto(
    id = id,
    title = title,
    description = description,
    isPublic = isPublic,
    isAIGenerated = isAIGenerated,
    createdAt = createdAt,
    updatedAt = updatedAt,
    linkShareCode = linkShareCode,
    subject = subject?.toSubjectResponseDto(),
    owner = owner.toDto(),
    color = color?.toColorResponseDto(),
    flashcardCount = flashcardCount,
    flashcards =flashcards.map { it.toDto() }
)
