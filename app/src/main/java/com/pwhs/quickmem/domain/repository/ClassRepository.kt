package com.pwhs.quickmem.domain.repository

import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.domain.model.classes.CreateClassRequestModel
import com.pwhs.quickmem.domain.model.classes.CreateClassResponseModel
import com.pwhs.quickmem.domain.model.classes.GetClassByOwnerResponseModel
import com.pwhs.quickmem.domain.model.classes.GetClassDetailResponseModel
import com.pwhs.quickmem.domain.model.classes.UpdateClassRequestModel
import com.pwhs.quickmem.domain.model.classes.UpdateClassResponseModel
import kotlinx.coroutines.flow.Flow

interface ClassRepository {
    suspend fun createClass(
        token: String,
        createClassRequestModel: CreateClassRequestModel
    ): Flow<Resources<CreateClassResponseModel>>

    suspend fun getClassById(
        token: String,
        classId: String
    ): Flow<Resources<GetClassDetailResponseModel>>

    suspend fun getClassByOwnerId(
        token: String,
        userId: String,
        folderId: String?,
        studySetId: String?
    ): Flow<Resources<List<GetClassByOwnerResponseModel>>>

    suspend fun updateClass(
        token: String,
        classId: String,
        updateClassRequestModel: UpdateClassRequestModel
    ): Flow<Resources<UpdateClassResponseModel>>

    suspend fun deleteClass(
        token: String,
        classId: String
    ): Flow<Resources<Unit>>

    suspend fun getSearchResultClasses(
        token: String,
        query: String,
        size: Int?,
        page: Int?,
    ): Flow<Resources<List<GetClassByOwnerResponseModel>>>
}