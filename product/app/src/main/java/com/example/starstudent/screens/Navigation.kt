package com.example.starstudent.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.starstudent.screens.SignInScreen


@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.SignInRegisterScreen.route) {
        //Opening screen (Sign-in or register)
        composable(route = Screens.SignInRegisterScreen.route) {
            SignInRegisterScreen(navController = navController)
        }
        //Sign in (Existing User)
        composable(route = Screens.SignInScreen.route)
            {
            SignInScreen(navController = navController)
        }

        //User enters their email - will check to
        composable(route = Screens.AddEmailScreen.route) {
            AddEmailScreen(navController = navController)
        }

        composable(route = Screens.VerifyEmailScreen.route) {
            VerifyEmail(navController = navController)
        }

        composable(route = Screens.HomeScreen.route) {
            Homepage(navController = navController)
        }
        composable(route = Screens.CreatePasswordsScreen.route) {
            CreatePassword(navController = navController)
        }

    }
}