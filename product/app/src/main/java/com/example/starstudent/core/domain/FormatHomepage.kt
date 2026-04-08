package com.example.starstudent.core.domain

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.starstudent.studySpaces.data.entities.StudySessionDuration
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

    fun getPreviousMonday() : Long{
        return LocalDate
            .now()
            .minusWeeks(1)
            .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
            .atStartOfDay(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    }

    fun getPreviousEndOfWeek() : Long{
        return LocalDate
            .now()
            .minusWeeks(1)
            .with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
            .atTime(23,59,59)
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    }

    fun getIcon(
        previousDuration : Int,
        currentDuration : Int
    ) : ImageVector{

        val comparison = previousDuration <= currentDuration

        return when (comparison){
            true -> Icons.Filled.KeyboardArrowUp
            false -> Icons.Filled.KeyboardArrowDown
        }
    }

    fun compareStudySessions(
        previousWeekSessionsList : List<StudySessionDuration>,
        currentWeekSessionsList : List<StudySessionDuration>
    ) : ImageVector{

        var previousDuration = 0
        var currentDuration = 0

        previousWeekSessionsList.forEach { session ->
            previousDuration += session.duration.toInt()
        }

        currentWeekSessionsList.forEach { session ->
            currentDuration += session.duration.toInt()
        }

        return getIcon(previousDuration, currentDuration)
    }

    fun getNowLong() : Long{
        return System
            .currentTimeMillis()
    }

    fun checkIsMonday() : Boolean{
        return (LocalDate.now().dayOfWeek == DayOfWeek.MONDAY)
    }
}