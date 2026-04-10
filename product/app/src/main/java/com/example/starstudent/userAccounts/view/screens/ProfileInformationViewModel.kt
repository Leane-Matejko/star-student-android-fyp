package com.example.starstudent.userAccounts.view.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.starstudent.core.domain.CurrentApplication
import com.example.starstudent.core.domain.Themes
import com.example.starstudent.core.domain.navigation.NavigationOptions
import com.example.starstudent.core.domain.navigation.NavigationFunctions
import com.example.starstudent.core.view.screens.ApplicationViewModel
import com.example.starstudent.userAccounts.data.AccessUserData
import com.example.starstudent.userAccounts.data.AccessUserTheme
import com.example.starstudent.userAccounts.domain.FormatProfile
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class ProfileInformationViewModel() : ViewModel(){

    val accessUserData = AccessUserData()

    val accessUserTheme = AccessUserTheme()

    val formatProfile = FormatProfile()

    private val navigationFunctions = NavigationFunctions()

    val themeOptions = formatProfile.themeOptions

    var showNavMenu by mutableStateOf(
        false
    )
        private set

    var enableSettingUpdate by mutableStateOf(false)
        private set

    var profileUsername by mutableStateOf(
        accessUserData.getUsername()
    )
        private set

    var profileEmail by mutableStateOf(
        accessUserData.getUserId()
    )
        private set

    var profileBirthday by mutableStateOf(
        formatProfile.convertLongToDate(
            accessUserData.getBirthday(),
            "dd MMMM yyyy"
        )
    )
        private set

    var profileLocationAccess by mutableStateOf(
        accessUserData
                    .getUserLocationAccess()
    )
        private set

    var updateUsername by mutableStateOf(
        accessUserData.getUsername()
    )
        private set

    var updateDD by mutableStateOf(
        formatProfile.convertLongToDate(
            accessUserData.getBirthday(),
            "dd"
        )
    )
        private set

    var updateMM by mutableStateOf(
        formatProfile.convertLongToDate(
            accessUserData.getBirthday(),
            "MM"
        )
    )
        private set

    var updateYYYY by mutableStateOf(
        formatProfile.convertLongToDate(
            accessUserData.getBirthday(),
            "yyyy"
        )
    )
        private set

    var updateLocationAccess by mutableStateOf(
        accessUserData.getUserLocationAccess())
        private set

    var username by mutableStateOf(
        accessUserData.getUsername()
    )
        private set

    var userId = CurrentApplication.instance.user.email.getEmail()

    var curDate by mutableStateOf(
        LocalDateTime
            .now()
            .format(
                DateTimeFormatter
                    .ofPattern(
                        "EEE d MMMM, HH:mm",
                        Locale.getDefault()
                    )
            )
    )
        private set

    var currentTheme by mutableStateOf(
        "system"
    )
        private set

    var showThemeOptions by mutableStateOf(
        false
    )
        private set

    //Get the navigation options for the profile settings (all windows wo/ profile settings)
    fun getNavigationMenu(navController: NavController): List<NavigationOptions>{

        val navigationFunctions = NavigationFunctions()

        return listOf(
            NavigationOptions("Homepage")
                { navigationFunctions.goToHomepage(navController) },
            NavigationOptions("Study Centre")
                { navigationFunctions.goToStudyCentre(navController) },
            NavigationOptions("Planner")
                { navigationFunctions.goToPlanner(navController) },
            NavigationOptions("Task List")
            { navigationFunctions.goToTaskList(navController) },
            NavigationOptions("History")
            {navigationFunctions.goToHistory(navController)},
        )
    }

    //Return string of formatted location access
    fun profileLocationAccessFormatted() : String{
        return formatProfile.profileLocationAccessFormatted(
            profileLocationAccess
        )
    }

    //Show the profile setting dialog
    fun showDialog(){
        enableSettingUpdate = true
    }

    //Hide the profile setting dialog
    fun closeDialog(){
        enableSettingUpdate = false
    }

    //Show the navigation menu
    fun showNavMenu()
    {showNavMenu = true}

    //Hide the navigation menu
    fun dismissNavMenu()
    {showNavMenu = false }

    //Update the date time of the top banner
    fun updateTime(){
        curDate = LocalDateTime
            .now()
            .format(
                DateTimeFormatter
                    .ofPattern(
                        "EEE d MMMM, HH:mm",
                        Locale.getDefault()
                    )
            )
    }

    //Update the temporary username value
    fun updateUsernameChange(username: String){
        updateUsername = username
    }

    //Update the temporary day value
    fun updateDDChange(day: String){
        updateDD = day
    }

    //Update the temporary month value
    fun updateMMChange(month: String){
        updateMM = month
    }

    //Update the temporary year value
    fun updateYYYYChange(year: String){
        updateYYYY = year
    }

    //Update the temporary location access value
    fun updateLocationAccess(){
        updateLocationAccess = !updateLocationAccess
    }

    //Check if the month is valid
    fun withinMonthRange(day : String) : Boolean{
        return formatProfile.withinMonthRange(day)
    }

    //Check if the day is valid
    fun withinDayRange(day : String) : Boolean{

        return formatProfile.withinDayRange(
            day,
            updateMM,
            updateYYYY
        )
    }

    //Check if the year is valid
    fun isRealYear(year : String) : Boolean{
        return formatProfile.isRealYear(year)
    }

    //Set the temporary day value to null
    fun setDayNull(){
        updateDD = ""
    }

    //Set the temporary month value to null
    fun setMonthNull(){
        updateMM = ""
    }

    //Set the temporary year value to null
    fun setYearNull(){
        updateYYYY = ""
    }

    //Update screen variables of stored profile values
    fun updateProfile(){
        profileUsername = updateUsername
        profileBirthday = formatProfile.convertLongToDate(
            formatProfile.convertBirthdayToLong(
                accessUserData.getBirthday(),
                updateDD,
                updateMM,
                updateYYYY
            ),
            "dd MMMM yyyy"
        )
        profileLocationAccess = updateLocationAccess
    }

    //Show theme options dropdown
    fun showThemeOptions(){
        showThemeOptions = true
    }

    //Hide theme options dropdown
    fun hideThemeOptions(){
        showThemeOptions = false
    }

    //Save new profile settings to the database
    fun saveChanges(){
        viewModelScope.launch {
            if(!updateUsername.isEmpty()){
                accessUserData.updateUsername(
                    updateUsername
                )
            }
            accessUserData.updateBirthday(
                formatProfile.convertBirthdayToLong(
                    accessUserData.getBirthday(),
                    updateDD,
                    updateMM,
                    updateYYYY
                )
            )
            accessUserData.updateLocationAccess(
                updateLocationAccess
            )
            updateProfile()
            closeDialog()
            //possible check to see if the db updated before closing
        }
    }

    //Update the user's theme throughout the app
    suspend fun updateTheme(
        themeKey: String,
        applicationViewModel: ApplicationViewModel
                    ){
        currentTheme = themeKey
        val theme = getThemeColor()
        applicationViewModel.setTheme(theme)
        updateCurrentTheme()
        hideThemeOptions()
    }

    //Retrieve the enumerated theme option
    fun getThemeColor() : Themes{
        return formatProfile.getThemeFromString(currentTheme)
    }

    //Retrieve the user's saved them
    suspend fun getCurrentTheme(){
        currentTheme = accessUserTheme.getUserTheme(
            userId
        )
    }

    //Update the stored user theme
    suspend fun updateCurrentTheme(){
        accessUserTheme.updateUserTheme(
            userId,
            currentTheme
        )
    }

    //Navigates to the user's profile
    fun profileNav(navController: NavController) {
        Log.d("TEST", "Navigating to the profile...")
        navigationFunctions.goToProfile(navController)
    }
}