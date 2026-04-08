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

    fun profileLocationAccessFormatted() : String{
        return formatProfile.profileLocationAccessFormatted(
            profileLocationAccess
        )
    }

    fun showDialog(){
        enableSettingUpdate = true
    }

    fun closeDialog(){
        enableSettingUpdate = false
    }

    fun showNavMenu()
    {showNavMenu = true}

    fun dismissNavMenu()
    {showNavMenu = false }

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

    fun updateUsernameChange(username: String){
        updateUsername = username
    }

    fun updateDDChange(day: String){
        updateDD = day
    }

    fun updateMMChange(month: String){
        updateMM = month
    }

    fun updateYYYYChange(year: String){
        updateYYYY = year
    }

    fun updateLocationAccess(){
        updateLocationAccess = !updateLocationAccess
    }

    fun withinMonthRange(day : String) : Boolean{
        return formatProfile.withinMonthRange(day)
    }

    fun withinDayRange(day : String) : Boolean{

        return formatProfile.withinDayRange(
            day,
            updateMM,
            updateYYYY
        )
    }

    fun isRealYear(year : String) : Boolean{
        return formatProfile.isRealYear(year)
    }

    fun setDayNull(){
        updateDD = ""
    }
    fun setMonthNull(){
        updateMM = ""
    }
    fun setYearNull(){
        updateYYYY = ""
    }

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

    fun showThemeOptions(){
        showThemeOptions = true
    }

    fun hideThemeOptions(){
        showThemeOptions = false
    }

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

//    fun setNewTheme(
//        newTheme : String
//    ){
//        currentTheme = newTheme
//    }

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

    fun getThemeColor() : Themes{
        return formatProfile.getThemeFromString(currentTheme)
    }

    suspend fun getCurrentTheme(){
        currentTheme = accessUserTheme.getUserTheme(
            userId
        )
    }

    suspend fun updateCurrentTheme(){
        accessUserTheme.updateUserTheme(
            userId,
            currentTheme
        )
    }
}