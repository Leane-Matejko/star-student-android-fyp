package com.example.starstudent.userAccounts.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "user_info")
data class UserInfo(
    @PrimaryKey(
        autoGenerate = false
    )
    val id : String,
    val username: String,
    val birthday : Long,
    val locationAccess : Boolean

)
