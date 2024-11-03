package com.pwhs.quickmem.domain.repository

import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.domain.model.classes.CreateClassRequestModel
import com.pwhs.quickmem.domain.model.classes.CreateClassResponseModel
import com.pwhs.quickmem.domain.model.classes.GetClassDetailResponseModel
import kotlinx.coroutines.flow.Flow

interface ClassRepository {
    suspend fun createClass(
        token: String,
        createClassRequestModel: CreateClassRequestModel
    ): Flow<Resources<CreateClassResponseModel>>

    suspend fun getClassByID(
        token: String,
        classId: String
    ): Flow<Resources<GetClassDetailResponseModel>>

    suspend fun getClassByOwnerID(
        token: String,
        userId: String
    ): Flow<Resources<List<GetClassDetailResponseModel>>>
}