package com.example.starstudent.signInRegister.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.starstudent.core.data.DatabaseSingleton
import com.example.starstudent.core.domain.CurrentApplication
import com.example.starstudent.core.domain.navigation.Screens
import com.example.starstudent.customisation.data.entities.HomepageSettings
import com.example.starstudent.customisation.domain.AccessHomepageSettings
import com.example.starstudent.signInRegister.domain.Email
import com.example.starstudent.signInRegister.domain.EmailOrPasswordNotCorrectException
import com.example.starstudent.studySpaces.data.dao.SavedLocationsDAO
import com.example.starstudent.studySpaces.data.entities.SavedLocations
import com.example.starstudent.userAccounts.data.AccessUserTheme
import com.example.starstudent.userAccounts.data.dao.UserThemeDAO
import com.example.starstudent.userAccounts.data.entities.UserInfo
import com.example.starstudent.userAccounts.data.entities.UserTheme
import kotlinx.coroutines.launch


/* ViewModel responsible for the Sign In page.
*/
class SignInViewModel : ViewModel() {

    private val email = Email()

    private val savedLocationsDAO =
        DatabaseSingleton
            .getDatabase(
                CurrentApplication.instance
            ).savedLocationsDao()

    private val accessUserTheme = AccessUserTheme()
    private val accessHomepageSettings = AccessHomepageSettings()

    var currentUser by mutableStateOf(UserInfo(
        "default",
        "default",
        0,
        false
    ))
        private set

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
        viewModelScope.launch {
            email.setEmail(emailString)
            try {
                if (email.checkRealEmail()) {
                    Log.d("TEST", "Checking credentials...")
                    errorWindow = false

                    val passwordValidation = email.checkEmailExists(emailString, passwordString)
                    Log.d("TEST", "Validating...")
                    if (passwordValidation) {
                        Log.d("TEST", "Found. Loading homepage...")
                        CurrentApplication.instance.setUser(email.getEmail())
                        CurrentApplication.instance.setUserInfo()
                        setupSavedLocations()
                        setupSavedTheme()
                        setupHomepageSettings()
                        navigateToHomepage(navController)
                    }
                }

            } catch (e: Exception) {
                Log.d("TEST", "Error thrown")
                errorMessage = e.message.toString()
                Log.d("ERROR" , errorMessage)
                resetErrorWindow()
                errorWindow = true
            }
        }
    }

    private fun setUserEmail(email: Email){
        CurrentApplication.instance.setUser(email.getEmail())
    }

    private suspend fun setupSavedLocations(){
        val checkUser = savedLocationsDAO.checkUserExists(email.getEmail())
        if(checkUser.isEmpty()){
            for(i in 1..5){
                savedLocationsDAO.addInitialLocations(
                    SavedLocations(
                        user = email.getEmail(),
                        label = "Default",
                        longitude = 0.0,
                        latitude = 0.0
                    )
                )
            }
        }
    }

    private suspend fun setupSavedTheme(){
        val checkUser = accessUserTheme.checkIfUserExists()
        if(checkUser.isEmpty()) {
            accessUserTheme.addNewUser(
                UserTheme(
                    CurrentApplication.instance.user.email.getEmail(),
                    "system"
                )
            )
        }

    }

    private suspend fun setupHomepageSettings(){
        val checkUser = accessHomepageSettings.checkIfUserExists()
        if(checkUser.isEmpty()) {
            accessHomepageSettings.addNewUser(
                HomepageSettings(
                    CurrentApplication.instance.user.email.getEmail(),
                    avatarWindow = true,
                    studyProgress = true,
                    sleepProgress = true,
                    overdueTasks = true,
                    weeklyTasks = true,
                    allTasks = true,
                    studyCentreNav = true,
                    plannerNav = true,
                    taskListNav = true,
                    historyNav = true,
                    profileNav = true,

                    )
            )
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