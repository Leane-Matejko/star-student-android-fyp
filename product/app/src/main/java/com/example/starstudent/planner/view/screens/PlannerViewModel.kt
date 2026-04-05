package com.example.starstudent.planner.view.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.starstudent.core.domain.BannerFunctions
import com.example.starstudent.core.domain.CurrentApplication
import com.example.starstudent.core.domain.navigation.NavigationFunctions
import com.example.starstudent.core.domain.navigation.NavigationOptions
import com.example.starstudent.planner.data.AccessTasks
import com.example.starstudent.planner.data.entities.Tasks
import com.example.starstudent.planner.data.entities.TaskWithCategory
import com.example.starstudent.planner.domain.CalendarFunctions
import com.example.starstudent.planner.domain.CategoryTaskFormatting
import com.example.starstudent.ui.theme.ExtendedLabelColours
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.ZoneId
import java.util.Calendar
import java.util.Date

class PlannerViewModel : ViewModel(){
    val calendarFunctions = CalendarFunctions()

    val categoryTaskFormatting = CategoryTaskFormatting()
    val accessTasks = AccessTasks()
    val bannerFunctions = BannerFunctions()
    val navigationFunctions = NavigationFunctions()
    val monthList = calendarFunctions.getMonthList()
    val yearList = calendarFunctions.getYearList()

    var showNavMenu by mutableStateOf(
        false
    )
        private set

    var showSeeTasksDialog by mutableStateOf(
        false
    )
        private set

    var showYearList by mutableStateOf(
        false
    )
        private set

    var username by mutableStateOf(
        CurrentApplication
            .instance
            .getUserInfo()
            .username)
        private set

    var curDate by mutableStateOf(
        bannerFunctions.updateTime()
    )
        private set

    var taskList by mutableStateOf(
        listOf<Tasks>()
    )
        private set

    var calendarMonth by mutableIntStateOf(
        Calendar.getInstance().get(Calendar.MONTH)
    )
        private set

    var calendarYear by mutableIntStateOf(
        Calendar.getInstance().get(Calendar.YEAR)
    )
        private set

    var selectedDay by mutableStateOf(
        System.currentTimeMillis()
    )
        private set

    var selectedDate by mutableStateOf(
        Date()
    )
        private set

    var selectedDayTaskList by mutableStateOf(
        listOf<TaskWithCategory>()
    )
        private set

    var taskListType by mutableStateOf(
        ""
    )
        private set

    var showSelectMonthDialog by mutableStateOf(
        false
    )
        private set

    fun updateTime(){
        curDate = bannerFunctions.updateTime()
    }

    fun toggleYearList(){
        showYearList = !showYearList
    }

    fun showNavMenu()
    {showNavMenu = true}

    fun dismissNavMenu()
    {showNavMenu = false }

    fun showSelectMonthDialog()
    {showSelectMonthDialog = true}

    fun hideSelectMonthDialog()
    {showSelectMonthDialog = false }

    fun showSeeTasksDialog(
        newDate : Long
    )
    {
        selectedDay = newDate
        showSeeTasksDialog = true
    }

    fun hideSeeTasksDialog()
    {showSeeTasksDialog = false }

    fun setMonth(newMonth: Int){
        calendarMonth = newMonth
    }

    fun setYear(newYear: Int){
        calendarYear = newYear
    }

    fun setNewTaskDate(
        newDate : Date
    ){
        selectedDate = newDate
    }

    fun updateTaskListType(taskType : String){
        taskListType = taskType
    }

