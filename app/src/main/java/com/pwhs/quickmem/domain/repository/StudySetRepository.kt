package com.pwhs.quickmem.domain.repository

import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.domain.model.study_set.CreateStudySetRequestModel
import com.pwhs.quickmem.domain.model.study_set.CreateStudySetResponseModel
import kotlinx.coroutines.flow.Flow

interface StudySetRepository {
    suspend fun createStudySet(
        token: String,
        createStudySetRequestModel: CreateStudySetRequestModel
    ): Flow<Resources<CreateStudySetResponseModel>>
}