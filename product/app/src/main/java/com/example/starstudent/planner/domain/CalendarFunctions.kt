package com.example.starstudent.planner.domain

import java.util.Calendar
import java.util.Date
import kotlin.ranges.rangeTo

class CalendarFunctions {

    val thisYear = Calendar.getInstance().get(Calendar.YEAR)

    //Return list of months
    fun getMonthList() : List<String>{
        return listOf(
            "Jan", "Feb", "Mar",
            "Apr", "May", "June",
            "July", "Aug", "Sep",
            "Oct", "Nov", "Dec"
        )
    }

    //Return range of year options
    fun getYearList() : IntRange{
        return ((thisYear - 1).. (thisYear + 16))
    }

    //Return a list of dates for a chosen month and year
    fun generateMonthDates(year: Int, month: Int): List<Date> {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, 1)

        val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        val dates = mutableListOf<Date>()

        for (day in 1..daysInMonth) {
            calendar.set(year, month, day)

            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)

            dates.add(
                calendar.time
            )
        }
        return dates
    }
}