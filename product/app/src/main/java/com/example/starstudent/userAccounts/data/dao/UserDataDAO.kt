package com.example.starstudent.userAccounts.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.starstudent.userAccounts.data.entities.UserData

@Dao
interface UserDataDAO {
    @Insert
    suspend fun insertUser(user: UserData)

    @Query("SELECT * FROM UserData")
    suspend fun getAll(): List<UserData>

    @Delete
    suspend fun deleteUser(user: UserData)

    @Query("DELETE FROM UserData WHERE id = :email")
    suspend fun deleteUserByName(email: String)
}