package com.example.starstudent.signInRegister.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.starstudent.core.domain.CurrentApplication
import com.example.starstudent.core.domain.navigation.Screens
import com.example.starstudent.signInRegister.domain.Email
import kotlinx.coroutines.launch

/* ViewModel for the add email view.
*/
class AddEmailViewModel() : ViewModel(){

    private val email = Email()

    var emailString by mutableStateOf("")
        private set

    var errorWindow by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf("")
        private set


    //Checks if the email enter is real, and then setting the user email to the account.
    fun next(navController: NavController) {
        viewModelScope.launch {
            try {
                email.setEmail(emailString)
                if ((email.checkRealEmail()) && !(email.preexistingUser())) {
                    errorWindow = false
                    CurrentApplication.instance.setUser(email.getEmail())
                    Log.d("TEST", email.getEmail())
                    navigateToCreatePassword(navController)
                }
            } catch (e: Exception){
                Log.d("TEST", "Error thrown")
                errorMessage = e.message.toString()
                resetErrorWindow()
                errorWindow = true
            }
        }
    }

    //Navigate the create password screen.
    fun navigateToCreatePassword(navController: NavController){
        navController.navigate(Screens.CreatePasswordsScreen.route)
    }

    //Reset the error notification pop-up.
    fun resetErrorWindow(){
        errorWindow = false
    }

    //Represent the email enter within the input field.
    fun setEmailChange(emailValue: String){
        emailString = emailValue
    }

}


//Buffer for loading
private fun Boolean.await() {
    Log.d("AWAIT", "CHECKING EMAIL STRING...")
}
