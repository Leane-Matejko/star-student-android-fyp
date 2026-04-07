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

    suspend fun getCurrentPausedSession(
        currentSession: List<StudySessions>
    ): List<PausedSessions>{
        return pausedSessionsDAO.getCurrentPausedSession(
            currentSession[0].id
        )
    }

    suspend fun getPausedSessions(
        sessionId: Int
    ) : List<PausedSessions>{
        return pausedSessionsDAO.getRecentSessions(sessionId)
    }

    suspend fun deletePausedSessions(
        sessionId: Int
    ){
        pausedSessionsDAO.deleteSession(sessionId)
    }
}