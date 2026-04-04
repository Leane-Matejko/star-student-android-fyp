package com.example.starstudent.planner.view.screens

import android.util.Log
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.starstudent.core.data.DatabaseSingleton
import com.example.starstudent.core.domain.BannerFunctions
import com.example.starstudent.core.domain.CurrentApplication
import com.example.starstudent.core.domain.navigation.NavigationFunctions
import com.example.starstudent.core.domain.navigation.NavigationOptions
import com.example.starstudent.planner.data.entities.Tasks
import java.util.Calendar

class PlannerViewModel : ViewModel(){

    val tasksDAO =
        DatabaseSingleton
            .getDatabase(
                CurrentApplication
                    .instance)
            .tasksDao()

    val bannerFunctions = BannerFunctions()

    val navigationFunctions = NavigationFunctions()

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

    fun updateTime(){
        curDate = bannerFunctions.updateTime()
    }

    var showNavMenu by mutableStateOf(
        false
    )
        private set

    var taskList by mutableStateOf(
        listOf<Tasks>()
    )
        private set

    var calendarMonth by mutableStateOf(
        Calendar.getInstance().get(Calendar.MONTH)
    )
        private set

    fun showNavMenu()
    {showNavMenu = true}

    fun dismissNavMenu()
    {showNavMenu = false }

    suspend fun getTaskList(){
        taskList = tasksDAO
            .getAllCurrentTasksWeek(
                username,
                0L
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