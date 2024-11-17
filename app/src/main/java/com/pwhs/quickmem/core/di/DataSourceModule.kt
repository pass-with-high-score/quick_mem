package com.pwhs.quickmem.core.di

import com.pwhs.quickmem.data.datasource.FolderRemoteDataSourceImpl
import com.pwhs.quickmem.data.datasource.StudySetRemoteDataSourceImpl
import com.pwhs.quickmem.data.remote.ApiService
import com.pwhs.quickmem.domain.datasource.FolderRemoteDataSource
import com.pwhs.quickmem.domain.datasource.StudySetRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Provides
    @Singleton
    fun provideStudySetRemoteDataSource(
        apiService: ApiService
    ): StudySetRemoteDataSource {
        return StudySetRemoteDataSourceImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideFolderRemoteDataSource(
        apiService: ApiService
    ): FolderRemoteDataSource {
        return FolderRemoteDataSourceImpl(apiService)
    }
}