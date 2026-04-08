package com.example.starstudent.customisation.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "homepage_settings")
data class HomepageSettings(
    @PrimaryKey(
        autoGenerate = false
    )
    val id : String,
    val avatarWindow : Boolean,
    val studyProgress : Boolean,
    val sleepProgress : Boolean,
    val overdueTasks : Boolean,
    val weeklyTasks : Boolean,
    val allTasks : Boolean,
    val studyCentreNav : Boolean,
    val plannerNav : Boolean,
    val taskListNav : Boolean,
    val historyNav : Boolean,
    val profileNav : Boolean,
)




//@Entity(tableName = "task_categories")
//data class TaskCategories(
//    @PrimaryKey(
//        autoGenerate = true
//    )
//    val id: Int = 0,
//    val user : String,
//    val cateLabel: String,
//    val labelColour: String,
//    val isActive : Boolean
//)
