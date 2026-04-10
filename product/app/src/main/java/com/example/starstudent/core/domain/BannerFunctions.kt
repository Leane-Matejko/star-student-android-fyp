package com.example.starstudent.core.domain

import android.util.Log
import androidx.navigation.NavController
import com.example.starstudent.core.domain.navigation.NavigationFunctions
import com.example.starstudent.core.domain.navigation.NavigationOptions
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class BannerFunctions {
    private val navigationFunctions = NavigationFunctions()

    //Updates the time string on the banner
    fun updateTime() : String{
        return LocalDateTime
            .now()
            .format(
                DateTimeFormatter
                    .ofPattern(
                        "EEE d MMMM, HH:mm",
                        Locale.getDefault()
                    )
            )
    }

    //Navigates to the user profile
    fun profileNav(navController: NavController){
        Log.d("Navigation Functions", "Navigating to the profile...")
        navigationFunctions.goToProfile(navController)
    }

    //Produces options for a navigation menu
    fun getNavigationMenu(navController: NavController): List<NavigationOptions>{
        return listOf(
            NavigationOptions("Homepage")
            {navigationFunctions.goToHomepage(navController)},
            NavigationOptions("Planner")
            {navigationFunctions.goToPlanner(navController)},
            NavigationOptions("Task List")
            {navigationFunctions.goToTaskList(navController)},
            NavigationOptions("History")
            {navigationFunctions.goToHistory(navController)},
            NavigationOptions("Profile Settings")
            {navigationFunctions.goToProfile(navController)}
        )
    }
}