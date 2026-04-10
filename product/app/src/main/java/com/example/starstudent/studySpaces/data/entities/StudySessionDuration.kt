package com.example.starstudent.studySpaces.data.entities

//Data class for study sessions with the calculated duration
data class StudySessionDuration(
    val id: Int,
    val user: String,
    val startTime: Long,
    val duration : Long
)
