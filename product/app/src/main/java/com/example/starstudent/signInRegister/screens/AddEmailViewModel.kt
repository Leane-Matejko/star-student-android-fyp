package com.example.starstudent.signInRegister.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.starstudent.core.domain.CurrentApplication
import com.example.starstudent.core.domain.navigation.Screens
import com.example.starstudent.signInRegister.domain.Email

class AddEmailViewModel() : ViewModel(){

    private val email = Email()

    var emailString by mutableStateOf("")
        private set

    var errorWindow by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf("")
        private set

    fun next(navController: NavController){
        try{
          email.setEmail(emailString)
            if (email.checkRealEmail()){
                errorWindow = false
                CurrentApplication.instance.setUser(email.getEmail())
                Log.d("TEST", email.getEmail())
                navigateToCreatePassword(navController)
            }
            }catch(e: Exception){
                Log.d("TEST", "Error thrown")
                errorMessage = e.message.toString()
                resetErrorWindow()
                errorWindow = true
            }
    }

    fun navigateToCreatePassword(navController: NavController){
        navController.navigate(Screens.CreatePasswordsScreen.route)
    }

    fun resetErrorWindow(){
        errorWindow = false
    }

    fun setEmailChange(emailValue: String){
        emailString = emailValue
    }

}

private fun Boolean.await() {
    Log.d("AWAIT", "CHECKING EMAIL STRING...")
}
