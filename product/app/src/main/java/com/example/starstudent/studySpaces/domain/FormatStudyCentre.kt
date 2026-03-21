package com.example.starstudent.studySpaces.domain

import android.icu.text.SimpleDateFormat
import com.example.starstudent.studySpaces.data.AccessPausedSessions
import com.example.starstudent.studySpaces.data.RecentSessionsFormat
import com.example.starstudent.studySpaces.data.entities.StudySessions
import java.util.Date
import java.util.Locale

class FormatStudyCentre {

    val accessPausedSessions = AccessPausedSessions()

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

    suspend fun getHours(sessionId: Int, endTime: Long, startTime: Long) : Long{
        val session = (endTime - startTime) - totalPausedTime(sessionId)
        if(session >= 1){
            return (session / (1000*60*60))
        }
        return 0
    }

    suspend fun getMinutes(sessionId: Int, endTime: Long, startTime: Long) : Long{
        val session = (endTime - startTime) - totalPausedTime(sessionId)
        if(session >= 1){
            return ((session / (1000*60))% 60)
        }
        return 0
    }

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

    fun convertLongToDate(longDate : Long, pattern : String) : String{
        return SimpleDateFormat(pattern, Locale.getDefault()).format(Date(longDate))
    }

    fun getLongToDate(startTime: Long) : String{
        return convertLongToDate(
            startTime,
            "EEEE d MMMM yyyy")
    }
}