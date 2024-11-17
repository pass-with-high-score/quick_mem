package com.pwhs.quickmem.domain.repository

import com.pwhs.quickmem.domain.model.search.SearchQueryModel

interface SearchQueryRepository {
    suspend fun addSearchQuery(query: String)
    suspend fun deleteSearchQuery(searchQueryModel: SearchQueryModel)
    suspend fun getRecentSearches(limit: Int = 5): List<SearchQueryModel>
    suspend fun clearSearchHistory()
}