package com.example.starstudent.core.domain.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.starstudent.signInRegister.screens.AddEmailScreen
import com.example.starstudent.signInRegister.screens.CreatePasswordScreen
import com.example.starstudent.core.view.screens.Homepage
import com.example.starstudent.signInRegister.screens.SignInRegisterScreen
import com.example.starstudent.signInRegister.screens.SignInScreen
import com.example.starstudent.signInRegister.screens.VerifyEmailScreen


@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.SignInRegisterScreen.route) {
        //Opening screen (Sign-in or register)
        composable( route = Screens.SignInRegisterScreen.route ) {
            SignInRegisterScreen( navController = navController)
        }
        //Sign in (Existing User)
        composable(route = Screens.SignInScreen.route)
            {
                SignInScreen(navController = navController)
        }

        //User enters their email
        composable(route = Screens.AddEmailScreen.route) {
            AddEmailScreen(navController = navController)
        }

        //Verify the use has access to their email
        composable(route = Screens.VerifyEmailScreen.route) {
            VerifyEmailScreen(navController = navController)
        }

        //Redirect to the homepage
        composable(route = Screens.HomePageScreen.route) {
//            Homepage(navController = navController)
              Homepage()
        }

        //Saves user's new passwords to the database and creates a new user
        composable(route = Screens.CreatePasswordsScreen.route) {
            CreatePasswordScreen(navController = navController)
        }

    }
}