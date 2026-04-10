package com.example.starstudent.studySpaces.data

import com.example.starstudent.core.data.DatabaseSingleton
import com.example.starstudent.core.domain.CurrentApplication
import com.example.starstudent.studySpaces.data.entities.PausedSessions
import com.example.starstudent.studySpaces.data.entities.StudySessions

class AccessPausedSessions {

    private val pausedSessionsDAO = DatabaseSingleton
            .getDatabase(
            CurrentApplication
                .instance)
        .pausedSessionsDao()

    //Insert a new paused session
    suspend fun startPauseSession(
        currentSession: List<StudySessions>,
        currentDateTime: Long){
        pausedSessionsDAO.pauseSession(
            PausedSessions(
                sessionId = currentSession[0].id,
                startTime = currentDateTime,
                endTime = 0L,
            )
        )
    }

    //Update the end time of a paused session
    suspend fun endPauseSession(
        currentPausedSession: List<PausedSessions>,
        currentDateTime: Long
    ){
        pausedSessionsDAO.unpauseSession(
            currentPausedSession[0].id,
            currentPausedSession[0].sessionId,
            currentPausedSession[0].startTime,
            endTime = currentDateTime
        )
    }

    //Return list of the current paused session
    suspend fun getCurrentPausedSession(
        currentSession: List<StudySessions>
    ): List<PausedSessions>{
        return pausedSessionsDAO.getCurrentPausedSession(
            currentSession[0].id
        )
    }

    //Return list of paused sessions
    suspend fun getPausedSessions(
        sessionId: Int
    ) : List<PausedSessions>{
        return pausedSessionsDAO.getRecentSessions(sessionId)
    }

    //Delete all paused sessions for a study session
    suspend fun deletePausedSessions(
        sessionId: Int
    ){
        pausedSessionsDAO.deleteSession(sessionId)
    }
}