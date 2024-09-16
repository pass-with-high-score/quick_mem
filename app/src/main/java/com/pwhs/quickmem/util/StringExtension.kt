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

fun String.getUsernameFromEmail(): String {
    return if (this.length < 8) {
        "user${(1000..9999).random()}"
    } else {
        this.substringBefore("@")
    }
}

fun String.getNameFromEmail(): String {
    return this.substringBefore("@").replace(".", " ")
}

fun String.emailIsValid(): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.strongPassword(): Boolean {
    val passwordPattern =
        "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$".toRegex()
    return passwordPattern.matches(this)
}