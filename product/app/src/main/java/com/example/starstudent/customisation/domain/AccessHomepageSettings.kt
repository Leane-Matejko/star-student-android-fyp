package com.example.starstudent.customisation.domain

import com.example.starstudent.core.data.DatabaseSingleton
import com.example.starstudent.core.domain.CurrentApplication
import com.example.starstudent.customisation.data.entities.HomepageSettings
import com.example.starstudent.userAccounts.data.entities.UserTheme

class AccessHomepageSettings {

    private val homepageSettingsDAO = DatabaseSingleton
        .getDatabase(
            CurrentApplication
                .instance)
        .homepageSettingsDao()

    suspend fun checkIfUserExists() : String{
        val curUserInfo = homepageSettingsDAO.getUserInfo(
            CurrentApplication
                .instance
                .user
                .email
                .getEmail()
        )
        if(curUserInfo?.isNotEmpty() == true){
            return curUserInfo[0].id
        }
        return ""
    }

    suspend fun addNewUser(userSettings : HomepageSettings){
        homepageSettingsDAO.addNewUser(
            userSettings
        )
    }

    suspend fun getHomepageSettings (user : String) : HomepageSettings{
        return homepageSettingsDAO.getHomepageSettings(user)
    }

    suspend fun updateAvatarWindow(
        user : String,
        showStatus : Boolean
    ){
        homepageSettingsDAO.updateAvatarWindow(
            user,
            showStatus
        )
    }

    suspend fun updateStudyProgress(
        user : String,
        showStatus : Boolean
    ){
        homepageSettingsDAO.updateStudyProgress(
            user,
            showStatus
        )
    }

    suspend fun updateSleepProgress(
        user : String,
        showStatus : Boolean
    ){
        homepageSettingsDAO.updateSleepProgress(
            user,
            showStatus
        )
    }

    suspend fun updateOverdueTasks(
        user : String,
        showStatus : Boolean
    ){
        homepageSettingsDAO.updateOverdueTasks(
            user,
            showStatus
        )
    }

    suspend fun updateWeeklyTasks(
        user : String,
        showStatus : Boolean
    ){
        homepageSettingsDAO.updateWeeklyTasks(
            user,
            showStatus
        )
    }

    suspend fun updateAllTasks(
        user : String,
        showStatus : Boolean
    ){
        homepageSettingsDAO.updateAllTasks(
            user,
            showStatus
        )
    }

    suspend fun updateStudyCentreNav(
        user : String,
        showStatus : Boolean
    ){
        homepageSettingsDAO.updateStudyCentreNav(
            user,
            showStatus
        )
    }

    suspend fun updatePlannerNav(
        user : String,
        showStatus : Boolean
    ){
        homepageSettingsDAO.updatePlannerNav(
            user,
            showStatus
        )
    }

    suspend fun updateTaskListNav(
        user : String,
        showStatus : Boolean
    ){
        homepageSettingsDAO.updateTaskListNav(
            user,
            showStatus
        )
    }

    suspend fun updateHistoryNav(
        user : String,
        showStatus : Boolean
    ){
        homepageSettingsDAO.updateHistoryNav(
            user,
            showStatus
        )
    }

    suspend fun updateProfileNav(
        user : String,
        showStatus : Boolean
    ){
        homepageSettingsDAO.updateProfileNav(
            user,
            showStatus
        )
    }
}