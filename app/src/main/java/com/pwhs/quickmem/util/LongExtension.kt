package com.pwhs.quickmem.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun Long.toFormattedString(): String {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val date = Date(this)
    return dateFormat.format(date)
}

fun Long.isDateSmallerThan(): Boolean {
    val calendarBirth = Calendar.getInstance().apply { timeInMillis = this@isDateSmallerThan }
    val calendarCompare = Calendar.getInstance()

    // Tính tuổi
    var age = calendarCompare.get(Calendar.YEAR) - calendarBirth.get(Calendar.YEAR)

    // Điều chỉnh tuổi nếu ngày hiện tại trước ngày sinh trong năm so sánh
    if (calendarCompare.get(Calendar.DAY_OF_YEAR) < calendarBirth.get(Calendar.DAY_OF_YEAR)) {
        age--
    }

    // Kiểm tra nếu tuổi nhỏ hơn 18
    return age < 18
}