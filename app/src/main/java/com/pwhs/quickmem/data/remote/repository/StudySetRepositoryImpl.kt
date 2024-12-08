package com.pwhs.quickmem.data.remote.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.data.dto.study_set.MakeACopyStudySetRequestDto
import com.pwhs.quickmem.data.mapper.classes.toDto
import com.pwhs.quickmem.data.mapper.study_set.toDto
import com.pwhs.quickmem.data.mapper.study_set.toModel
import com.pwhs.quickmem.data.mapper.subject.toModel
import com.pwhs.quickmem.data.paging.StudySetBySubjectPagingSource
import com.pwhs.quickmem.data.paging.StudySetPagingSource
import com.pwhs.quickmem.data.remote.ApiService
import com.pwhs.quickmem.domain.datasource.SearchStudySetBySubjectRemoteDataSource
import com.pwhs.quickmem.domain.datasource.StudySetRemoteDataSource
import com.pwhs.quickmem.domain.model.classes.AddStudySetToClassesRequestModel
import com.pwhs.quickmem.domain.model.study_set.AddStudySetToClassRequestModel
import com.pwhs.quickmem.domain.model.study_set.AddStudySetToFolderRequestModel
import com.pwhs.quickmem.domain.model.study_set.AddStudySetToFoldersRequestModel
import com.pwhs.quickmem.domain.model.study_set.CreateStudySetByAIRequestModel
import com.pwhs.quickmem.domain.model.study_set.CreateStudySetRequestModel
import com.pwhs.quickmem.domain.model.study_set.CreateStudySetResponseModel
import com.pwhs.quickmem.domain.model.study_set.GetStudySetResponseModel
import com.pwhs.quickmem.domain.model.study_set.SaveRecentAccessStudySetRequestModel
import com.pwhs.quickmem.domain.model.study_set.UpdateStudySetRequestModel
import com.pwhs.quickmem.domain.model.study_set.UpdateStudySetResponseModel
import com.pwhs.quickmem.domain.model.subject.GetTop5SubjectResponseModel
import com.pwhs.quickmem.domain.repository.StudySetRepository
import com.pwhs.quickmem.presentation.app.search_result.study_set.enum.SearchResultCreatorEnum
import com.pwhs.quickmem.presentation.app.search_result.study_set.enum.SearchResultSizeEnum
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class StudySetRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val studySetRemoteDataSource: StudySetRemoteDataSource,
    private val searchStudySetBySubjectRemoteDataSource: SearchStudySetBySubjectRemoteDataSource
) : StudySetRepository {
    override suspend fun createStudySet(
        token: String,
        createStudySetRequestModel: CreateStudySetRequestModel
    ): Flow<Resources<CreateStudySetResponseModel>> {
        return flow {
            if (token.isEmpty()) {
                return@flow
            }
            emit(Resources.Loading())
            try {
                val response = apiService.createStudySet(token, createStudySetRequestModel.toDto())
                emit(Resources.Success(response.toModel()))
            } catch (e: Exception) {
                emit(Resources.Error(e.toString()))
            }
        }
    }

    override suspend fun getStudySetById(
        token: String,
        studySetId: String
    ): Flow<Resources<GetStudySetResponseModel>> {
        return flow {
            if (token.isEmpty()) {
                return@flow
            }
            emit(Resources.Loading())
            try {
                val response = apiService.getStudySetById(token, studySetId)
                response.flashcards = response.flashcards?.sortedByDescending { it.createdAt }
                emit(Resources.Success(response.toModel()))
            } catch (e: Exception) {
                Timber.e(e)
                emit(Resources.Error(e.toString()))
            }
        }
    }

    override suspend fun getStudySetsByOwnerId(
        token: String,
        ownerId: String,
        classId: String?,
        folderId: String?
    ): Flow<Resources<List<GetStudySetResponseModel>>> {
        return flow {
            if (token.isEmpty()) {
                return@flow
            }
            emit(Resources.Loading())
            try {
                val response = apiService.getStudySetsByOwnerId(token, ownerId, classId, folderId)
                emit(Resources.Success(response.map { it.toModel() }))
            } catch (e: Exception) {
                Timber.e(e)
                emit(Resources.Error(e.toString()))
            }
        }
    }

    override suspend fun updateStudySet(
        token: String,
        studySetId: String,
        updateStudySetRequestModel: UpdateStudySetRequestModel
    ): Flow<Resources<UpdateStudySetResponseModel>> {
        return flow {
            if (token.isEmpty()) {
                return@flow
            }
            emit(Resources.Loading())
            try {
                val response =
                    apiService.updateStudySet(token, studySetId, updateStudySetRequestModel.toDto())
                emit(Resources.Success(response.toModel()))
            } catch (e: Exception) {
                Timber.e(e)
                emit(Resources.Error(e.toString()))
            }
        }
    }

    override suspend fun deleteStudySet(
        token: String,
        studySetId: String
    ): Flow<Resources<Unit>> {
        return flow {
            if (token.isEmpty()) {
                return@flow
            }
            emit(Resources.Loading())
            try {
                apiService.deleteStudySet(token, studySetId)
                emit(Resources.Success(Unit))
            } catch (e: Exception) {
                Timber.e(e)
                emit(Resources.Error(e.toString()))
            }
        }
    }

    override suspend fun resetProgress(
        token: String,
        studySetId: String,
        resetType: String
    ): Flow<Resources<Unit>> {
        return flow {
            if (token.isEmpty()) {
                return@flow
            }
            emit(Resources.Loading())
            try {
                apiService.resetStudySetProgress(token, studySetId, resetType)
                emit(Resources.Success(Unit))
            } catch (e: Exception) {
                Timber.e(e)
                emit(Resources.Error(e.toString()))
            }
        }
    }

    override suspend fun addStudySetToFolder(
        token: String,
        addStudySetToFolderRequestModel: AddStudySetToFolderRequestModel
    ): Flow<Resources<Unit>> {
        return flow {
            if (token.isEmpty()) {
                return@flow
            }
            emit(Resources.Loading())
            try {
                apiService.addStudySetToFolder(token, addStudySetToFolderRequestModel.toDto())
                emit(Resources.Success(Unit))
            } catch (e: Exception) {
                Timber.e(e)
                emit(Resources.Error(e.toString()))
            }
        }
    }

    override suspend fun addStudySetToClass(
        token: String,
        addStudySetToClassRequestModel: AddStudySetToClassRequestModel
    ): Flow<Resources<Unit>> {
        return flow {
            if (token.isEmpty()) {
                return@flow
            }
            emit(Resources.Loading())
            try {
                apiService.addStudySetToClass(token, addStudySetToClassRequestModel.toDto())
                emit(Resources.Success(Unit))
            } catch (e: Exception) {
                Timber.e(e)
                emit(Resources.Error(e.toString()))
            }
        }
    }

    override suspend fun addStudySetToFolders(
        token: String,
        addStudySetToFoldersRequestModel: AddStudySetToFoldersRequestModel
    ): Flow<Resources<Unit>> {
        return flow {
            if (token.isEmpty()) {
                return@flow
            }
            emit(Resources.Loading())
            try {
                apiService.addStudySetToFolders(token, addStudySetToFoldersRequestModel.toDto())
                emit(Resources.Success(Unit))
            } catch (e: Exception) {
                Timber.e(e)
                emit(Resources.Error(e.toString()))
            }
        }
    }

    override suspend fun addStudySetToClasses(
        token: String,
        addStudySetToClassesRequestModel: AddStudySetToClassesRequestModel
    ): Flow<Resources<Unit>> {
        return flow {
            if (token.isEmpty()) {
                return@flow
            }
            emit(Resources.Loading())
            try {
                apiService.addStudySetToClasses(token, addStudySetToClassesRequestModel.toDto())
                emit(Resources.Success(Unit))
            } catch (e: Exception) {
                Timber.e(e)
                emit(Resources.Error(e.toString()))
            }
        }
    }

    override suspend fun getSearchResultStudySets(
        token: String,
        title: String,
        size: SearchResultSizeEnum,
        creatorType: SearchResultCreatorEnum?,
        page: Int,
        colorId: Int?,
        subjectId: Int?,
        isAIGenerated: Boolean?
    ): Flow<PagingData<GetStudySetResponseModel>> {
        if (token.isEmpty()) {
            return emptyFlow()
        }
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                StudySetPagingSource(
                    studySetRemoteDataSource,
                    token,
                    title,
                    size,
                    creatorType,
                    colorId,
                    subjectId,
                    isAIGenerated
                )
            }
        ).flow

    }

    override suspend fun getStudySetByCode(
        token: String,
        code: String
    ): Flow<Resources<GetStudySetResponseModel>> {
        return flow {
            if (token.isEmpty()) {
                return@flow
            }
            emit(Resources.Loading())
            try {
                val response = apiService.getStudySetByLinkCode(token, code)
                emit(Resources.Success(response.toModel()))
            } catch (e: Exception) {
                Timber.e(e)
                emit(Resources.Error(e.toString()))
            }
        }
    }

    override suspend fun makeCopyStudySet(
        token: String,
        studySetId: String,
        newOwnerId: String
    ): Flow<Resources<CreateStudySetResponseModel>> {
        return flow {
            if (token.isEmpty()) {
                return@flow
            }
            emit(Resources.Loading())
            try {
                val request =
                    MakeACopyStudySetRequestDto(studySetId = studySetId, newOwnerId = newOwnerId)
                val response = apiService.duplicateStudySet(token, request)
                emit(Resources.Success(response.toModel()))
            } catch (e: Exception) {
                Timber.e(e)
                emit(Resources.Error(e.toString()))
            }
        }
    }

    override suspend fun getTop5Subject(
        token: String
    ): Flow<Resources<List<GetTop5SubjectResponseModel>>> {
        return flow {
            if (token.isEmpty()) {
                return@flow
            }
            emit(Resources.Loading())
            try {
                val response = apiService.getTop5Subject(token)
                emit(Resources.Success(response.map { it.toModel() }))
            } catch (e: Exception) {
                Timber.e(e)
                emit(Resources.Error(e.toString()))
            }
        }
    }

    override suspend fun getStudySetBySubjectId(
        token: String,
        subjectId: Int,
        page: Int
    ): Flow<PagingData<GetStudySetResponseModel>> {
        if (token.isEmpty()) {
            return emptyFlow()
        }
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                StudySetBySubjectPagingSource(
                    searchStudySetBySubjectRemoteDataSource,
                    token,
                    subjectId,
                )
            }
        ).flow

    }

    override suspend fun saveRecentAccessStudySet(
        token: String,
        saveRecentAccessStudySetRequestModel: SaveRecentAccessStudySetRequestModel
    ): Flow<Resources<Unit>> {
        return flow {
            if (token.isEmpty()) {
                return@flow
            }
            emit(Resources.Loading())
            try {
                apiService.saveRecentStudySet(token, saveRecentAccessStudySetRequestModel.toDto())
                emit(Resources.Success(Unit))
            } catch (e: Exception) {
                Timber.e(e)
                emit(Resources.Error(e.toString()))
            }
        }
    }

    override suspend fun getRecentAccessStudySet(
        token: String,
        userId: String
    ): Flow<Resources<List<GetStudySetResponseModel>>> {
        return flow {
            if (token.isEmpty()) {
                return@flow
            }
            emit(Resources.Loading())
            try {
                val response = apiService.getRecentStudySet(token, userId)
                emit(Resources.Success(response.map { it.toModel() }))
            } catch (e: Exception) {
                Timber.e(e)
                emit(Resources.Error(e.toString()))
            }
        }
    }

    override suspend fun createStudySetByAI(
        token: String,
        createStudySetByAIRequestModel: CreateStudySetByAIRequestModel
    ): Flow<Resources<CreateStudySetResponseModel>> {
        return flow {
            if (token.isEmpty()) {
                return@flow
            }
            emit(Resources.Loading())
            try {
                val response =
                    apiService.createStudySetByAI(token, createStudySetByAIRequestModel.toDto())
                emit(Resources.Success(response.toModel()))
            } catch (e: Exception) {
                Timber.e(e)
                emit(Resources.Error(e.toString()))
            }
        }
    }
}