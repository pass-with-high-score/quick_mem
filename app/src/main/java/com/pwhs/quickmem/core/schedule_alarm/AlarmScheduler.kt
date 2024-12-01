package com.pwhs.quickmem.core.schedule_alarm

import com.pwhs.quickmem.core.data.alarm.StudyAlarm

interface AlarmScheduler {
    fun schedule(item: StudyAlarm)
    fun cancel(item: StudyAlarm)
    fun cancelAll()
}