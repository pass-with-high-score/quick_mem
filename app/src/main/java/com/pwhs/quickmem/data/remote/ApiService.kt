package com.pwhs.quickmem.data.remote

import retrofit2.http.POST

interface ApiService {
    @POST("auth/signup")
    suspend fun signUp()

    @POST("auth/login")
    suspend fun login()
}