package com.example.starstudent.planner.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "tasks",
    foreignKeys = [
        ForeignKey(
            entity = TaskCategories::class,
            parentColumns = ["id"],
            childColumns = ["cateId"]
        )
    ],
    indices = [Index("cateId")]
    )
data class Tasks(
    @PrimaryKey(
        autoGenerate = true
    )
    val id: Int = 0,
    val cateId : Int = 0,
    val taskLabel : String,
    val isCritical : Boolean,
    val dueDate : Long,
    val isComplete : Boolean,
    val completeDate : Long,
    val isActive : Boolean
)
