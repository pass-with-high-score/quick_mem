package com.pwhs.quickmem.domain.repository

import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.domain.model.flashcard.CreateFlashCardModel
import com.pwhs.quickmem.domain.model.flashcard.EditFlashCardModel
import com.pwhs.quickmem.domain.model.flashcard.FlashCardResponseModel
import kotlinx.coroutines.flow.Flow

interface FlashCardRepository {
    suspend fun createFlashCard(
        token: String,
        createFlashCardModel: CreateFlashCardModel
    ): Flow<Resources<FlashCardResponseModel>>

    suspend fun deleteFlashCard(
        token: String,
        id: String
    ): Flow<Resources<Unit>>

    suspend fun toggleStarredFlashCard(
        token: String,
        id: String,
        isStarred: Boolean
    ): Flow<Resources<Unit>>

    suspend fun updateFlashCard(
        token: String,
        id: String,
        editFlashCardModel: EditFlashCardModel
    ): Flow<Resources<FlashCardResponseModel>>
}