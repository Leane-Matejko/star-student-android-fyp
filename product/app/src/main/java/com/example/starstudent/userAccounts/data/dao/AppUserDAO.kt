package com.example.starstudent.userAccounts.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.starstudent.userAccounts.data.entities.AppUserData

@Dao
interface AppUserDAO {
    @Insert
    suspend fun insertUser(user: AppUserData)

    @Query("SELECT * FROM app_user_data")
    suspend fun getAll(): List<AppUserData>

    @Delete
    suspend fun deleteUser(user: AppUserData)

    @Query("DELETE FROM app_user_data WHERE id = :email")
    suspend fun deleteUserByName(email: String)
}