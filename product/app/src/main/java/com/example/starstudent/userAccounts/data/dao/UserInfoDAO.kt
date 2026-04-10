package com.example.starstudent.userAccounts.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.starstudent.userAccounts.data.entities.UserInfo

//Data access object for user info
@Dao
interface UserInfoDAO {

    //Insert a new user
    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    suspend fun addNewUser(newUser: UserInfo)

    //Delete a user
    @Delete
    suspend fun deleteUser(deletedUser: UserInfo)

    //Retrieve user information with their id
    @Query("SELECT * FROM user_info WHERE id = :user")
    suspend fun getUserInfo(user: String) :  List<UserInfo>?

    //Update user's username
    @Query("UPDATE user_info SET username = :username WHERE id = :user")
    suspend fun updateUsername(user : String, username : String)

    //Update user birthday
    @Query("UPDATE user_info SET birthday = :birthday WHERE id = :user")
    suspend fun updateBirthday(user : String, birthday : Long)

    @Query("UPDATE user_info SET locationAccess = :locationAccess WHERE id = :user")
    suspend fun updateLocationAccess(user : String, locationAccess : Boolean)
}