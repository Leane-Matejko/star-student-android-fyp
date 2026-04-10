package com.example.starstudent.studySpaces.domain

import android.icu.text.SimpleDateFormat
import com.example.starstudent.studySpaces.data.AccessPausedSessions
import com.example.starstudent.studySpaces.data.RecentSessionsFormat
import com.example.starstudent.studySpaces.data.entities.StudySessions
import java.util.Date
import java.util.Locale

class FormatStudyCentre {

    val accessPausedSessions = AccessPausedSessions()

    //Return list of formated study session start date and duration
    suspend fun formatRecentSessions(
        recentStudySessions: List<StudySessions>
    ): List<RecentSessionsFormat>{
        val formattedStudySessions = mutableListOf<RecentSessionsFormat>()
        recentStudySessions.forEach { sessions ->
            formattedStudySessions += RecentSessionsFormat(
                getLongToDate(
                    sessions.startTime),
                calculateTotalStudyTime(
                    sessions.id,
                    sessions.startTime,
                    sessions.endTime
                )
            )
        }
        return formattedStudySessions
    }

    //Return the string of study time (HH:mm) for a study session
    suspend fun calculateTotalStudyTime(
        sessionId: Int,
        startTime: Long,
        endTime: Long
    ) : String{
        return "%02d".format(
            getHours(
                sessionId,
                endTime,
                startTime)) + " Hours, " +
                "%02d".format(
                    getMinutes(
                        sessionId,
                        endTime,
                        startTime))+ " Minutes "
    }

    //Return the hours spent of a study session
    suspend fun getHours(sessionId: Int, endTime: Long, startTime: Long) : Long{
        val session = (endTime - startTime) - totalPausedTime(sessionId)
        if(session >= 1){
            return (session / (1000*60*60))
        }
        return 0
    }

    //Return the minutes spent of a study session
    suspend fun getMinutes(sessionId: Int, endTime: Long, startTime: Long) : Long{
        val session = (endTime - startTime) - totalPausedTime(sessionId)
        if(session >= 1){
            return ((session / (1000*60))% 60)
        }
        return 0
    }

    //Return the total paused time for a study session
    suspend fun totalPausedTime(sessionID: Int) : Long{
        val pausedSessionList = accessPausedSessions.getPausedSessions(sessionID)
        if (pausedSessionList.isNotEmpty()){
            var totalPausedTime = 0L
            pausedSessionList.forEach { session ->
                totalPausedTime += session.endTime - session.startTime
            }
            return totalPausedTime
        }
        return 0L
    }

    //Return a string of a formatted date in a specific pattern
    fun convertLongToDate(longDate : Long, pattern : String) : String{
        return SimpleDateFormat(pattern, Locale.getDefault()).format(Date(longDate))
    }

    //Return a string of a start time long into the date
    fun getLongToDate(startTime: Long) : String{
        return convertLongToDate(
            startTime,
            "EEEE d MMMM yyyy")
    }
}