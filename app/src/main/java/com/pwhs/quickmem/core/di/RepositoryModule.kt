package com.pwhs.quickmem.core.di

import com.pwhs.quickmem.data.remote.repository.AuthRepositoryImpl
import com.pwhs.quickmem.data.remote.repository.FlashCardRepositoryImpl
import com.pwhs.quickmem.data.remote.repository.StudySetRepositoryImpl
import com.pwhs.quickmem.data.remote.repository.UploadImageRepositoryImpl
import com.pwhs.quickmem.domain.repository.AuthRepository
import com.pwhs.quickmem.domain.repository.FlashCardRepository
import com.pwhs.quickmem.domain.repository.StudySetRepository
import com.pwhs.quickmem.domain.repository.UploadImageRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {
    @Binds
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    abstract fun bindStudySetRepository(
        studySetRepositoryImpl: StudySetRepositoryImpl
    ): StudySetRepository

    @Binds
    abstract fun bindFlashCardRepository(
        flashCardRepositoryImpl: FlashCardRepositoryImpl
    ): FlashCardRepository

    @Binds
    abstract fun bindUploadImageRepository(
        uploadImageRepositoryImpl: UploadImageRepositoryImpl
    ): UploadImageRepository

}