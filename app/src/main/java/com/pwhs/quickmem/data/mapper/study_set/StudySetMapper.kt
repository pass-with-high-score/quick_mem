package com.pwhs.quickmem.data.mapper.study_set

import com.pwhs.quickmem.data.local.entities.StudySetEntity
import com.pwhs.quickmem.domain.model.study_set.StudySetModel

fun StudySetEntity.toModel() = StudySetModel(
    id = id,
    title = title,
    description = description,
    flashcardCount = flashcardCount,
    colorHex = colorHex,
    subjectName = subjectName,
    ownerId = ownerId,
    ownerUsername = ownerUsername,
    ownerAvatarUrl = ownerAvatarUrl,
    isPublic = isPublic
)

fun StudySetModel.toEntity() = StudySetEntity(
    id = id,
    title = title,
    description = description,
    flashcardCount = flashcardCount,
    colorHex = colorHex,
    subjectName = subjectName,
    ownerId = ownerId,
    ownerUsername = ownerUsername,
    ownerAvatarUrl = ownerAvatarUrl,
    isPublic = isPublic
)