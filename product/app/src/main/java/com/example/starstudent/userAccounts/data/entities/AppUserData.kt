package com.example.starstudent.userAccounts.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

//Stores user's id
@Entity(tableName = "app_user_data")
    data class AppUserData(
        @PrimaryKey(
            autoGenerate = false
        )
        val id : String
    )