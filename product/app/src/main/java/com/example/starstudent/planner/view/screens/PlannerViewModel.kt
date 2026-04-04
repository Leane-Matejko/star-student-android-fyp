package com.example.starstudent.planner.view.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.starstudent.core.domain.BannerFunctions
import com.example.starstudent.core.domain.CurrentApplication
import com.example.starstudent.core.domain.navigation.NavigationFunctions
import com.example.starstudent.core.domain.navigation.NavigationOptions
import com.example.starstudent.planner.data.AccessTasks
import com.example.starstudent.planner.data.entities.Tasks
import com.example.starstudent.planner.domain.CalendarFunctions
import java.util.Calendar
import java.util.Date

class PlannerViewModel : ViewModel(){
    val calendarFunctions = CalendarFunctions()
    val accessTasks = AccessTasks()
    val bannerFunctions = BannerFunctions()
    val navigationFunctions = NavigationFunctions()
    val monthList = calendarFunctions.getMonthList()
    val yearList = calendarFunctions.getYearList()

    var showNavMenu by mutableStateOf(
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

    fun setMonth(newMonth: Int){
        calendarMonth = newMonth
    }

    fun setYear(newYear: Int){
        calendarYear = newYear
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