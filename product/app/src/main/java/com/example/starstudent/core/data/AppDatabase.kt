package com.example.starstudent.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.starstudent.studySpaces.data.dao.PausedSessionsDAO
import com.example.starstudent.studySpaces.data.dao.StudySessionsDAO
import com.example.starstudent.studySpaces.data.entities.PausedSessions
import com.example.starstudent.studySpaces.data.entities.StudySessions

import com.example.starstudent.userAccounts.data.entities.AppUserData
import com.example.starstudent.userAccounts.data.dao.AppUserDAO
import com.example.starstudent.userAccounts.data.dao.UserInfoDAO
import com.example.starstudent.userAccounts.data.entities.UserInfo

@Database(
    entities = [
        AppUserData::class,
        UserInfo::class,
        StudySessions::class,
        PausedSessions::class],
    version = 4,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase(){
    abstract fun appUserDao() : AppUserDAO

    abstract fun userInfoDao() : UserInfoDAO

    abstract fun studySessionsDao() : StudySessionsDAO

    abstract fun pausedSessionsDao() : PausedSessionsDAO
}