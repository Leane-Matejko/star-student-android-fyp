package com.example.starstudent.studySpaces.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.starstudent.studySpaces.data.entities.StudySessions
import com.example.starstudent.userAccounts.data.entities.AppUserData
import java.sql.Time

@Dao
interface StudySessionsDAO {

    @Insert
    suspend fun startSession(session: StudySessions)

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
}