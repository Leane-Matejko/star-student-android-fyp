package com.example.starstudent.userAccounts.data

import com.example.starstudent.core.data.DatabaseSingleton
import com.example.starstudent.core.domain.CurrentApplication

class AccessUserData {

    private val userInfoDAO = DatabaseSingleton
        .getDatabase(
            CurrentApplication
                .instance)
        .userInfoDao()

    private var userInfo = CurrentApplication
        .instance
        .getUserInfo()

    //Return if the user exists
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

    //Return user's id
    fun getUserId() : String{
        return userInfo.id
    }

    //Return user's username
    fun getUsername() : String{
        return userInfo.username
    }

    //Return user's birthday
    fun getBirthday() : Long{
        return userInfo.birthday
    }

    //Return user's location
    fun getUserLocationAccess() : Boolean{
        return userInfo.locationAccess
    }

    //Update user's username
    suspend fun updateUsername(username : String){
        if(!username.isEmpty()){
            userInfoDAO.updateUsername(
                checkIfUserExists(),
                username
            )
            updateInstance()
        }
    }

    //Update user's birthday
    suspend fun updateBirthday(birthday: Long){
        if(birthday != 0L){
            userInfoDAO.updateBirthday(
                checkIfUserExists(),
                birthday
            )
            updateInstance()
        }
    }

    //Update user's location access
    suspend fun updateLocationAccess(locationAccess : Boolean){
        userInfoDAO.updateLocationAccess(
            checkIfUserExists(),
            locationAccess
        )
        updateInstance()
    }

    //Updating app's access the user information
    suspend fun updateInstance(){
        CurrentApplication.instance.setUserInfo()
        userInfo = CurrentApplication.instance.getUserInfo()
    }
}