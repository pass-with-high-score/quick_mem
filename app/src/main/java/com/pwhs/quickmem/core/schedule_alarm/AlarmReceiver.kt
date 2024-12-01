package com.pwhs.quickmem.core.schedule_alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.pwhs.quickmem.MainActivity
import com.pwhs.quickmem.R
import com.pwhs.quickmem.core.data.alarm.StudyAlarm
import timber.log.Timber
import java.time.LocalDateTime

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        context ?: return
        // Ensure that the intent action is what we expect
        val action = intent?.action
        if (action != Intent.ACTION_BOOT_COMPLETED && action != "com.pwhs.quickmem.core.schedule_alarm.ALARM") {
            Timber.d("Received an unknown or unauthorized broadcast action: $action")
            return
        } else {
            Timber.d("Received an authorized broadcast action: $action")
        }

        Timber.d("Alarm received")
        val message = intent.getStringExtra("EXTRA_MESSAGE") ?: return
        Timber.d("Message: $message")

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "daily_notification_channel"

        val channel = NotificationChannel(
            channelId,
            "Daily Notifications",
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(channel)

        val openAppIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            openAppIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle("Daily Reminder")
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_bear)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(1, notification)

        val alarmScheduler = AndroidAlarmScheduler(context)
        val scheduleTime = LocalDateTime.now().plusDays(1)
        val studyAlarm = StudyAlarm(
            message = "Daily reminder",
            time = scheduleTime
        )
        alarmScheduler.schedule(studyAlarm)
    }
}
