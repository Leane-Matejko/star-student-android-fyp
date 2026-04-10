package com.example.starstudent.customisation.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.starstudent.customisation.data.entities.HomepageSettings

//Data access object for the homepage customisation settings
@Dao
interface HomepageSettingsDAO {

    //Insert new user with default values
    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    suspend fun addNewUser(newUser: HomepageSettings)

    //Return list of user's information
    @Query("" +
            "SELECT * " +
            "FROM homepage_settings " +
            "WHERE id = :user")
    suspend fun getUserInfo(user: String) :  List<HomepageSettings>?

    //Return list of user's homepage settings
    @Query("" +
            "SELECT * " +
            "FROM homepage_settings " +
            "WHERE id = :user"
    )
    suspend fun getHomepageSettings(user: String) :  HomepageSettings

    //Updates the visibility of the avatar window widget
    @Query("" +
            "UPDATE homepage_settings " +
            "SET avatarWindow = :showStatus " +
            "WHERE id = :user")
    suspend fun updateAvatarWindow(
        user: String,
        showStatus : Boolean
    )

    //Updates the visibility of the study progress widget
    @Query("" +
            "UPDATE homepage_settings " +
            "SET studyProgress = :showStatus " +
            "WHERE id = :user")
    suspend fun updateStudyProgress(
        user: String,
        showStatus : Boolean
    )

    //Updates the visibility of the sleep progress widget
    @Query("" +
            "UPDATE homepage_settings " +
            "SET sleepProgress = :showStatus " +
            "WHERE id = :user")
    suspend fun updateSleepProgress(
        user: String,
        showStatus : Boolean
    )

    //Updates the visibility of the overdue tasks widget
    @Query("" +
            "UPDATE homepage_settings " +
            "SET overdueTasks = :showStatus " +
            "WHERE id = :user")
    suspend fun updateOverdueTasks(
        user: String,
        showStatus : Boolean
    )

    //Updates the visibility of the weekly tasks widget
    @Query("" +
            "UPDATE homepage_settings " +
            "SET weeklyTasks = :showStatus " +
            "WHERE id = :user")
    suspend fun updateWeeklyTasks(
        user: String,
        showStatus : Boolean
    )

    //Updates the visibility of the all tasks widget
    @Query("" +
            "UPDATE homepage_settings " +
            "SET allTasks = :showStatus " +
            "WHERE id = :user")
    suspend fun updateAllTasks(
        user: String,
        showStatus : Boolean
    )

    //Updates the visibility of the study centre navigation widget
    @Query("" +
            "UPDATE homepage_settings " +
            "SET studyCentreNav = :showStatus " +
            "WHERE id = :user")
    suspend fun updateStudyCentreNav(
        user: String,
        showStatus : Boolean
    )

    //Updates the visibility of the planner navigation widget
    @Query("" +
            "UPDATE homepage_settings " +
            "SET plannerNav = :showStatus " +
            "WHERE id = :user")
    suspend fun updatePlannerNav(
        user: String,
        showStatus : Boolean
    )

    //Updates the visibility of the task list widget
    @Query("" +
            "UPDATE homepage_settings " +
            "SET taskListNav = :showStatus " +
            "WHERE id = :user")
    suspend fun updateTaskListNav(
        user: String,
        showStatus : Boolean
    )

    //Updates the visibility of the history navigation widget
    @Query("" +
            "UPDATE homepage_settings " +
            "SET historyNav = :showStatus " +
            "WHERE id = :user")
    suspend fun updateHistoryNav(
        user: String,
        showStatus : Boolean
    )

    //Updates the visibility of the profile navigation widget
    @Query("" +
            "UPDATE homepage_settings " +
            "SET profileNav = :showStatus " +
            "WHERE id = :user")
    suspend fun updateProfileNav(
        user: String,
        showStatus : Boolean
    )
}