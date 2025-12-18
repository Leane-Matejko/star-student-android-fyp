package com.example.starstudent.signInRegister.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.starstudent.core.domain.navigation.Screens

class SignInViewModel : ViewModel() {

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    fun setEmailChange(emailValue: String){
        email = emailValue
        Log.d("TEST", emailValue)
    }

    fun setPasswordChange(passwordValue: String){
        password = passwordValue
        Log.d("TEST", passwordValue)
    }

    fun navigateToHomepage(navController: NavController){
        navController.navigate(Screens.HomePageScreen.route)
    }
}