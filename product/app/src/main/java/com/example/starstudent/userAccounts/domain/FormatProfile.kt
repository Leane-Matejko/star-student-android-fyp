package com.example.starstudent.userAccounts.domain

import android.icu.text.SimpleDateFormat
import android.security.identity.AuthenticationKeyMetadata
import com.example.starstudent.core.domain.Themes
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

class FormatProfile {

    val themeOptions = listOf(
            "system",
            "light",
            "dark",
            "strawberry"
            )

    fun profileLocationAccessFormatted(
        locationAccess : Boolean
    ) : String{
        if(locationAccess){
            return "Enabled"
        }
        return "Denied"
    }

    fun convertLongToDate(longDate : Long, pattern : String) : String{
        return SimpleDateFormat(pattern, Locale.getDefault()).format(Date(longDate))
    }

    fun formatBannerDateTime() : String{
        return LocalDateTime
            .now()
            .format(
                DateTimeFormatter
                    .ofPattern(
                        "EEE d MMMM, HH:mm",
                        Locale.getDefault()
                    )
            )
    }

    fun withinMonthRange(day : String) : Boolean{

        return day.toIntOrNull()!! in 1..12
    }

    fun leapYearCheck(day : String, newYear: String) : Boolean {
        if (newYear.toIntOrNull()?.div(4) == 0){
            return (day.toIntOrNull()!! <= 29)
        }
        return (day.toIntOrNull()!! <= 28)
    }

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

    fun getThemeFromString (themeKey : String) : Themes{
        return when(themeKey){
            "system" -> Themes.SYSTEM
            "light" -> Themes.LIGHT
            "dark" -> Themes.DARK
            "strawberry" -> Themes.STRAWBERRY
            else -> Themes.SYSTEM
        }
    }
}