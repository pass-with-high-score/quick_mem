package com.pwhs.quickmem.core.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build.VERSION_CODES
import android.os.Build.VERSION.SDK_INT
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.pwhs.quickmem.MainActivity
import com.pwhs.quickmem.R
import timber.log.Timber

class AppFirebaseMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Timber.d("Refreshed token: $token")
        sendTokenToServer(token)
    }

    private fun sendTokenToServer(token: String) {
        // Implement logic to send token to your server
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // Xử lý thông báo nhận được
        Timber.d("From: ${remoteMessage.from}")
        showNotification(remoteMessage.notification?.title, remoteMessage.notification?.body)
    }

    private fun showNotification(title: String?, body: String?) {
        val channelId = "QuickMem Channel"
        val notificationId = 0 // Có thể thay đổi để quản lý nhiều thông báo

        // Tạo Intent cho Notification
        val intent = Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Tạo NotificationCompat.Builder
        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_bear)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        // Tạo NotificationChannel cho Android Oreo trở lên
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

        // Hiển thị thông báo
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.notify(notificationId, builder.build())
    }
}