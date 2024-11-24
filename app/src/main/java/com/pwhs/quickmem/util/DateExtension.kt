package com.pwhs.quickmem.util

import java.util.Date

fun Date.toFormattedString(): String {
    val now = Date()
    val diff = this.time - now.time
    val seconds = diff / 1000
    val minutes = seconds / 60
    val hours = minutes / 60
    val days = hours / 24

    return when {
        days > 0 -> "$days days left"
        hours > 0 -> "$hours hours left"
        minutes > 0 -> "$minutes minutes left"
        seconds > 0 -> "$seconds seconds left"
        else -> "Expired"
    }
}