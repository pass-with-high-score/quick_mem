package com.pwhs.quickmem

import android.app.Application
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        // Timber initialization
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

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

    private fun sendTokenToServer(token: String?) {
        // Implement logic to send token to your server
        if (token != null) {
           Timber.d("Token sent to server: $token")
        }
    }
}