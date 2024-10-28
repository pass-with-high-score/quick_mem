package com.pwhs.quickmem.domain.repository

import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.domain.model.folder.CreateFolderRequestModel
import com.pwhs.quickmem.domain.model.folder.CreateFolderResponseModel
import kotlinx.coroutines.flow.Flow

interface FolderRepository {
    suspend fun createFolder(
        token: String,
        createFolderRequestModel: CreateFolderRequestModel
    ): Flow<Resources<CreateFolderResponseModel>>
}