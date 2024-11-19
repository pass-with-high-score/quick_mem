package com.pwhs.quickmem.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pwhs.quickmem.data.local.dao.SearchQueryDao
import com.pwhs.quickmem.data.local.entities.SearchQueryEntity

@Database(entities = [SearchQueryEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun searchQueryDao(): SearchQueryDao

    companion object {
        const val DATABASE_NAME = "quickmem-db"
    }
}