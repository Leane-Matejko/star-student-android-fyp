package com.example.starstudent.signInRegister.screens

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.starstudent.core.domain.navigation.Screens


/* ViewModel for the sign in and register page.
*/
class SignInRegisterViewModel : ViewModel(){

    fun navigateToSignInScreen(navController: NavController){
        navController.navigate(Screens.SignInScreen.route)
    }

    fun navigateToAddEmailScreen(navController: NavController){
        navController.navigate(Screens.AddEmailScreen.route)
    }

}

