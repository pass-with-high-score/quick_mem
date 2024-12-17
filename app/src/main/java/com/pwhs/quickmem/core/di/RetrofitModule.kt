package com.pwhs.quickmem.core.di

import com.pwhs.quickmem.BuildConfig
import com.pwhs.quickmem.core.utils.AppConstant.BASE_URL
import com.pwhs.quickmem.data.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun provideQuickMemApi(): ApiService {
        val versionName = BuildConfig.VERSION_NAME
        val versionCode = BuildConfig.VERSION_CODE
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.MINUTES)
                    .readTimeout(10, TimeUnit.MINUTES)
                    .writeTimeout(10, TimeUnit.MINUTES)
                    .addInterceptor(HttpLoggingInterceptor().apply {
                        level =
                            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
                    })
                    .addInterceptor(
                        Interceptor { chain ->
                            val request: Request = chain.request()
                                .newBuilder()
                                .header("accept", "application/json")
                                .header("version-name", versionName)
                                .header("version-code", versionCode.toString())
                                .build()
                            chain.proceed(request)
                        }
                    )
                    .build()
            )
            .build()
            .create(ApiService::class.java)
    }
}