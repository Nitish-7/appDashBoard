package com.assignment.openinappdashboard.parsersandconverters

import com.assignment.openinappdashboard.model.CustomDate
import java.text.SimpleDateFormat
import java.util.*

class TimeAndDateProvider {
    companion object {
        fun convertStringToDateObject(dateString: String): CustomDate {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date = inputFormat.parse(dateString)

            val outputYearFormat = SimpleDateFormat("yyyy", Locale.getDefault())
            val outputMonthFormat = SimpleDateFormat("MMMM", Locale.getDefault())
            val outputDateFormat = SimpleDateFormat("dd", Locale.getDefault())

            val year = outputYearFormat.format(date!!)
            val month = outputMonthFormat.format(date).substring(0, 3)
            val day = outputDateFormat.format(date)

            return CustomDate(year, month, day)
        }

        fun getGreetingTextFromLocalTime(): String {
            val currentTime = Calendar.getInstance().time
            val hourOfDay = SimpleDateFormat("HH", Locale.getDefault()).format(currentTime).toInt()

            val greeting: String = when (hourOfDay) {
                in 0..11 -> "Good Morning"
                in 12..16 -> "Good Afternoon"
                else -> "Good Evening"
            }
            return greeting
        }
    }
}