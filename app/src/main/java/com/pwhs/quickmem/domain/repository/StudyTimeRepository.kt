package com.pwhs.quickmem.domain.repository

import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.domain.model.study_time.CreateStudyTimeModel
import com.pwhs.quickmem.domain.model.study_time.GetStudyTimeByStudySetResponseModel
import kotlinx.coroutines.flow.Flow

interface StudyTimeRepository {
    suspend fun getStudyTimeByStudySet(
        token: String,
        studySetId: String
    ): Flow<Resources<GetStudyTimeByStudySetResponseModel>>

    suspend fun createStudyTime(
        token: String,
        createStudyTimeModel: CreateStudyTimeModel
    ): Flow<Resources<Unit>>
}