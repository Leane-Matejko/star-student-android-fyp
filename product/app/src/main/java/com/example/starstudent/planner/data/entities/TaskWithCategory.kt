package com.example.starstudent.planner.data.entities

import androidx.room.PrimaryKey

data class TaskWithCategory(
    @PrimaryKey(
        autoGenerate = true
    )
    val id: Int = 0,
    val cateId : Int = 0,
    val taskLabel : String,
    val taskCategoryLabel: String,
    val taskCategoryLabelColour: String,
    val isCritical : Boolean,
    val dueDate : Long,
    val isComplete : Boolean,
    val completeDate : Long,
    val isActive : Boolean
)
