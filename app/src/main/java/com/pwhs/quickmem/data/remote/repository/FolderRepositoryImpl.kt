package com.pwhs.quickmem.data.remote.repository

import com.pwhs.quickmem.data.remote.ApiService
import com.pwhs.quickmem.domain.repository.FolderRepository
import javax.inject.Inject

class FolderRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : FolderRepository {
}