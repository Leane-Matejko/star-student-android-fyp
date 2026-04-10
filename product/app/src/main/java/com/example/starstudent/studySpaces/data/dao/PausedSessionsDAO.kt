package com.example.starstudent.studySpaces.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.starstudent.studySpaces.data.entities.PausedSessions

//Data access object for paused sessions
@Dao
interface   PausedSessionsDAO {
    //Insert a new paused session
    @Insert
    suspend fun pauseSession(session: PausedSessions)

    //Update the end time for a paused session
    @Query("""
        UPDATE paused_sessions
        SET endTime = :endTime
        WHERE id = :id AND sessionId = :sessionId AND startTime = :startTime
    """)
    suspend fun unpauseSession(
        id: Int,
        sessionId: Int,
        startTime: Long,
        endTime: Long
    )

    //Return list of incomplete paused sessions
    @Query("""
        SELECT * 
        FROM paused_sessions 
        WHERE sessionId = :sessionId AND endTime = 0
        ORDER BY startTime DESC
        LIMIT 1
        """)
    suspend fun getCurrentPausedSession(
        sessionId: Int
    ) :  List<PausedSessions>

    //Return list of completed paused session for a study session
    @Query("""
        SELECT * 
        FROM paused_sessions 
        WHERE sessionId = :sessionId AND endTime != 0
        ORDER BY startTime DESC
        """)
    suspend fun getRecentSessions(
        sessionId: Int
    ) :  List<PausedSessions>

    //Delete a paused session
    @Query("""
        DELETE  
        FROM paused_sessions 
        WHERE sessionId = :sessionId
        """)
    suspend fun deleteSession(
        sessionId: Int
    )
}