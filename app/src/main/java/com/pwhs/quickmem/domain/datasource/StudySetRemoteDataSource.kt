package com.pwhs.quickmem.domain.datasource

import com.pwhs.quickmem.domain.model.study_set.GetStudySetResponseModel
import com.pwhs.quickmem.presentation.app.search_result.study_set.enum.SearchResultCreatorEnum
import com.pwhs.quickmem.presentation.app.search_result.study_set.enum.SearchResultSizeEnum

interface StudySetRemoteDataSource {
    suspend fun getSearchResultStudySets(
        token: String,
        title: String,
        size: SearchResultSizeEnum,
        creatorType: SearchResultCreatorEnum?,
        page: Int,
        colorId: Int?,
        subjectId: Int?,
    ): List<GetStudySetResponseModel>
}