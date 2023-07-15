package com.assignment.openinappdashboard.model

import com.assignment.openinappdashboard.parsersandconverters.TimeAndDateProvider

open class LinksClicksDataWithDate {
    var dateWiseClickCountTotal: MutableMap<CustomDate, Int> = mutableMapOf()

    constructor(overallUrlChart: Map<String, Int>?) {
        convertingMapOfCustomDate(overallUrlChart)
    }

    private fun convertingMapOfCustomDate(overallUrlChart: Map<String, Int>?) {
        if (overallUrlChart != null) {
            for (item in overallUrlChart) {
                val date: CustomDate = TimeAndDateProvider.convertStringToDateObject(item.key)
                val clickCount = item.value
                dateWiseClickCountTotal[date] = clickCount
            }
        }
    }

    fun getClickCountDayWiseOfMonthOfYear(year: String, month: String): Map<String, Int> {
        val clickCountDayWiseOfMonthOfYear: MutableMap<String, Int> = mutableMapOf()
        for (date in dateWiseClickCountTotal) {
            if (date.key.year == year && date.key.month == month) {
                clickCountDayWiseOfMonthOfYear[date.key.date] = date.value
            }
        }
        return clickCountDayWiseOfMonthOfYear
    }

//    fun getClickCountMonthWiseOfYear(year: String): Map<String, Int> {
//        val clickCountMonthWiseOfYear: MutableMap<String, Int> = mutableMapOf()
//        for (date in dateWiseClickCountTotal) {
//            if (date.key.year == year) {
//                clickCountMonthWiseOfYear[date.key.month] =
//                    date.value + clickCountMonthWiseOfYear[date.key.month]!!
//            }
//        }
//        return clickCountMonthWiseOfYear
//    }

//    fun chartDateRangeForMothOfYear(year: String, month: String): String {
//        return ""
//    }
//
//    fun getClicksCountForCustomDateRange(date1: CustomDate, date2: CustomDate) {
//
//    }

}