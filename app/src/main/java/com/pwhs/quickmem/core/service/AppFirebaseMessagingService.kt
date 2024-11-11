package com.pwhs.quickmem.core.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.pwhs.quickmem.MainActivity
import com.pwhs.quickmem.R
import com.pwhs.quickmem.core.datastore.AppManager
import com.pwhs.quickmem.core.datastore.TokenManager
import com.pwhs.quickmem.data.dto.notification.TokenRequestDto
import com.pwhs.quickmem.data.remote.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class AppFirebaseMessagingService : FirebaseMessagingService() {
    private val tokenManager = TokenManager(this)

    private val appManager = AppManager(this)

    @Inject
    lateinit var apiService: ApiService
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Timber.d("Refreshed token: $token")
        sendTokenToServer(token)
    }

    private fun sendTokenToServer(token: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val isLogged = appManager.isLoggedIn.firstOrNull() ?: false
            if (isLogged) {
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


    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Timber.d("From: ${remoteMessage.from}")
        CoroutineScope(Dispatchers.IO).launch {
            val isPushNotificationsEnabled = appManager.pushNotifications.firstOrNull() ?: false
            if (isPushNotificationsEnabled) {
                remoteMessage.notification?.let {
                    showNotification(it.title, it.body)
                }
            }
        }
    }

    private fun showNotification(title: String?, body: String?) {
        val channelId = "QuickMem Channel"
        val notificationId = 0

        val intent = Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_bear)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        if (SDK_INT >= VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "QuickMem Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Send notification from QuickMem"
            }
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }

        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.notify(notificationId, builder.build())
    }
}