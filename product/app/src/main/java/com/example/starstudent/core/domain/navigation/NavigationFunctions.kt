package com.example.starstudent.core.domain.navigation

import androidx.navigation.NavController

class NavigationFunctions {

    fun goToHomepage(navController: NavController){
        navController.navigate(Screens.HomePageScreen.route)
    }

    fun goToProfile(navController: NavController){
        navController.navigate(Screens.ProfileInformationScreen.route)
    }

    fun goToStudyCentre(navController: NavController){
        navController.navigate(Screens.StudyCentreScreen.route)
    }
}