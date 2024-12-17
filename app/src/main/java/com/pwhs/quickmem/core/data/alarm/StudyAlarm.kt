package com.pwhs.quickmem.core.data.alarm

import androidx.annotation.StringRes
import java.time.LocalDateTime

data class StudyAlarm(
    val time: LocalDateTime,
    @StringRes val message: Int
)
