package com.pwhs.quickmem.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun String.upperCaseFirstLetter(): String {
    return this.replaceFirstChar { it.uppercase() }
}

fun String.toDateFormatted(): String {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val date = dateFormat.parse(this) ?: return this
    return dateFormat.format(date)
}

fun String.isDateSmallerThan(date: String): Boolean {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    // Parse the current string (birth date) and the comparison date
    val birthDate: Date = dateFormat.parse(this) ?: return false
    val comparisonDate: Date = dateFormat.parse(date) ?: return false

    // Calculate the difference in years
    val calendarBirth = Calendar.getInstance().apply { time = birthDate }
    val calendarCompare = Calendar.getInstance().apply { time = comparisonDate }

    // Calculate the age
    var age = calendarCompare.get(Calendar.YEAR) - calendarBirth.get(Calendar.YEAR)

    // Adjust age if the current date is before the birthday in the comparison year
    if (calendarCompare.get(Calendar.DAY_OF_YEAR) < calendarBirth.get(Calendar.DAY_OF_YEAR)) {
        age--
    }

    // Check if the age is smaller than 18
    return age < 18
}