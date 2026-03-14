package com.example.starstudent.userAccounts.view.screens

import android.R
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starstudent.core.data.DatabaseSingleton
import com.example.starstudent.core.domain.CurrentApplication
import com.example.starstudent.userAccounts.data.entities.UserInfo
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

class ProfileInformationViewModel() : ViewModel(){

    var enableSettingUpdate by mutableStateOf(false)
        private set

    var profileUsername by mutableStateOf(CurrentApplication.instance.getUserInfo().username)
        private set

    var profileEmail by mutableStateOf(CurrentApplication.instance.getUserInfo().id)
        private set

    var profileBirthday by mutableStateOf(
        convertLongToDate(
            CurrentApplication.instance.getUserInfo().birthday,
            "dd MMMM yyyy"
        )
    )
        private set

    var profileLocationAccess by mutableStateOf(
        CurrentApplication
                .instance
                .getUserInfo()
                .locationAccess
    )
        private set

    var updateUsername by mutableStateOf( CurrentApplication.instance.getUserInfo().username)
        private set

    var updateDD by mutableStateOf(
        convertLongToDate(
            CurrentApplication.instance.getUserInfo().birthday,
            "dd"
        )
    )
        private set

    var updateMM by mutableStateOf(
        convertLongToDate(
            CurrentApplication.instance.getUserInfo().birthday,
            "MM"
        )
    )
        private set

    var updateYYYY by mutableStateOf(
        convertLongToDate(
            CurrentApplication.instance.getUserInfo().birthday,
            "yyyy"
        )
    )
        private set

    var updateLocationAccess by mutableStateOf(
        CurrentApplication
                .instance
                .getUserInfo()
                .locationAccess)
        private set

    val userInfo = DatabaseSingleton
        .getDatabase(CurrentApplication.instance)
        .userInfoDao()

    var username by mutableStateOf(CurrentApplication.instance.getUserInfo().username)
        private set


    var curUserInfo: List<UserInfo> by mutableStateOf(
        listOf(
            (UserInfo(
                CurrentApplication
                    .instance
                    .user
                    .email
                    .getEmail(),
        "default",
        0,
                false
            ))))
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

    fun convertLongToDate(longDate : Long, pattern : String) : String{
        return SimpleDateFormat(pattern , Locale.getDefault()).format(Date(longDate))
    }

    fun profileLocationAccessFormatted() : String{
        if(profileLocationAccess){
            return "Enabled"
        }
        return "Denied"
    }

    fun showDialog(){
        enableSettingUpdate = true
    }

    fun closeDialog(){
        enableSettingUpdate = false
    }

    fun getUser(){
        viewModelScope.launch {
            curUserInfo = userInfo.getUserInfo(
                CurrentApplication
                    .instance
                    .user
                    .email
                    .getEmail())!!

            if (curUserInfo.isNotEmpty()){
                username = curUserInfo[0].username
            }
        }
    }

    fun getBirthday() : Long{
        return CurrentApplication.instance.getUserInfo().birthday
    }

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

        return day.toIntOrNull()!! in 1..12
    }

    fun leapYearCheck(day : String) : Boolean {
        if (updateYYYY.toIntOrNull()?.div(4) == 0){
            return (day.toIntOrNull()!! <= 29)
        }
        return (day.toIntOrNull()!! <= 28)
    }

    fun withinDayRange(day : String) : Boolean{

        val result =
            when (updateMM.toIntOrNull()) {
            1, 3, 5, 7, 8, 10, 12 -> (day.toIntOrNull()!! <= 31)
            4, 6, 9, 11 -> (day.toIntOrNull()!! <= 30)
            2 -> (leapYearCheck(day))
                else -> {false}
            }

        return result
    }

    fun isRealYear(year : String) : Boolean{
        return (year.toIntOrNull()!! <= LocalDateTime.now().year )
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

    fun convertBirthdayToLong() : Long{

        val birthdayDay = convertLongToDate(
            getBirthday(),
            "dd")
        val birthdayMonth = convertLongToDate(
            getBirthday(),
            "MM")
        val birthdayYear = convertLongToDate(
            getBirthday(),
            "yyyy")

        var day = updateDD
        var month = updateMM
        var year = updateYYYY

        day.ifBlank{
            day = birthdayDay
        }
        month.ifBlank{
            month = birthdayMonth
        }
        year.ifBlank{
            year = birthdayYear
        }

        val newDate = LocalDate.of(
            year.toInt(),
            month.toInt(),
            day.toInt())

        return newDate
            .atStartOfDay(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    }

    fun updateProfile(){
        profileUsername = updateUsername

        profileBirthday = convertLongToDate(
            convertBirthdayToLong(),
            "dd MMMM yyyy"
        )

        profileLocationAccess = updateLocationAccess
    }

    fun saveChanges(){

        viewModelScope.launch {
            if(!updateUsername.isEmpty()){
                userInfo.updateUsername(
                    CurrentApplication
                        .instance
                        .user
                        .email
                        .getEmail(),
                    updateUsername
                )
            }
            userInfo.updateBirthday(
                CurrentApplication
                        .instance
                        .user
                        .email
                        .getEmail(),
                    convertBirthdayToLong()
            )
            userInfo.updateLocationAccess(
                CurrentApplication
                    .instance
                    .user
                    .email
                    .getEmail(),
                updateLocationAccess
            )
            updateProfile()
            closeDialog()
            //possible check to see if the db updated before closing
        }
    }
}