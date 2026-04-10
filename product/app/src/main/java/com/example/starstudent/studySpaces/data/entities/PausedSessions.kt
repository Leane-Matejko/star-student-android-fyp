package com.example.starstudent.studySpaces.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

//Stores user's paused sessions
@Entity(tableName = "paused_sessions")
data class PausedSessions(
    @PrimaryKey(
        autoGenerate = true
    )
    val id: Int = 0,
    val sessionId: Int,
    val startTime: Long,
    val endTime: Long
)
