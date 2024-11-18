package com.pwhs.quickmem.data.mapper.study_set

import com.pwhs.quickmem.data.dto.study_set.GetMakeACopyResponseDto
import com.pwhs.quickmem.domain.model.study_set.GetMakeACopyResponseModel

fun GetMakeACopyResponseDto.toGetMakeACopyResponseModel(): GetMakeACopyResponseModel {
    return GetMakeACopyResponseModel(
        id = id,
        title = title,
        description = description,
        isPublic = isPublic,
        isAIGenerated = isAIGenerated,
        createdAt = createdAt,
        updatedAt = updatedAt,
        linkShareCode = linkShareCode,
        subject = subject.toSubjectModel(),
        owner = owner.toOwnerModel(),
        color = color.toColorModel(),
        flashcardCount = flashcardCount,
        flashcards = flashcards.map { it.toFlashcardModel() }
    )
}

fun GetMakeACopyResponseDto.SubjectDto.toSubjectModel(): GetMakeACopyResponseModel.Subject {
    return GetMakeACopyResponseModel.Subject(
        id = id,
        name = name
    )
}

fun GetMakeACopyResponseDto.OwnerDto.toOwnerModel(): GetMakeACopyResponseModel.Owner {
    return GetMakeACopyResponseModel.Owner(
        id = id,
        username = username,
        avatarUrl = avatarUrl,
        role = role
    )
}

fun GetMakeACopyResponseDto.ColorDto.toColorModel(): GetMakeACopyResponseModel.Color {
    return GetMakeACopyResponseModel.Color(
        id = id,
        name = name,
        hexValue = hexValue
    )
}

fun GetMakeACopyResponseDto.FlashcardDto.toFlashcardModel(): GetMakeACopyResponseModel.Flashcard {
    return GetMakeACopyResponseModel.Flashcard(
        id = id,
        term = term,
        definition = definition,
        definitionImageUrl = definitionImageUrl,
        hint = hint,
        explanation = explanation
    )
}
