package com.example.starstudent.signInRegister.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.starstudent.core.domain.navigation.Screens
import com.example.starstudent.signInRegister.domain.Email
import com.example.starstudent.signInRegister.domain.EmailOrPasswordNotCorrectException
import kotlinx.coroutines.launch

class SignInViewModel : ViewModel() {

    private val email = Email()

    var emailString by mutableStateOf("")
        private set

    var passwordString by mutableStateOf("")
        private set

    var errorWindow by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf("")
        private set

    fun setEmailChange(emailValue: String){
        emailString = emailValue
    }

    fun setPasswordChange(passwordValue: String){
        passwordString = passwordValue
    }

    fun resetErrorWindow(){
        errorWindow = false
    }

    fun next(navController: NavController){
        email.setEmail(emailString)
        try{
            if (email.checkRealEmail()){
                errorWindow = false
                viewModelScope.launch {
                    val passwordValidation = email.checkEmailExists(emailString, passwordString)
                    if(passwordValidation){
                        navigateToHomepage(navController)
                    }
                }
            }
        }catch(e: Exception){
            Log.d("TEST", "Error thrown")
            errorMessage = e.message.toString()
            resetErrorWindow()
            errorWindow = true
        }
    }

    private fun Unit.await() {
        Log.d("AWAIT", "CHECKING PASSWORD....")
    }

    fun navigateToHomepage(navController: NavController){
        navController.navigate(Screens.HomePageScreen.route)
    }
}