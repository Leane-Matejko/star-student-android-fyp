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

    //Return current study sessions
    suspend fun getCurrentStudySession(
        user : String
    ): List<StudySessions>{
        return  studySessionsDAO.getCurrentSession(
            user
        )
    }

    //Insert a new study session
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

    //Update end time of a study session
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

    //Return recent study session
    suspend fun getRecentStudySessions(
        user: String
    ): List<StudySessions>{
        return studySessionsDAO.getRecentSessions(
            user
        )
    }

    //Return list of all study sessions
    suspend fun getRecentStudySessionsAll(
        user: String
    ): List<StudySessions>{
        return studySessionsDAO.getRecentSessionsAll(
            user
        )
    }

    //Return list of most recent active session
    suspend fun getMostRecentActiveSessions(
        user: String
    ) : List<StudySessions>{
        return studySessionsDAO.getMostRecentActiveSessions(
            user
        )
    }

    //Return list of Study Session with calculated duration
    suspend fun getRecentSessionsWithDuration(
        user: String
    ): List<StudySessionDuration>{
        return studySessionsDAO.getRecentSessionsWithDuration(
            user
        )
    }

    //Delete study session
    suspend fun deleteSession(
        id : Int
    ){
        studySessionsDAO.deleteSession(id)
    }
}