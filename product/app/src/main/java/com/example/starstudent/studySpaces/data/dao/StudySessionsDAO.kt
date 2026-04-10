package com.example.starstudent.studySpaces.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.starstudent.studySpaces.data.entities.StudySessionDuration
import com.example.starstudent.studySpaces.data.entities.StudySessions

//Data access object for study sessions
@Dao
interface StudySessionsDAO {

    //Insert a new study session
    @Insert
    suspend fun startSession(session: StudySessions)

    //Update the end time of an existing study session
    @Query("""
        UPDATE study_sessions
        SET endTime = :endTime
        WHERE id = :id AND user = :user AND startTime = :startTime
    """)
    suspend fun endSession(
        id: Int,
        user: String,
        startTime: Long,
        endTime: Long
    )

    //Return a list of incomplete study sessions from the user
    @Query("""
        SELECT * 
        FROM study_sessions 
        WHERE user = :user AND endTime = 0
        ORDER BY startTime DESC
        LIMIT 1
        """)
    suspend fun getCurrentSession(
        user: String
    ) :  List<StudySessions>

    //Return up to 5 of the most recent sessions that are longer than 5 minutes
    @Query("""
        SELECT * 
        FROM study_sessions 
        WHERE user = :user AND endTime != 0 AND (endTime-startTime) > 300000
        ORDER BY startTime DESC
        LIMIT 5
        """)
    suspend fun getRecentSessions(
        user: String
    ) :  List<StudySessions>

    //Return list of completed study sessions
    @Query("""
        SELECT * 
        FROM study_sessions 
        WHERE user = :user AND endTime != 0
        ORDER BY startTime ASC
        """)
    suspend fun getRecentSessionsAll(
        user: String
    ) :  List<StudySessions>

    //Return list of the most recent incomplete study sessions
    @Query("""
        SELECT * 
        FROM study_sessions 
        WHERE user = :user AND endTime = 0
        ORDER BY startTime DESC
        LIMIT 1
        """)
    suspend fun getMostRecentActiveSessions(
        user: String
    ) :  List<StudySessions>

    //Return list of Study Sessions with the calculated total duration
    @Query("""
        SELECT 
            study.id AS id, 
            study.user AS user, 
            study.startTime AS startTime, 
            (study.endTime - study.startTime - IFNULL(SUM(paused.endTime - paused.startTime), 0)) AS duration
        FROM study_sessions study
        LEFT JOIN paused_sessions paused
        ON study.id == paused.sessionId
        WHERE study.user = :user
        GROUP BY study.id
        ORDER BY study.startTime
    """)
    suspend fun getRecentSessionsWithDuration(
        user : String
    ): List<StudySessionDuration>

    //Delete a study session
    @Query("""
        DELETE  
        FROM study_sessions 
        WHERE id = :id
        """)
    suspend fun deleteSession(
        id: Int
    )

    //Return the long of calculated total duration of a study sessions
    @Query("""
        SELECT 
            (study.endTime - study.startTime - IFNULL(SUM(paused.endTime - paused.startTime), 0)) AS duration
        FROM study_sessions study
        LEFT JOIN paused_sessions paused
        ON study.id == paused.sessionId
        WHERE study.id = :id
        GROUP BY study.id
        ORDER BY study.startTime
    """)
    suspend fun getSessionDuration(
        id : Int
    ): Long

    //Update the start time of a study session
    @Query("""
        UPDATE study_sessions
        SET startTime = :startTime
        WHERE id = :id
    """)
    suspend fun resetSessionStart(
        id: Int,
        startTime: Long
    )


}