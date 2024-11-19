package com.pwhs.quickmem.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pwhs.quickmem.data.local.entities.SearchQueryEntity

@Dao
interface SearchQueryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearch(searchQueryEntity: SearchQueryEntity)

    @Delete
    suspend fun deleteSearch(searchQueryEntity: SearchQueryEntity)

    @Query("SELECT * FROM recent_search ORDER BY timestamp DESC LIMIT :limit")
    suspend fun getRecentSearches(limit: Int = 5): List<SearchQueryEntity>

    @Query("DELETE FROM recent_search WHERE `query` = :query")
    suspend fun deleteSearch(query: String)

    @Query("DELETE FROM recent_search")
    suspend fun clearSearchHistory()
}