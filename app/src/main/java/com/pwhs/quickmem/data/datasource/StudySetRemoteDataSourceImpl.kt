package com.pwhs.quickmem.data.datasource

import com.pwhs.quickmem.data.mapper.study_set.toModel
import com.pwhs.quickmem.data.remote.ApiService
import com.pwhs.quickmem.domain.datasource.StudySetRemoteDataSource
import com.pwhs.quickmem.domain.model.study_set.GetStudySetResponseModel
import com.pwhs.quickmem.presentation.app.search_result.study_set.enum.SearchResultCreatorEnum
import com.pwhs.quickmem.presentation.app.search_result.study_set.enum.SearchResultSizeEnum
import timber.log.Timber

class StudySetRemoteDataSourceImpl(
    private val apiService: ApiService
) : StudySetRemoteDataSource {
    override suspend fun getSearchResultStudySets(
        token: String,
        title: String,
        size: SearchResultSizeEnum,
        creatorType: SearchResultCreatorEnum?,
        page: Int,
        colorId: Int?,
        subjectId: Int?,
        isAIGenerated: Boolean?
    ): List<GetStudySetResponseModel> {
        try {
            val response =
                apiService.searchStudySet(
                    token,
                    title,
                    size.query,
                    creatorType?.query,
                    page,
                    colorId,
                    subjectId,
                    isAIGenerated
                )
            return response.map { it.toModel() }
        } catch (e: Exception) {
            Timber.e(e.toString())
            throw e
        }
    }
}