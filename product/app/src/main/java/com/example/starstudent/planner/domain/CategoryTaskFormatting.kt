package com.example.starstudent.planner.domain

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.Color
import com.example.starstudent.ui.theme.ExtendedLabelColours
import java.time.Instant
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

class CategoryTaskFormatting {

    fun getCategoryButtonText(
        editCategory : Boolean
    ) : String{
        if (editCategory){
            return "Save Changes"
        }
        return "Save"
    }

    fun getTaskButtonText(
        editTask : Boolean
    ) : String{
        if (editTask){
            return "Save Changes"
        }
        return "Save"
    }

    fun getEditCategoryDialogLabel(
        editCategory : Boolean
    ) : String {
        if(editCategory){
            return "Edit Category"
        }
        return "Add New Category"
    }

    fun getEditTaskDialogLabel(
        editTask : Boolean
    ) : String {
        if(editTask){
            return "Edit Task"
        }
        return "Add New Task"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatDateTime(dateTime : Long) : String{
        return Instant.ofEpochMilli(dateTime)
            .atZone(ZoneId.systemDefault())
            .format(
                DateTimeFormatter
                    .ofPattern(
                        "EEE d MMM yyyy, HH:mm"
                    )
            )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatDateTime(dateTime : Long, pattern : String) : String{
        return Instant.ofEpochMilli(dateTime)
            .atZone(ZoneId.systemDefault())
            .format(
                DateTimeFormatter
                    .ofPattern(
                        pattern
                    )
            )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDateTime(
        date: Long,
        hour: Int,
        minute: Int
    ) : Long{
        val zone = ZoneId.systemDefault()

        val checkedDate = if (date >= 0L) {date} else {System.currentTimeMillis()}

        val localDate = Instant.ofEpochMilli(checkedDate)
            .atZone(zone)
            .toLocalDate()

        val dateTime = LocalDateTime.of(
            localDate,
            LocalTime.of(hour, minute)
        )

        return dateTime
            .atZone(zone)
            .toInstant()
            .toEpochMilli()
    }

    fun getCategoryListHeight(tasksNum : Int) : Int {
        if(tasksNum == 0){
            return 120
        }
        return (100 + (tasksNum * 70))
    }

    fun getEditCategoryDialogHeight(
        editCategory : Boolean
    ) : Int {
        if(editCategory){
            return 620
        }
        return 520
    }

    fun getEditTaskDialogHeight(
        editTask : Boolean
    ) : Int {
        if(editTask){
            return 700
        }
        return 600
    }

    fun getSelectedTaskListHeight(
        taskListNum : Int
    ) : Int {
        return if (taskListNum == 0){
            220
        } else{
            (160 + (taskListNum * 160))
        }
    }

    fun getTaskTitle(key : String) : String{
        return when(key) {
            "overdue" -> "Overdue"
            "today" -> "Today"
            "week" -> "This Week"
            "month" -> "This Month"
            else -> "Tasks: $key"
        }
    }

    fun dateIntToLong(
        date : Date,
        hour : Int,
        minute: Int
    ) : Long{
        return date
            .toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
            .atTime(hour, minute)
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    }

    fun getDayStart(
        day : Date
    ) : Long {
        return dateIntToLong(day, 0,0)
    }

    fun getDayEnd(
        day : Date
    ) : Long {
        return dateIntToLong(day, 23,59)
    }

    fun getCategoryColour(colour : String, colourList : ExtendedLabelColours) : Color {
        return when (colour) {
            "red" -> colourList.red
            "orange" -> colourList.orange
            "yellow" -> colourList.yellow
            "green" -> colourList.green
            "blue" -> colourList.blue
            "navy" -> colourList.navy
            "purple" -> colourList.purple
            "pink" -> colourList.pink
            else -> colourList.red
        }
    }
}