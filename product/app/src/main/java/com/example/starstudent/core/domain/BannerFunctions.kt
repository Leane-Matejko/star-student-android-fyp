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

    fun profileNav(navController: NavController){
        Log.d("Navigation Functions", "Navigating to the profile...")
        navigationFunctions.goToProfile(navController)
    }

    fun getNavigationMenu(navController: NavController): List<NavigationOptions>{
        return listOf(
            NavigationOptions("Homepage")
            {navigationFunctions.goToHomepage(navController)},
            NavigationOptions("Profile Settings")
            {navigationFunctions.goToProfile(navController)}
        )
    }
}