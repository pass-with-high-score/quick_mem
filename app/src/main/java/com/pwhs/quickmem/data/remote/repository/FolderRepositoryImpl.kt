package com.pwhs.quickmem.data.remote.repository

import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.data.mapper.folder.toDto
import com.pwhs.quickmem.data.mapper.folder.toModel
import com.pwhs.quickmem.data.remote.ApiService
import com.pwhs.quickmem.domain.model.folder.CreateFolderRequestModel
import com.pwhs.quickmem.domain.model.folder.CreateFolderResponseModel
import com.pwhs.quickmem.domain.model.folder.GetFolderResponseModel
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
}