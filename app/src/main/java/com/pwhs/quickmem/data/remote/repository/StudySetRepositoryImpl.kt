package com.pwhs.quickmem.data.remote.repository

import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.data.dto.study_set.CreateStudySetResponseDto
import com.pwhs.quickmem.data.mapper.study_set.toDto
import com.pwhs.quickmem.data.mapper.study_set.toModel
import com.pwhs.quickmem.data.remote.ApiService
import com.pwhs.quickmem.domain.model.study_set.CreateStudySetRequestModel
import com.pwhs.quickmem.domain.model.study_set.CreateStudySetResponseModel
import com.pwhs.quickmem.domain.model.study_set.GetStudySetResponseModel
import com.pwhs.quickmem.domain.repository.StudySetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class StudySetRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : StudySetRepository {
    override suspend fun createStudySet(
        token: String,
        createStudySetRequestModel: CreateStudySetRequestModel
    ): Flow<Resources<CreateStudySetResponseModel>> {
        return flow {
            emit(Resources.Loading())
            try {
                val response = apiService.createStudySet(token, createStudySetRequestModel.toDto())
                emit(Resources.Success(response.toModel()))
            } catch (e: Exception) {
                emit(Resources.Error(e.toString()))
            }
        }
    }

    override suspend fun getStudySetById(
        token: String,
        studySetId: String
    ): Flow<Resources<GetStudySetResponseModel>> {
        return flow {
            emit(Resources.Loading())
            try {
                val response = apiService.getStudySetById(token, studySetId)
                emit(Resources.Success(response.toModel()))
            } catch (e: Exception) {
                Timber.e(e)
                emit(Resources.Error(e.toString()))
            }
        }
    }

    override suspend fun getStudySetsByOwnerId(
        token: String,
        ownerId: String
    ): Flow<Resources<List<GetStudySetResponseModel>>> {
        return flow {
            emit(Resources.Loading())
            try {
                val response = apiService.getStudySetsByOwnerId(token, ownerId)
                emit(Resources.Success(response.map { it.toModel() }))
            } catch (e: Exception) {
                Timber.e(e)
                emit(Resources.Error(e.toString()))
            }
        }
    }
}