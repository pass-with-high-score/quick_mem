package com.pwhs.quickmem.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pwhs.quickmem.data.local.entities.StudySetEntity

@Dao
interface StudySetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudySet(studySetEntity: StudySetEntity)

    @Query("DELETE FROM study_set WHERE id = :id")
    suspend fun deleteStudySet(id: String)

    @Query("SELECT * FROM study_set LIMIT :limit")
    suspend fun getStudySet(limit: Int = 5): List<StudySetEntity>

    @Query(
        "UPDATE study_set " +
                "SET title = :title, " +
                "description = :description, " +
                "colorHex = :colorHex, " +
                "subjectName = :subjectName, " +
                "isPublic = :isPublic " +
                "WHERE id = :id"
    )
    suspend fun updateStudySet(
        id: String,
        title: String,
        description: String,
        colorHex: String,
        subjectName: String,
        isPublic: Boolean
    )

}