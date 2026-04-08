package com.example.starstudent.core.domain

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.TemporalAdjusters

class FormatHomepage {

    fun getRecentMonday() : Long{
        return LocalDate
            .now()
            .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
            .atStartOfDay(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    }

    fun getEndOfWeek() : Long{
        return LocalDate
            .now()
            .with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
            .atTime(23,59,59)
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    }

    fun getNowLong() : Long{
        return System
            .currentTimeMillis()
    }

    fun checkIsMonday() : Boolean{
        return (LocalDate.now().dayOfWeek == DayOfWeek.MONDAY)
    }
}