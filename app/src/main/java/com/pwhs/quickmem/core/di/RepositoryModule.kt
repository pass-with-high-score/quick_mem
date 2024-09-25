package com.pwhs.quickmem.core.di

import com.pwhs.quickmem.data.remote.repository.AuthRepositoryImpl
import com.pwhs.quickmem.data.remote.repository.StudySetRepositoryImpl
import com.pwhs.quickmem.domain.repository.AuthRepository
import com.pwhs.quickmem.domain.repository.StudySetRepository
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

}