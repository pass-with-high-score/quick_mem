package com.pwhs.quickmem.data.local.repository

import com.pwhs.quickmem.data.local.dao.SearchQueryDao
import com.pwhs.quickmem.data.local.entities.SearchQueryEntity
import com.pwhs.quickmem.data.mapper.search.toEntity
import com.pwhs.quickmem.data.mapper.search.toModel
import com.pwhs.quickmem.domain.model.search.SearchQueryModel
import com.pwhs.quickmem.domain.repository.SearchQueryRepository
import javax.inject.Inject

class SearchQueryRepositoryImpl @Inject constructor(
    private val searchQueryDao: SearchQueryDao
) : SearchQueryRepository {
    override suspend fun addSearchQuery(query: String) {
        searchQueryDao.insertSearch(SearchQueryEntity(query = query))
    }

    override suspend fun deleteSearchQuery(searchQueryModel: SearchQueryModel) {
        searchQueryDao.deleteSearch(searchQueryModel.toEntity())
    }

    override suspend fun getRecentSearches(limit: Int): List<SearchQueryModel> {
        return searchQueryDao.getRecentSearches(limit).map { it.toModel() }.reversed()
    }

    override suspend fun clearSearchHistory() {
        searchQueryDao.clearSearchHistory()
    }
}