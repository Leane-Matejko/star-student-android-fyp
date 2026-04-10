package com.example.starstudent.userAccounts.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.starstudent.userAccounts.data.entities.AppUserData

//Data access object for app user
@Dao
interface AppUserDAO {
    //Insert a new user
    @Insert
    suspend fun insertUser(user: AppUserData)

    //Retrieve all user data
    @Query("SELECT * FROM app_user_data")
    suspend fun getAll(): List<AppUserData>

    //Delete an existing user
    @Delete
    suspend fun deleteUser(user: AppUserData)

    //Delete an existing user by their id
    @Query("DELETE FROM app_user_data WHERE id = :email")
    suspend fun deleteUserByName(email: String)
}