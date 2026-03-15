package com.example.starstudent.core.view.screens


import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.starstudent.core.data.DatabaseSingleton
import com.example.starstudent.core.domain.CurrentApplication
import com.example.starstudent.core.domain.navigation.NavigationOptions
import com.example.starstudent.core.domain.navigation.NavigationFunctions
import com.example.starstudent.userAccounts.data.entities.UserInfo
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

/* ViewModel for the homepage.
*/
class HomepageViewModel : ViewModel(){

    private val navigationFunctions = NavigationFunctions()
    val userInfo = DatabaseSingleton
        .getDatabase(
            CurrentApplication
                .instance)
        .userInfoDao()

    var showNavMenu by mutableStateOf(
        false
    )
        private set

    var username by mutableStateOf(
        CurrentApplication
            .instance
            .getUserInfo()
            .username)
        private set

    var curUserInfo: List<UserInfo> by mutableStateOf(
        listOf(
            (UserInfo
                (CurrentApplication
                    .instance
                    .user.email
                    .getEmail(),
                "default",
                0,
                false)
                    )
        )
    )
        private set

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

    fun getNavigationMenu(navController: NavController): List<NavigationOptions>{

        return listOf(
           NavigationOptions("Study Centre")
                {navigationFunctions.goToStudyCentre(navController)},
            NavigationOptions("Profile Settings")
                {navigationFunctions.goToProfile(navController)}
        )
    }

    fun getUser(){
        viewModelScope.launch {
            curUserInfo = userInfo.getUserInfo(
                CurrentApplication
                    .instance
                    .user
                    .email
                    .getEmail())!!

            if(curUserInfo.isNotEmpty()){
                username = curUserInfo[0].username
            }else {
                username = CurrentApplication.instance.user.email.getEmail()
                curUserInfo = listOf(
                    (UserInfo
                        (CurrentApplication
                        .instance
                        .user.email
                        .getEmail(),
                        "default",
                        0,
                        false)
                            ))
            }
        }
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

    fun profileNav(navController: NavController){
        Log.d("TEST", "Navigating to the profile...")
        navigationFunctions.goToProfile(navController)
    }

    fun navStudyCentre(navController: NavController){
        Log.d("TEST", "Navigating to the Study Centre...")
        navigationFunctions.goToStudyCentre(navController)
    }

}