package com.pwhs.quickmem.data.remote.repository

import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.data.mapper.classes.toDto
import com.pwhs.quickmem.data.mapper.classes.toModel
import com.pwhs.quickmem.data.remote.ApiService
import com.pwhs.quickmem.domain.model.classes.CreateClassRequestModel
import com.pwhs.quickmem.domain.model.classes.CreateClassResponseModel
import com.pwhs.quickmem.domain.model.classes.GetClassByOwnerResponseModel
import com.pwhs.quickmem.domain.model.classes.GetClassDetailResponseModel
import com.pwhs.quickmem.domain.model.classes.UpdateClassRequestModel
import com.pwhs.quickmem.domain.model.classes.UpdateClassResponseModel
import com.pwhs.quickmem.domain.repository.ClassRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class ClassRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ClassRepository {
    override suspend fun createClass(
        token: String,
        createClassRequestModel: CreateClassRequestModel
    ): Flow<Resources<CreateClassResponseModel>> {
        return flow {
            emit(Resources.Loading(true))
            try {
                val response = apiService.createClass(
                    token, createClassRequestModel.toDto()
                )
                Timber.d("createClass: $response")
                emit(Resources.Success(response.toModel()))
            } catch (e: Exception) {
                Timber.e(e)
                emit(Resources.Error(e.toString()))
            }
        }
    }

    override suspend fun getClassById(
        token: String,
        classId: String
    ): Flow<Resources<GetClassDetailResponseModel>> {
        return flow {
            emit(Resources.Loading())
            try {
                val response = apiService.getClassByID(
                    token,
                    classId
                )
                Timber.d("getClassByIddddd: ${response.studySets?.firstOrNull()?.flashCardCount}")
                emit(Resources.Success(response.toModel()))
            } catch (e: Exception) {
                Timber.e(e)
                emit(Resources.Error(e.toString()))
            }
        }

    }

    override suspend fun getClassByOwnerId(
        token: String,
        userId: String,
        folderId: String?,
        studySetId: String?
    ): Flow<Resources<List<GetClassByOwnerResponseModel>>> {
        return flow {
            emit(Resources.Loading())
            try {
                val response = apiService.getClassByOwnerID(token, userId, folderId, studySetId)
                Timber.d("listClass: $response")
                emit(Resources.Success(response.map { it.toModel() }))
            } catch (e: Exception) {
                Timber.e(e)
                emit(Resources.Error(e.toString()))
            }
        }
    }

    override suspend fun updateClass(
        token: String,
        classId: String,
        updateClassRequestModel: UpdateClassRequestModel
    ): Flow<Resources<UpdateClassResponseModel>> {
        return flow {
            emit(Resources.Loading())
            try {
                val response = apiService.updateClass(
                    token, classId, updateClassRequestModel.toDto()
                )
                Timber.d("updateClass: $response")
                emit(Resources.Success(response.toModel()))
            } catch (e: Exception) {
                Timber.e(e)
                emit(Resources.Error(e.toString()))
            }
        }
    }

    override suspend fun deleteClass(token: String, classId: String): Flow<Resources<Unit>> {
        return flow {
            emit(Resources.Loading())
            try {
                apiService.deleteClass(
                    token, classId
                )
                emit(Resources.Success(Unit))
            } catch (e: Exception) {
                Timber.e(e)
                emit(Resources.Error(e.toString()))
            }
        }
    }

    override suspend fun getSearchResultClasses(
        token: String,
        title: String,
        page: Int?
    ): Flow<Resources<List<GetClassByOwnerResponseModel>>> {
        return flow {
            emit(Resources.Loading())
            try {
                val response = apiService.searchClass(
                    token, title, page
                )
                Timber.d("getSearchResultClasses: $response")
                emit(Resources.Success(response.map { it.toModel() }))
            } catch (e: Exception) {
                Timber.e(e)
                emit(Resources.Error(e.toString()))
            }
        }
    }


}