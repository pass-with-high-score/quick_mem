package com.pwhs.quickmem.domain.datasource

import com.pwhs.quickmem.domain.model.folder.GetFolderResponseModel

interface FolderRemoteDataSource {
    suspend fun getSearchResultFolders(
        token: String,
        title: String,
        page: Int?,
    ): List<GetFolderResponseModel>
}