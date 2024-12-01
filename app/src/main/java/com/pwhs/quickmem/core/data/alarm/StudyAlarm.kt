package com.pwhs.quickmem.core.data.alarm

import java.time.LocalDateTime

data class StudyAlarm(
    val time: LocalDateTime,
    val message: String
)
