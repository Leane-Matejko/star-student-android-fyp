package com.example.starstudent.signInRegister.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.starstudent.core.domain.navigation.Screens
import com.example.starstudent.signInRegister.domain.Password

class CreatePasswordViewModel : ViewModel(){

    val password = Password()

    var firstPassword by mutableStateOf("")
        private set

    var secondPassword by mutableStateOf("")
        private set

    var errorWindow by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf("")
        private set

    fun verify(navController: NavController){
        try{
            if(password.checkPasswordsMatch(firstPassword, secondPassword)){
                navController.navigate(Screens.HomePageScreen.route)
            }

        }catch (e: Exception){
            Log.d("TEST", "Error thrown")
            errorMessage = e.message.toString()
            resetErrorWindow()
            errorWindow = true
        }
    }

    fun resetErrorWindow(){
        errorWindow = false
    }

    fun setFirstPasswordValue(passwordValue: String){
        firstPassword = passwordValue
    }

    fun setSecondPasswordValue(passwordValue: String){
        secondPassword = passwordValue
    }

}