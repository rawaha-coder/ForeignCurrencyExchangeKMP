package com.rawahacoder.foreigncurrencyexchangekmp.utility

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun showCurrentDateTime(): String {
    val timestampCurrent = Clock.System.now()
    val date = timestampCurrent.toLocalDateTime(TimeZone.currentSystemDefault())
    val dayOfMonth = date.dayOfMonth
    val month = date.month.toString().lowercase()
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
    val year = date.year

    val suffixVal = when {
        dayOfMonth in 11..13 -> "th"
        dayOfMonth % 10 == 1 -> "st"
        dayOfMonth % 10 == 2 -> "nd"
        dayOfMonth % 10 == 3 -> "rd"
        else -> "th"
    }

    return "$dayOfMonth$suffixVal $month $year"

}