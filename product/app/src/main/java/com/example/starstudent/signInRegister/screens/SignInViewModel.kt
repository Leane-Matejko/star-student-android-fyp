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


/* ViewModel responsible for the Sign In page.
*/
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

    //Represent the email entered within the input field.
    fun setEmailChange(emailValue: String){
        emailString = emailValue
    }
    //Represent the password entered within the input field.
    fun setPasswordChange(passwordValue: String){
        passwordString = passwordValue
    }


    //Resetting the error window.
    fun resetErrorWindow(){
        errorWindow = false
    }


    //Checks if the user's email is real, and then validated the user account. If successful then move onto the homepage
    fun next(navController: NavController){
        email.setEmail(emailString)
        try{
            if (email.checkRealEmail()){
                Log.d("TEST", "Checking credentials...")
                errorWindow = false
                viewModelScope.launch {
                    val passwordValidation = email.checkEmailExists(emailString, passwordString)
                    Log.d("TEST", "Validating...")
                    if(passwordValidation){
                        Log.d("TEST", "Found. Loading homepage...")
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

    //Buffer for checking the password
    private fun Unit.await() {
        Log.d("AWAIT", "CHECKING PASSWORD....")
    }


    //Navigation the homepage
    fun navigateToHomepage(navController: NavController){
        navController.navigate(Screens.HomePageScreen.route)
    }
}