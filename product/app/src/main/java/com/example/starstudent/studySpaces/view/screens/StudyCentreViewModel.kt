package com.example.starstudent.studySpaces.view.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.starstudent.core.domain.CurrentApplication
import com.example.starstudent.core.domain.navigation.NavigationFunctions
import com.example.starstudent.core.domain.navigation.NavigationOptions
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class StudyCentreViewModel : ViewModel() {

    private val navigationFunctions = NavigationFunctions()

    var username by mutableStateOf(
        CurrentApplication
            .instance
            .getUserInfo()
            .username)
        private set

    var curDate by mutableStateOf(
        LocalDateTime
            .now()
            .format(
                DateTimeFormatter
                    .ofPattern(
                        "EEE d MMMM, HH:mm",
                        Locale.getDefault()
                    )
            )
    )
        private set

    var showNavMenu by mutableStateOf(
        false
    )
        private set

    fun updateTime(){
        curDate = LocalDateTime
            .now()
            .format(
                DateTimeFormatter
                    .ofPattern(
                        "EEE d MMMM, HH:mm",
                        Locale.getDefault()
                    )
            )
    }

    fun showNavMenu()
    {showNavMenu = true}

    fun dismissNavMenu()
    {showNavMenu = false }

    fun getNavigationMenu(navController: NavController): List<NavigationOptions>{

        return listOf(
            NavigationOptions("Homepage")
            {navigationFunctions.goToHomepage(navController)},
            NavigationOptions("Profile Settings")
            {navigationFunctions.goToProfile(navController)}
        )
    }

    fun profileNav(navController: NavController){
        Log.d("TEST", "Navigating to the profile...")
        navigationFunctions.goToProfile(navController)
    }
}