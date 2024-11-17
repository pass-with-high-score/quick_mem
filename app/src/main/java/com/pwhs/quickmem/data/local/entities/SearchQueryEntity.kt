package com.pwhs.quickmem.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recent_search")
data class SearchQueryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val query: String,
    val timestamp: Long = System.currentTimeMillis()
)
