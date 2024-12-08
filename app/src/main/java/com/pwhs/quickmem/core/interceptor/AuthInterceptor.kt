package com.pwhs.quickmem.core.interceptor

import android.content.Context
import com.pwhs.quickmem.core.datastore.TokenManager
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val context: Context,
    private val tokenManager: TokenManager,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(addAuthorizationHeader(request))

        if (response.code == 401) {
            synchronized(this) {
                val newToken = runBlocking { refreshToken() }
                return if (newToken != null) {
                    chain.proceed(addAuthorizationHeader(request, newToken))
                } else {
                    // Log out the user
                    runBlocking { tokenManager.clearTokens() }
                    response
                }
            }
        }

        return response
    }

    private fun addAuthorizationHeader(request: Request, token: String? = null): Request {
        val accessToken = token ?: runBlocking { tokenManager.accessToken.firstOrNull() } ?: ""
        return request.newBuilder()
            .header("Authorization", accessToken)
            .build()
    }

    private suspend fun refreshToken(): String? {
        // Call the refresh token API
        return null
    }
}