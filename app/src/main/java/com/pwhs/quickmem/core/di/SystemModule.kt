package com.pwhs.quickmem.core.di

import android.content.Context
import com.pwhs.quickmem.core.schedule_alarm.AlarmScheduler
import com.pwhs.quickmem.core.schedule_alarm.AndroidAlarmScheduler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SystemModule {
    @Provides
    @Singleton
    fun provideAlarmScheduler(@ApplicationContext context: Context): AlarmScheduler {
        return AndroidAlarmScheduler(context)
    }
}