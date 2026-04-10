package com.example.starstudent.planner.domain

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.Color
import com.example.starstudent.ui.theme.ExtendedLabelColours
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

class CategoryTaskFormatting {

    //Return the string for add/edit category button
    fun getCategoryButtonText(
        editCategory : Boolean
    ) : String{
        if (editCategory){
            return "Save Changes"
        }
        return "Save"
    }

    //Return the string for add/edit task button
    fun getTaskButtonText(
        editTask : Boolean
    ) : String{
        if (editTask){
            return "Save Changes"
        }
        return "Save"
    }

    //Return the string for add/edit category header
    fun getEditCategoryDialogLabel(
        editCategory : Boolean
    ) : String {
        if(editCategory){
            return "Edit Category"
        }
        return "Add New Category"
    }

    //Return the string for the add/edit task header
    fun getEditTaskDialogLabel(
        editTask : Boolean
    ) : String {
        if(editTask){
            return "Edit Task"
        }
        return "Add New Task"
    }

    //Return the string for a formatted date time
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

    //Return the string for a formatted date time long in a specific pattern
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

    //Return the string for a formatted date time in a specific format
    fun formatDateTime(dateTime : LocalDate, pattern : String) : String{
        return dateTime.format(
            DateTimeFormatter.ofPattern(pattern)
        )
    }

    //Return the long for a long date and hour and minutes (Int)
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

    //Return the container height for the category list
    fun getCategoryListHeight(tasksNum : Int) : Int {
        if(tasksNum == 0){
            return 120
        }
        return (100 + (tasksNum * 70))
    }

    //Return the container height for the add/edit category window
    fun getEditCategoryDialogHeight(
        editCategory : Boolean
    ) : Int {
        if(editCategory){
            return 620
        }
        return 520
    }

    //Return the container height for the add/edit task window
    fun getEditTaskDialogHeight(
        editTask : Boolean
    ) : Int {
        if(editTask){
            return 700
        }
        return 600
    }

    //Return the container height for the select task list
    fun getSelectedTaskListHeight(
        taskListNum : Int
    ) : Int {
        return if (taskListNum == 0){
            220
        } else{
            (160 + (taskListNum * 160))
        }
    }

    //Return the window header for the categorised list
    fun getTaskTitle(key : String) : String{
        return when(key) {
            "overdue" -> "Overdue"
            "today" -> "Today"
            "week" -> "This Week"
            "month" -> "This Month"
            else -> "Tasks: $key"
        }
    }

    //Return the long for a date and hour and minutes (Ints)
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

    //Return the string of the duration in hours and minutes
    fun formatDuration(
        duration : Int,
    ) : String{
        return "${duration/(1000*60*60)}h : ${((duration / (1000*60))% 60)}m"
    }

    //Return the long of a date at the start of the day
    fun getDayStart(
        day : Date
    ) : Long {
        return dateIntToLong(day, 0,0)
    }

    //Return the long of a date at the end of the day
    fun getDayEnd(
        day : Date
    ) : Long {
        return dateIntToLong(day, 23,59)
    }

    //Return the extended colour option from a string
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