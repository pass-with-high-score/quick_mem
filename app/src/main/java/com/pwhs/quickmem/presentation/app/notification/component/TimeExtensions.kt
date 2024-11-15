package com.pwhs.quickmem.presentation.app.notification.component

import java.time.Duration
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

fun String.calculateTimeAgo(onDelete: () -> Unit): String {
    return try {
        val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
        val createdDateTime = OffsetDateTime.parse(this, formatter)
        val now = OffsetDateTime.now()
        val duration = Duration.between(createdDateTime, now)

        if (duration.toDays() > 14) {
            onDelete()
            "Expired"
        } else {
            when {
                duration.toMinutes() < 1 -> "${duration.seconds}s"
                duration.toHours() < 1 -> "${duration.toMinutes()}m"
                duration.toDays() < 1 -> "${duration.toHours()}h"
                else -> "${duration.toDays()}d"
            }
        }
    } catch (e: Exception) {
        "N/A"
    }
}
