package com.example.starstudent.planner.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

//Stores user's task categories
@Entity(tableName = "task_categories")
data class TaskCategories(
    @PrimaryKey(
        autoGenerate = true
    )
    val id: Int = 0,
    val user : String,
    val cateLabel: String,
    val labelColour: String,
    val isActive : Boolean
)