    fun LocalDate.getLocalDateStart() : Long{
        return this
            .atStartOfDay(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    }

    fun LocalDate.getLocalDateEnd() : Long{
        return this
            .atTime(23, 59, 59)
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    }

    fun getWeekStart() : Long{
        return LocalDate
            .now()
            .with(DayOfWeek.MONDAY)
            .getLocalDateStart()
    }

    fun getWeekEnd() : Long{
        return LocalDate
            .now()
            .with(DayOfWeek.SUNDAY)
            .getLocalDateEnd()
    }

    fun getMonthStart() : Long{
        return LocalDate
            .now()
            .withDayOfMonth(1)
            .getLocalDateStart()
    }

    fun getMonthEnd() : Long{
        val currentDay = LocalDate.now()

        return currentDay
            .withDayOfMonth(currentDay.lengthOfMonth())
            .getLocalDateEnd()
    }

    fun getTaskListTitle() : String{
        return when (taskListType){
            "overdue" -> categoryTaskFormatting.getTaskTitle("overdue")
            "today" -> categoryTaskFormatting.getTaskTitle("today")
            "week" -> categoryTaskFormatting.getTaskTitle("week")
            "month" -> categoryTaskFormatting.getTaskTitle("month")
            else -> categoryTaskFormatting.getTaskTitle(getSelectedDateFormatted())
        }
    }

    fun getSelectedDateFormatted() : String{
        return categoryTaskFormatting.formatDateTime(
            selectedDay,
            "EEE d MMM yyyy")
    }

    fun getSelectedTaskListHeight() : Int{
        return categoryTaskFormatting.getSelectedTaskListHeight(
            selectedDayTaskList.size
        )
    }

    fun formatDateTime(date : Long) : String{
        return categoryTaskFormatting.formatDateTime(date)
    }

    fun formatDateTime(date : Long, pattern: String) : String{
        return categoryTaskFormatting.formatDateTime(date, pattern)
    }

    fun getCategoryColour(color: String, colorList: ExtendedLabelColours): Color{
        return categoryTaskFormatting.getCategoryColour(color, colorList)
    }

    suspend fun getTaskList(){
        taskList = accessTasks.getTaskList(
            username,
            0L
        )
    }

    fun getPreviousMonth(){
        if (calendarMonth == 0){
            calendarMonth = 11
            calendarYear -= 1
        }else{
            calendarMonth -= 1
        }
    }

    fun getNextMonth(){
        if (calendarMonth == 11){
            calendarMonth = 0
            calendarYear += 1
        }else{
            calendarMonth += 1
        }
    }

    fun getDayStart(
        day : Date
    ) : Long{
        return categoryTaskFormatting.getDayStart(day)
    }

    fun getDayStart(
        day : Long
    ) : Long{
        return categoryTaskFormatting.getDateTime(day, 0, 0)
    }

    fun getDayEnd(
        day : Date
    ) : Long{
        return categoryTaskFormatting.getDayEnd(day)
    }

    fun getDayEnd(
        day : Long
    ) : Long{
        return categoryTaskFormatting.getDateTime(day, 23, 59)
    }

    suspend fun getSelectDayTaskList(
        intervalStart: Long,
        intervalEnd: Long
    ){
        selectedDayTaskList = accessTasks.getTasksWithCategories(
            username,
            intervalStart,
            intervalEnd
        )
    }

    fun generateMonthDates(year: Int, month: Int): List<Date> {
        return calendarFunctions.generateMonthDates(
            year,
            month
        )
    }

    fun profileNav(navController: NavController){
        Log.d("TEST", "Navigating to the profile...")
        navigationFunctions.goToProfile(navController)
    }

    fun getNavigationMenu(navController: NavController): List<NavigationOptions>{

        val navigationFunctions = NavigationFunctions()

        return listOf(
            NavigationOptions("Homepage")
            { navigationFunctions.goToHomepage(navController) },
            NavigationOptions("Study Centre")
            { navigationFunctions.goToStudyCentre(navController) },
            NavigationOptions("Task List")
            { navigationFunctions.goToTaskList(navController) },
            NavigationOptions("Profile Settings")
            { navigationFunctions.goToProfile(navController) }
        )
    }

    fun navTaskList(navController: NavController){
        Log.d("TEST", "Navigating to the Task List...")
        navigationFunctions.goToTaskList(navController)
    }

}