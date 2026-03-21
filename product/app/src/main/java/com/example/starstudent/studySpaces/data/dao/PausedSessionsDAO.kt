package com.example.starstudent.studySpaces.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.starstudent.studySpaces.data.entities.PausedSessions
import com.example.starstudent.studySpaces.data.entities.StudySessions

@Dao
interface PausedSessionsDAO {
    @Insert
    suspend fun pauseSession(session: PausedSessions)

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

    @Query("""
        SELECT * 
        FROM paused_sessions 
        WHERE sessionId = :sessionId AND endTime != 0
        ORDER BY startTime DESC
        """)
    suspend fun getRecentSessions(
        sessionId: Int
    ) :  List<PausedSessions>
}