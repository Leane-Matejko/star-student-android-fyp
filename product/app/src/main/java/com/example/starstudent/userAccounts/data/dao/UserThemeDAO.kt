package com.example.starstudent.userAccounts.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.starstudent.userAccounts.data.entities.UserTheme


//Data access object for user themes
@Dao
interface UserThemeDAO {

    //Insert a new user
    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    suspend fun addNewUser(newUser: UserTheme)

    //Get users information
    @Query("" +
            "SELECT * " +
            "FROM user_theme " +
            "WHERE id = :user")
    suspend fun getUserInfo(user: String) :  List<UserTheme>?

    //Return user's saved theme
    @Query("" +
            "SELECT theme " +
            "FROM user_theme " +
            "WHERE id = :user")
    suspend fun getUserTheme(
        user: String
    ) :  String

    //Update user's saved theme
    @Query("" +
            "UPDATE user_theme " +
            "SET theme = :theme " +
            "WHERE id = :user")
    suspend fun updateUserTheme(
        user: String,
        theme : String
    )
}