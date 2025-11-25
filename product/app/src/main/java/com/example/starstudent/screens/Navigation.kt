package com.example.starstudent.screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.starstudent.screens.uiComponents.SignInScreen


@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(  navController = navController, startDestination = Screens.SignInRegisterScreen.route){
        //Opening screen (Sign-in or register)
        composable(route = Screens.SignInRegisterScreen.route){
            SignInRegisterScreen(navController = navController)
        }
        //Sign in (Existing User)
        composable ( route = Screens.SignInScreen.route){
            SignInScreen(navController = navController)
        }
    }
}