package com.pwhs.quickmem.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pwhs.quickmem.data.local.dao.SearchQueryDao
import com.pwhs.quickmem.data.local.dao.StudySetDao
import com.pwhs.quickmem.data.local.entities.SearchQueryEntity
import com.pwhs.quickmem.data.local.entities.StudySetEntity

@Database(
    entities = [
        SearchQueryEntity::class,
        StudySetEntity::class
    ], version = 5, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun searchQueryDao(): SearchQueryDao
    abstract fun studySetDao(): StudySetDao

    companion object {
        const val DATABASE_NAME = "quickmem-db"
    }
}