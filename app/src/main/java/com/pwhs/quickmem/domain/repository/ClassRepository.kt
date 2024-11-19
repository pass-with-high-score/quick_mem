package com.pwhs.quickmem.domain.repository

import androidx.paging.PagingData
import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.domain.model.classes.CreateClassRequestModel
import com.pwhs.quickmem.domain.model.classes.CreateClassResponseModel
import com.pwhs.quickmem.domain.model.classes.ExitClassRequestModel
import com.pwhs.quickmem.domain.model.classes.GetClassByOwnerResponseModel
import com.pwhs.quickmem.domain.model.classes.GetClassDetailResponseModel
import com.pwhs.quickmem.domain.model.classes.JoinClassRequestModel
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
        title: String,
        page: Int?,
    ): Flow<PagingData<GetClassByOwnerResponseModel>>

    suspend fun getClassByCode(
        token: String,
        userId: String,
        classCode: String
    ): Flow<Resources<GetClassDetailResponseModel>>

    suspend fun joinClass(
        token: String,
        joinClassRequestModel: JoinClassRequestModel
    ): Flow<Resources<Unit>>

    suspend fun exitClass(
        token: String,
        exitClassRequestModel: ExitClassRequestModel
    ): Flow<Resources<Unit>>
}