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
import com.example.starstudent.signInRegister.domain.Password
import com.example.starstudent.studySpaces.data.entities.SavedLocations
import com.example.starstudent.userAccounts.data.AccessUserTheme
import com.example.starstudent.userAccounts.data.entities.AppUserData
import com.example.starstudent.userAccounts.data.entities.UserInfo
import com.example.starstudent.userAccounts.data.entities.UserTheme
import kotlinx.coroutines.launch



/* ViewModel for the create password page.
*/
class CreatePasswordViewModel : ViewModel(){

    val password = Password()

    val savedLocationsDAO =
        DatabaseSingleton
            .getDatabase(
                CurrentApplication.instance
    ).savedLocationsDao()

    val accessUserTheme = AccessUserTheme()

    val accessHomepageSettings = AccessHomepageSettings()

    val userEmail = CurrentApplication.instance.user.email.getEmail()

    var firstPassword by mutableStateOf("")
        private set

    var secondPassword by mutableStateOf("")
        private set

    var errorWindow by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf("")
        private set

    //Verify if the passwords are matching, if so then add the user to the database.
    fun verify(navController: NavController){
        viewModelScope.launch {
            try {
                if (password.checkPasswordsMatch(firstPassword, secondPassword)) {
                    CurrentApplication.instance.user.addUserAccount(firstPassword).await()

                    Log.d("USER", "Added New User")

                    Log.d("Adding new user", "Adding the new user to the phone's db")

                    val userDataDAO = DatabaseSingleton
                        .getDatabase(CurrentApplication.instance)
                        .appUserDao()

                    userDataDAO.insertUser(
                        AppUserData(
                            id = CurrentApplication.instance.user.email.getEmail()
                        )
                    )

                    val userInfoDAO = DatabaseSingleton
                        .getDatabase(CurrentApplication.instance)
                        .userInfoDao()

                    userInfoDAO.addNewUser(
                        UserInfo(
                            id = CurrentApplication.instance.user.email.getEmail(),
                            username = CurrentApplication.instance.user.email.getEmail(),
                            birthday = System.currentTimeMillis(),
                            locationAccess = false
                        )
                    )

                    CurrentApplication.instance.setUserInfo()

                    setupSavedLocations()

                    setupSavedTheme()

                    setupHomepageSettings()

                    Log.d("NEW USER ADDED", "New users added to the phone's db.")

                    navController.navigate(Screens.HomePageScreen.route)
                }

            } catch (e: Exception) {
                Log.d("TEST", "Error thrown")
                errorMessage = e.message.toString()
                resetErrorWindow()
                errorWindow = true
            }
        }
    }

    private suspend fun setupSavedLocations(){
        val checkUser = savedLocationsDAO.checkUserExists(CurrentApplication.instance.user.email.getEmail())
        if(checkUser.isEmpty()){
            for(i in 1..5){
                savedLocationsDAO.addInitialLocations(
                    SavedLocations(
                        user = CurrentApplication.instance.user.email.getEmail(),
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

    //Reset the error notification pop-up.
    fun resetErrorWindow(){
        errorWindow = false
    }

    //Represent the first password entered within the input field.
    fun setFirstPasswordValue(passwordValue: String){
        firstPassword = passwordValue
    }

    //Represent the second entered within the input field.
    fun setSecondPasswordValue(passwordValue: String){
        secondPassword = passwordValue
    }

    //Buffer added for connecting to the firebase database.
    private fun Unit.await() {
        Log.d("AWAIT", "DATABASE CONNECTION....")
    }


}