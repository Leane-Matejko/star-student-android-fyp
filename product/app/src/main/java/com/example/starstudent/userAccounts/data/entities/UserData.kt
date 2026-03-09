package com.example.starstudent.userAccounts.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
    data class UserData(
        @PrimaryKey(
            autoGenerate = false
        )
        val id : String
    )