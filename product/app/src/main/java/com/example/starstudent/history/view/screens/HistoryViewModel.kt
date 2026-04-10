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


    //Updates the time of the top banner
    fun updateTime(){
        curDate = bannerFunctions.updateTime()
    }

    //Shows the navigation menu
    fun showNavMenu()
    {showNavMenu = true}

    //Hides the navigation menu
    fun dismissNavMenu()
    {showNavMenu = false }

    //Navigates to the user's profile
    fun profileNav(navController: NavController){
        Log.d("TEST", "Navigating to the profile...")
        navigationFunctions.goToProfile(navController)
    }

    //Navigates to the session history page
    fun historyStudySessionNav(navController: NavController){
        Log.d("TEST", "Navigating to the study session history...")
        navigationFunctions.goToHistoryStudySessions(navController)
    }

    //Get the navigation options for the history page (all windows wo/ history)
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

    //Closes error window
    fun resetErrorWindow(){
        errorWindow = false
    }
}