package com.example.starstudent.history.view.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.starstudent.core.domain.BannerFunctions
import com.example.starstudent.core.domain.CurrentApplication
import com.example.starstudent.core.domain.navigation.NavigationFunctions
import com.example.starstudent.core.domain.navigation.NavigationOptions

class HistoryViewModel : ViewModel() {

    val bannerFunctions = BannerFunctions()
    val navigationFunctions = NavigationFunctions()

    var showNavMenu by mutableStateOf(
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

    var errorWindow by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf("")
        private set



    fun updateTime(){
        curDate = bannerFunctions.updateTime()
    }

    fun showNavMenu()
    {showNavMenu = true}

    fun dismissNavMenu()
    {showNavMenu = false }

    fun profileNav(navController: NavController){
        Log.d("TEST", "Navigating to the profile...")
        navigationFunctions.goToProfile(navController)
    }

    fun historyStudySessionNav(navController: NavController){
        Log.d("TEST", "Navigating to the study session history...")
        navigationFunctions.goToHistoryStudySessions(navController)
    }

    fun getNavigationMenu(navController: NavController): List<NavigationOptions>{

        val navigationFunctions = NavigationFunctions()

        return listOf(
            NavigationOptions("Homepage")
            { navigationFunctions.goToHomepage(navController) },
            NavigationOptions("Study Centre")
            { navigationFunctions.goToStudyCentre(navController) },
            NavigationOptions("Planner")
            {navigationFunctions.goToPlanner(navController)},
            NavigationOptions("Task List")
            { navigationFunctions.goToTaskList(navController) },
            NavigationOptions("Profile Settings")
            { navigationFunctions.goToProfile(navController) }
        )
    }

    fun resetErrorWindow(){
        errorWindow = false
    }
}