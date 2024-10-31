package com.pwhs.quickmem.domain.repository

import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.domain.model.folder.CreateFolderRequestModel
import com.pwhs.quickmem.domain.model.folder.CreateFolderResponseModel
import com.pwhs.quickmem.domain.model.folder.GetFolderResponseModel
import com.pwhs.quickmem.domain.model.folder.UpdateFolderRequestModel
import com.pwhs.quickmem.domain.model.folder.UpdateFolderResponseModel
import kotlinx.coroutines.flow.Flow

interface FolderRepository {
    suspend fun createFolder(
        token: String,
        createFolderRequestModel: CreateFolderRequestModel
    ): Flow<Resources<CreateFolderResponseModel>>

    suspend fun getFolderById(
        token: String,
        folderId: String
    ): Flow<Resources<GetFolderResponseModel>>

    suspend fun updateFolder(
        token: String,
        folderId: String,
        updateFolderRequestModel: UpdateFolderRequestModel
    ): Flow<Resources<UpdateFolderResponseModel>>
}