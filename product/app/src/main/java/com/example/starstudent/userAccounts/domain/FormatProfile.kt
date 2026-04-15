package com.example.starstudent.userAccounts.domain

import android.icu.text.SimpleDateFormat
import com.example.starstudent.core.domain.Themes
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date
import java.util.Locale

class FormatProfile {

    val themeOptions = listOf(
            "system",
            "light",
            "dark",
            "strawberry",
            "neon"
            )

    //Format location access status
    fun profileLocationAccessFormatted(
        locationAccess : Boolean
    ) : String{
        if(locationAccess){
            return "Enabled"
        }
        return "Denied"
    }

    //Convert long date into string of specific pattern
    fun convertLongToDate(longDate : Long, pattern : String) : String{
        return SimpleDateFormat(pattern, Locale.getDefault()).format(Date(longDate))
    }

    //Return if the month value is within possible month values
    fun withinMonthRange(day : String) : Boolean{

        return day.toIntOrNull()!! in 1..12
    }

    //Return if the day is within a leap year
    fun leapYearCheck(day : String, newYear: String) : Boolean {
        if (newYear.toIntOrNull()?.div(4) == 0){
            return (day.toIntOrNull()!! <= 29)
        }
        return (day.toIntOrNull()!! <= 28)
    }

    //Return if the day value is within range of a month
    fun withinDayRange(day : String, newMonth : String, newYear: String) : Boolean{

        val result =
            when (newMonth.toIntOrNull()) {
                1, 3, 5, 7, 8, 10, 12 -> (day.toIntOrNull()!! <= 31)
                4, 6, 9, 11 -> (day.toIntOrNull()!! <= 30)
                2 -> (leapYearCheck(day, newYear))
                else -> {false}
            }

        return result
    }

    fun isRealYear(year : String) : Boolean{
        return (year.toIntOrNull()!! <= LocalDateTime.now().year )
    }


    //Convert temporary birthday values into a long
    fun convertBirthdayToLong(
        birthday: Long,
        newDD: String,
        newMM: String,
        newYYYY: String

    ) : Long{

        val birthdayDay = convertLongToDate(
            birthday,
            "dd")
        val birthdayMonth = convertLongToDate(
            birthday,
            "MM")
        val birthdayYear = convertLongToDate(
            birthday,
            "yyyy")

        var day = newDD
        var month = newMM
        var year = newYYYY

        day.ifBlank{
            day = birthdayDay
        }
        month.ifBlank{
            month = birthdayMonth
        }
        year.ifBlank{
            year = birthdayYear
        }

        val newDate = LocalDate.of(
            year.toInt(),
            month.toInt(),
            day.toInt())

        return newDate
            .atStartOfDay(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    }

    //Return theme remunerated options
    fun getThemeFromString (themeKey : String) : Themes{
        return when(themeKey){
            "system" -> Themes.SYSTEM
            "light" -> Themes.LIGHT
            "dark" -> Themes.DARK
            "strawberry" -> Themes.STRAWBERRY
            "neon" -> Themes.NEON
            else -> Themes.SYSTEM
        }
    }
}