package com.example.starstudent.studySpaces.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

//Stores user's study sessions
@Entity(tableName = "study_sessions")
    data class StudySessions(
        @PrimaryKey(
            autoGenerate = true
        )
        val id: Int = 0,
        val user: String,
        val startTime: Long,
        val endTime: Long
    )
