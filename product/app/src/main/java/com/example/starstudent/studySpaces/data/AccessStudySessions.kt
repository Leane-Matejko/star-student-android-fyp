package com.example.starstudent.studySpaces.data

import com.example.starstudent.core.data.DatabaseSingleton
import com.example.starstudent.core.domain.CurrentApplication
import com.example.starstudent.studySpaces.data.entities.StudySessionDuration
import com.example.starstudent.studySpaces.data.entities.StudySessions

class AccessStudySessions {

    val studySessionsDAO = DatabaseSingleton
        .getDatabase(
            CurrentApplication
                .instance)
        .studySessionsDao()

    suspend fun getCurrentStudySession(
        user : String
    ): List<StudySessions>{
        return  studySessionsDAO.getCurrentSession(
            user
        )
    }

    suspend fun startSession(
        user: String,
        startTime: Long
    ){
        studySessionsDAO.startSession(
            StudySessions(
                user = user,
                startTime = startTime,
                endTime = 0L
            )
        )
    }

    suspend fun endSession(
        currentSession: StudySessions,
        endTime: Long
    ){
        studySessionsDAO.endSession(
            currentSession.id,
            currentSession.user,
            currentSession.startTime,
            endTime = endTime
        )
    }

    suspend fun getRecentStudySessions(
        user: String
    ): List<StudySessions>{
        return studySessionsDAO.getRecentSessions(
            user
        )
    }

    suspend fun getRecentStudySessionsAll(
        user: String
    ): List<StudySessions>{
        return studySessionsDAO.getRecentSessionsAll(
            user
        )
    }

    suspend fun getRecentSessionsWithDuration(
        user: String
    ): List<StudySessionDuration>{
        return studySessionsDAO.getRecentSessionsWithDuration(
            user
        )
    }

    suspend fun deleteSession(
        id : Int
    ){
        studySessionsDAO.deleteSession(id)
    }
}