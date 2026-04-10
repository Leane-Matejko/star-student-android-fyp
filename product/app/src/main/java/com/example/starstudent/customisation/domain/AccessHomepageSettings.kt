package com.example.starstudent.customisation.domain

import com.example.starstudent.core.data.DatabaseSingleton
import com.example.starstudent.core.domain.CurrentApplication
import com.example.starstudent.customisation.data.entities.HomepageSettings

class AccessHomepageSettings {

    //Data access object for the homepage_settings table
    private val homepageSettingsDAO = DatabaseSingleton
        .getDatabase(
            CurrentApplication
                .instance)
        .homepageSettingsDao()

    //Check if the user already exists in the database
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

    //Adds a new user to homepage with default to show all widgets
    suspend fun addNewUser(userSettings : HomepageSettings){
        homepageSettingsDAO.addNewUser(
            userSettings
        )
    }

    //Get the current homepage customisation settings
    suspend fun getHomepageSettings (user : String) : HomepageSettings{
        return homepageSettingsDAO.getHomepageSettings(user)
    }

    //Updates the visibility of the avatar window widget
    suspend fun updateAvatarWindow(
        user : String,
        showStatus : Boolean
    ){
        homepageSettingsDAO.updateAvatarWindow(
            user,
            showStatus
        )
    }

    //Updates the visibility of the study progress widget
    suspend fun updateStudyProgress(
        user : String,
        showStatus : Boolean
    ){
        homepageSettingsDAO.updateStudyProgress(
            user,
            showStatus
        )
    }

    //Updates the visibility of the sleep progress widget
    suspend fun updateSleepProgress(
        user : String,
        showStatus : Boolean
    ){
        homepageSettingsDAO.updateSleepProgress(
            user,
            showStatus
        )
    }

    //Updates the visibility of the overdue tasks widget
    suspend fun updateOverdueTasks(
        user : String,
        showStatus : Boolean
    ){
        homepageSettingsDAO.updateOverdueTasks(
            user,
            showStatus
        )
    }

    //Updates the visibility of the weekly tasks widget
    suspend fun updateWeeklyTasks(
        user : String,
        showStatus : Boolean
    ){
        homepageSettingsDAO.updateWeeklyTasks(
            user,
            showStatus
        )
    }

    //Updates the visibility of the all tasks widget
    suspend fun updateAllTasks(
        user : String,
        showStatus : Boolean
    ){
        homepageSettingsDAO.updateAllTasks(
            user,
            showStatus
        )
    }

    //Updates the visibility of the study centre navigation widget
    suspend fun updateStudyCentreNav(
        user : String,
        showStatus : Boolean
    ){
        homepageSettingsDAO.updateStudyCentreNav(
            user,
            showStatus
        )
    }

    //Updates the visibility of the planner navigation widget
    suspend fun updatePlannerNav(
        user : String,
        showStatus : Boolean
    ){
        homepageSettingsDAO.updatePlannerNav(
            user,
            showStatus
        )
    }

    //Updates the visibility of the task list widget
    suspend fun updateTaskListNav(
        user : String,
        showStatus : Boolean
    ){
        homepageSettingsDAO.updateTaskListNav(
            user,
            showStatus
        )
    }

    //Updates the visibility of the history navigation widget
    suspend fun updateHistoryNav(
        user : String,
        showStatus : Boolean
    ){
        homepageSettingsDAO.updateHistoryNav(
            user,
            showStatus
        )
    }

    //Updates the visibility of the profile navigation widget
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