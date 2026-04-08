package com.example.starstudent.userAccounts.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.starstudent.userAccounts.data.entities.UserInfo
import com.example.starstudent.userAccounts.data.entities.UserTheme

@Dao
interface UserThemeDAO {

    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    suspend fun addNewUser(newUser: UserTheme)

    @Query("" +
            "SELECT * " +
            "FROM user_theme " +
            "WHERE id = :user")
    suspend fun getUserInfo(user: String) :  List<UserTheme>?

    @Query("" +
            "SELECT theme " +
            "FROM user_theme " +
            "WHERE id = :user")
    suspend fun getUserTheme(
        user: String
    ) :  String

    @Query("" +
            "UPDATE user_theme " +
            "SET theme = :theme " +
            "WHERE id = :user")
    suspend fun updateUserTheme(
        user: String,
        theme : String
    )
}