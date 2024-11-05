package com.pwhs.quickmem.domain.repository

import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.domain.model.classes.AddFoldersToClassRequestModel
import com.pwhs.quickmem.domain.model.classes.AddFoldersToClassResponseModel
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

    suspend fun getClassByID(
        token: String,
        classId: String
    ): Flow<Resources<GetClassDetailResponseModel>>

    suspend fun getClassByOwnerID(
        token: String,
        userId: String
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

    suspend fun addFoldersToClass(
        token: String,
        addFoldersToClassRequestModel: AddFoldersToClassRequestModel
    ):Flow<Resources<AddFoldersToClassResponseModel>>
}