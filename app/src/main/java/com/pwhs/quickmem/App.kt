package com.pwhs.quickmem

import android.app.Application
import com.google.firebase.messaging.FirebaseMessaging
import com.pwhs.quickmem.core.datastore.AppManager
import com.pwhs.quickmem.core.datastore.TokenManager
import com.pwhs.quickmem.data.dto.notification.TokenRequestDto
import com.pwhs.quickmem.data.remote.ApiService
import com.revenuecat.purchases.LogLevel
import com.revenuecat.purchases.Purchases
import com.revenuecat.purchases.PurchasesConfiguration
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var tokenManager: TokenManager

    @Inject
    lateinit var appManager: AppManager

    @Inject
    lateinit var apiService: ApiService

    override fun onCreate() {
        super.onCreate()

        // Timber initialization
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        Purchases.logLevel = LogLevel.DEBUG
        Purchases.configure(
            PurchasesConfiguration.Builder(
                context = this,
                apiKey = "goog_TBgLrymHTtfZJQzfyRseRIYlPER",
            ).build()
        )
        // Get FCM token
        getFCMToken()
    }

    private fun getFCMToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Timber.w("Fetching FCM registration token failed", task.exception)
                return@addOnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result
            Timber.d("FCM Token: $token")

            // Send token to your server
            sendTokenToServer(token)
        }
    }

    private fun sendTokenToServer(token: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val isLogged = appManager.isLoggedIn.firstOrNull() ?: false
            if (!isLogged) {
                return@launch
            } else {
                val accessToken = tokenManager.accessToken.firstOrNull() ?: ""
                val userId = appManager.userId.firstOrNull() ?: ""
                try {
                    apiService.sendDeviceToken(accessToken, TokenRequestDto(userId, token))
                    Timber.d("Token sent to server successfully.")
                } catch (e: Exception) {
                    Timber.e(e, "Error sending token to server")
                }
            }
        }
    }
}