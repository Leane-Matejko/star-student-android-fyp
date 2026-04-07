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

    fun goToPlanner(navController: NavController){
        navController.navigate(Screens.PlannerScreen.route)
    }

    fun goToTaskList(navController: NavController){
        navController.navigate(Screens.TaskListScreen.route)
    }

    fun goToHistory(navController: NavController){
        navController.navigate(Screens.HistoryScreen.route)
    }

    fun goToHistoryStudySessions(navController: NavController){
        navController.navigate(Screens.HistoryStudySessionsScreen.route)
    }
}