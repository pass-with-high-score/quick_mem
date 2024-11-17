package com.pwhs.quickmem.core.di

import com.pwhs.quickmem.data.local.repository.SearchQueryRepositoryImpl
import com.pwhs.quickmem.data.remote.repository.AuthRepositoryImpl
import com.pwhs.quickmem.data.remote.repository.ClassRepositoryImpl
import com.pwhs.quickmem.data.remote.repository.FlashCardRepositoryImpl
import com.pwhs.quickmem.data.remote.repository.FolderRepositoryImpl
import com.pwhs.quickmem.data.remote.repository.NotificationRepositoryImpl
import com.pwhs.quickmem.data.remote.repository.StreakRepositoryImpl
import com.pwhs.quickmem.data.remote.repository.StudySetRepositoryImpl
import com.pwhs.quickmem.data.remote.repository.UploadImageRepositoryImpl
import com.pwhs.quickmem.domain.repository.AuthRepository
import com.pwhs.quickmem.domain.repository.ClassRepository
import com.pwhs.quickmem.domain.repository.FlashCardRepository
import com.pwhs.quickmem.domain.repository.FolderRepository
import com.pwhs.quickmem.domain.repository.NotificationRepository
import com.pwhs.quickmem.domain.repository.SearchQueryRepository
import com.pwhs.quickmem.domain.repository.StreakRepository
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

    @Binds
    abstract fun bindClassRepository(
        classRepositoryImpl: ClassRepositoryImpl
    ): ClassRepository

    @Binds
    abstract fun bindFolderRepository(
        folderRepositoryImpl: FolderRepositoryImpl
    ): FolderRepository

    @Binds
    abstract fun bindStreakRepository(
        streakRepositoryImpl: StreakRepositoryImpl
    ): StreakRepository

    @Binds
    abstract fun bindNotificationRepository(
        notificationRepositoryImpl: NotificationRepositoryImpl
    ): NotificationRepository

    @Binds
    abstract fun binSearchQueryRepository(
        searchQueryRepositoryImpl: SearchQueryRepositoryImpl
    ): SearchQueryRepository
}