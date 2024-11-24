package com.pwhs.quickmem.domain.repository

import com.pwhs.quickmem.domain.model.study_set.StudySetModel

interface StudySetLocalRepository {
    suspend fun addStudySet(studySetModel: StudySetModel)
    suspend fun deleteStudySet(id: String)
    suspend fun getStudySet(limit: Int = 5): List<StudySetModel>
    suspend fun updateStudySet(
        id: String,
        title: String,
        description: String,
        colorHex: String,
        subjectName: String,
        isPublic: Boolean
    )
}