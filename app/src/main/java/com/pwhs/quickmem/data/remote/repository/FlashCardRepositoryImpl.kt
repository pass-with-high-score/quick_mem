package com.pwhs.quickmem.data.remote.repository

import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.data.dto.flashcard.FlipFlashCardDto
import com.pwhs.quickmem.data.dto.flashcard.ToggleStarredFlashCardDto
import com.pwhs.quickmem.data.mapper.flashcard.toDto
import com.pwhs.quickmem.data.mapper.flashcard.toModel
import com.pwhs.quickmem.data.remote.ApiService
import com.pwhs.quickmem.domain.model.flashcard.CreateFlashCardModel
import com.pwhs.quickmem.domain.model.flashcard.EditFlashCardModel
import com.pwhs.quickmem.domain.model.flashcard.FlashCardResponseModel
import com.pwhs.quickmem.domain.model.flashcard.UpdateFlashCardResponseModel
import com.pwhs.quickmem.domain.repository.FlashCardRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class FlashCardRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : FlashCardRepository {
    override suspend fun createFlashCard(
        token: String,
        createFlashCardModel: CreateFlashCardModel
    ): Flow<Resources<FlashCardResponseModel>> {
        return flow {
            emit(Resources.Loading(true))
            try {
                val response = apiService.createFlashCard(token, createFlashCardModel.toDto())
                Timber.d("createFlashCard: $response")
                emit(Resources.Success(response.toModel()))
            } catch (e: Exception) {
                Timber.e(e)
                emit(Resources.Error(e.toString()))
            }
        }
    }

    override suspend fun deleteFlashCard(
        token: String,
        id: String
    ): Flow<Resources<Unit>> {
        return flow {
            emit(Resources.Loading(true))
            try {
                val response = apiService.deleteFlashCard(token, id)
                Timber.d("deleteFlashCard: $response")
                emit(Resources.Success(null))
            } catch (e: HttpException) {
                Timber.e(e)
                emit(Resources.Error(e.toString()))
            } catch (e: IOException) {
                Timber.e(e)
                emit(Resources.Error(e.toString()))
            }
        }
    }

    override suspend fun toggleStarredFlashCard(
        token: String,
        id: String,
        isStarred: Boolean
    ): Flow<Resources<UpdateFlashCardResponseModel>> {
        return flow {
            emit(Resources.Loading(true))
            try {
                val response = apiService.toggleStarredFlashCard(
                    token, id,
                    ToggleStarredFlashCardDto(isStarred)
                )
                Timber.d("toggleStarredFlashCard: $response")
                emit(Resources.Success(response.toModel()))
            } catch (e: HttpException) {
                Timber.e(e)
                emit(Resources.Error(e.toString()))
            } catch (e: IOException) {
                Timber.e(e)
                emit(Resources.Error(e.toString()))
            }
        }
    }

    override suspend fun updateFlashCard(
        token: String,
        id: String,
        editFlashCardModel: EditFlashCardModel
    ): Flow<Resources<FlashCardResponseModel>> {
        return flow {
            emit(Resources.Loading(true))
            Timber.d("updateFlashCard: $editFlashCardModel")
            try {
                val response = apiService.updateFlashCard(
                    token,
                    id,
                    editFlashCardModel.toDto()
                )
                Timber.d("updateFlashCard: $response")
                emit(Resources.Success(response.toModel()))
            } catch (e: HttpException) {
                Timber.e(e)
                emit(Resources.Error(e.toString()))
            } catch (e: IOException) {
                Timber.e(e)
                emit(Resources.Error(e.toString()))
            }
        }
    }

    override suspend fun updateFlipFlashCard(
        token: String,
        id: String,
        flipStatus: String
    ): Flow<Resources<UpdateFlashCardResponseModel>> {
        return flow {
            try {
                val response =
                    apiService.updateFlipFlashCard(token, id, FlipFlashCardDto(flipStatus))
                Timber.d("updateFlipFlashCard: $response")
                emit(Resources.Success(response.toModel()))
            } catch (e: HttpException) {
                Timber.e(e)
                emit(Resources.Error(e.toString()))
            } catch (e: IOException) {
                Timber.e(e)
                emit(Resources.Error(e.toString()))
            }
        }
    }

    override suspend fun getFlashCardsByStudySetId(
        token: String,
        studySetId: String
    ): Flow<Resources<List<FlashCardResponseModel>>> {
        return flow {
            emit(Resources.Loading(true))
            try {
                val response = apiService.getFlashCardsByStudySetId(token, studySetId)
                Timber.d("getFlashCardsByStudySetId: $response")
                emit(Resources.Success(response.map { it.toModel() }))
            } catch (e: HttpException) {
                Timber.e(e)
                emit(Resources.Error(e.toString()))
            } catch (e: IOException) {
                Timber.e(e)
                emit(Resources.Error(e.toString()))
            }
        }
    }
}