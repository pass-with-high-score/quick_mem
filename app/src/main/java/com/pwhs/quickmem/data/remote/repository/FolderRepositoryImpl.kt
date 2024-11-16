package com.pwhs.quickmem.data.remote.repository

import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.data.mapper.folder.toDto
import com.pwhs.quickmem.data.mapper.folder.toModel
import com.pwhs.quickmem.data.remote.ApiService
import com.pwhs.quickmem.domain.model.folder.AddFolderToClassRequestModel
import com.pwhs.quickmem.domain.model.folder.CreateFolderRequestModel
import com.pwhs.quickmem.domain.model.folder.CreateFolderResponseModel
import com.pwhs.quickmem.domain.model.folder.GetFolderResponseModel
import com.pwhs.quickmem.domain.model.folder.UpdateFolderRequestModel
import com.pwhs.quickmem.domain.model.folder.UpdateFolderResponseModel
import com.pwhs.quickmem.domain.repository.FolderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class FolderRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : FolderRepository {
    override suspend fun createFolder(
        token: String,
        createFolderRequestModel: CreateFolderRequestModel
    ): Flow<Resources<CreateFolderResponseModel>> {
        return flow {
            emit(Resources.Loading())
            try {
                val response = apiService.createFolder(token, createFolderRequestModel.toDto())
                emit(Resources.Success(response.toModel()))
            } catch (e: Exception) {
                emit(Resources.Error(e.toString()))
            }
        }
    }

    override suspend fun getFolderById(
        token: String,
        folderId: String
    ): Flow<Resources<GetFolderResponseModel>> {
        return flow {
            emit(Resources.Loading())
            try {
                val response = apiService.getFolderById(token, folderId)
                emit(Resources.Success(response.toModel()))
            } catch (e: Exception) {
                Timber.e(e)
                emit(Resources.Error(e.toString()))
            }
        }
    }

    override suspend fun updateFolder(
        token: String,
        folderId: String,
        updateFolderRequestModel: UpdateFolderRequestModel
    ): Flow<Resources<UpdateFolderResponseModel>> {
        return flow {
            emit(Resources.Loading())
            try {
                val response =
                    apiService.updateFolder(token, folderId, updateFolderRequestModel.toDto())
                emit(Resources.Success(response.toModel()))
            } catch (e: Exception) {
                Timber.e(e)
                emit(Resources.Error(e.toString()))
            }
        }
    }

    override suspend fun getFoldersByUserId(
        token: String,
        userId: String,
        classId: String?,
        studySetId: String?
    ): Flow<Resources<List<GetFolderResponseModel>>> {
        return flow {
            emit(Resources.Loading())
            try {
                val response = apiService.getFoldersByOwnerId(token, userId, classId, studySetId)
                emit(Resources.Success(response.map { it.toModel() }))
            } catch (e: Exception) {
                Timber.e(e)
                emit(Resources.Error(e.toString()))
            }
        }
    }

    override suspend fun deleteFolder(token: String, folderId: String): Flow<Resources<Unit>> {
        return flow {
            emit(Resources.Loading())
            try {
                apiService.deleteFolder(token, folderId)
                emit(Resources.Success(Unit))
            } catch (e: Exception) {
                Timber.e(e)
                emit(Resources.Error(e.toString()))
            }
        }
    }

    override suspend fun addFolderToClass(
        token: String,
        addFolderToClassRequestModel: AddFolderToClassRequestModel
    ): Flow<Resources<Unit>> {
        return flow {
            emit(Resources.Loading())
            try {
                apiService.addFolderToClass(token, addFolderToClassRequestModel.toDto())
                emit(Resources.Success(Unit))
            } catch (e: Exception) {
                Timber.e(e)
                emit(Resources.Error(e.toString()))
            }
        }
    }

    override suspend fun getSearchResultFolders(
        token: String,
        query: String,
        size: Int,
        page: Int
    ): Flow<Resources<List<GetFolderResponseModel>>> {
        return flow {
            emit(Resources.Loading())
            try {
                val response = apiService.searchFolder(token, query, size, page)
                emit(Resources.Success(response.map { it.toModel() }))
            } catch (e: Exception) {
                Timber.e(e)
                emit(Resources.Error(e.toString()))
            }
        }
    }
}