package com.pwhs.quickmem.data.remote.repository

import com.pwhs.quickmem.data.remote.ApiService
import com.pwhs.quickmem.domain.repository.ClassRepository
import javax.inject.Inject

class ClassRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ClassRepository {
}