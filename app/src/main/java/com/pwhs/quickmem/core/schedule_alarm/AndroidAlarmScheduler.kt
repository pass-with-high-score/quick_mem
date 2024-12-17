package com.pwhs.quickmem.core.schedule_alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.pwhs.quickmem.core.data.alarm.StudyAlarm
import timber.log.Timber
import java.time.ZoneId
import javax.inject.Inject

class AndroidAlarmScheduler @Inject constructor(
    private val context: Context
) : AlarmScheduler {

    private val alarmManager = context.getSystemService(AlarmManager::class.java)
    private val scheduledAlarms = mutableListOf<PendingIntent>()

    override fun schedule(item: StudyAlarm) {
        cancel(item)
        Timber.d("Scheduling alarm for item: $item")
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("EXTRA_MESSAGE", item.message)
            action = "com.pwhs.quickmem.core.schedule_alarm.ALARM"
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            item.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        scheduledAlarms.add(pendingIntent)

        val timeInMillis = item.time.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

    override fun cancel(item: StudyAlarm) {
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            item.hashCode(),
            Intent(context, AlarmReceiver::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.cancel(pendingIntent)

        scheduledAlarms.remove(pendingIntent)
    }

    override fun cancelAll() {

        scheduledAlarms.forEach {
            alarmManager.cancel(it)
        }
        scheduledAlarms.clear()
    }
}