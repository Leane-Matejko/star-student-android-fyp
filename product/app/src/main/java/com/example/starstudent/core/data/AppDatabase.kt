package com.example.starstudent.core.data

import androidx.room.Database
import androidx.room.RoomDatabase

import com.example.starstudent.userAccounts.data.entities.AppUserData
import com.example.starstudent.userAccounts.data.dao.AppUserDAO
import com.example.starstudent.userAccounts.data.dao.UserInfoDAO
import com.example.starstudent.userAccounts.data.entities.UserInfo

@Database(
    entities = [AppUserData::class, UserInfo::class],
    version = 2,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase(){
    abstract fun appUserDao() : AppUserDAO

    abstract fun userInfoDao() : UserInfoDAO
}