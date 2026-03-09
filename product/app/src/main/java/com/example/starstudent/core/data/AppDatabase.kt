package com.example.starstudent.core.data

import androidx.room.Database
import androidx.room.RoomDatabase

import com.example.starstudent.userAccounts.data.entities.UserData
import com.example.starstudent.userAccounts.data.dao.UserDataDAO

@Database(
    entities = [UserData::class],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase(){
    abstract fun userDao() : UserDataDAO
}