package com.example.starstudent.userAccounts.data

import com.example.starstudent.core.data.DatabaseSingleton
import com.example.starstudent.core.domain.CurrentApplication

class AccessUserData {

    private val userInfoDAO = DatabaseSingleton
        .getDatabase(
            CurrentApplication
                .instance)
        .userInfoDao()

    private val userInfo = CurrentApplication
        .instance
        .getUserInfo()

    suspend fun checkIfUserExists() : String{
        val curUserInfo = userInfoDAO.getUserInfo(
            CurrentApplication
                .instance
                .user
                .email
                .getEmail()
        )
        if(curUserInfo?.isNotEmpty() == true){
            return curUserInfo[0].id
        }
        return ""
    }

    fun getUserId() : String{
        return userInfo.id
    }

    fun getUsername() : String{
        return userInfo.username
    }

    fun getBirthday() : Long{
        return userInfo.birthday
    }

    fun getUserLocationAccess() : Boolean{
        return userInfo.locationAccess
    }

    suspend fun updateUsername(username : String){
        if(!username.isEmpty()){
            userInfoDAO.updateUsername(
                checkIfUserExists(),
                username
            )
        }
    }

    suspend fun updateBirthday(birthday: Long){
        if(birthday != 0L){
            userInfoDAO.updateBirthday(
                checkIfUserExists(),
                birthday
            )
        }
    }

    suspend fun updateLocationAccess(locationAccess : Boolean){
        userInfoDAO.updateLocationAccess(
            checkIfUserExists(),
            locationAccess
        )
    }
}