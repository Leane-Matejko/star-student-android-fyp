package com.example.starstudent.userAccounts.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

//Stores user theme
@Entity(tableName = "user_theme")
data class UserTheme(
    @PrimaryKey(
        autoGenerate = false
    )
    val id : String,
    val theme: String
)
