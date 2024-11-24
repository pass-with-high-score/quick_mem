package com.pwhs.quickmem.data.local.repository

import com.pwhs.quickmem.data.local.dao.StudySetDao
import com.pwhs.quickmem.data.mapper.study_set.toEntity
import com.pwhs.quickmem.data.mapper.study_set.toModel
import com.pwhs.quickmem.domain.model.study_set.StudySetModel
import com.pwhs.quickmem.domain.repository.StudySetLocalRepository
import javax.inject.Inject

class StudySetLocalRepositoryImpl @Inject constructor(
    private val studySetDao: StudySetDao
) : StudySetLocalRepository {
    override suspend fun addStudySet(studySetModel: StudySetModel) {
        studySetDao.insertStudySet(studySetModel.toEntity())
    }

    override suspend fun deleteStudySet(id: String) {
        studySetDao.deleteStudySet(id)
    }

    override suspend fun getStudySet(limit: Int): List<StudySetModel> {
        return studySetDao.getStudySet(limit).map { it.toModel() }.reversed()
    }

    override suspend fun updateStudySet(
        id: String,
        title: String,
        description: String,
        colorHex: String,
        subjectName: String,
        isPublic: Boolean
    ) {
        studySetDao.updateStudySet(id, title, description, colorHex, subjectName, isPublic)
    }
}