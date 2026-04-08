package com.example.starstudent.userAccounts.data

import com.example.starstudent.core.data.DatabaseSingleton
import com.example.starstudent.core.domain.CurrentApplication
import com.example.starstudent.userAccounts.data.entities.UserTheme

class AccessUserTheme {
    private val userThemeDAO = DatabaseSingleton
        .getDatabase(
            CurrentApplication
                .instance)
        .userThemeDao()

    suspend fun checkIfUserExists() : String{
        val curUserInfo = userThemeDAO.getUserInfo(
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

    suspend fun addNewUser(userTheme : UserTheme){
        userThemeDAO.addNewUser(
            userTheme
        )
    }

    suspend fun getUserTheme(user : String) : String{
        return userThemeDAO.getUserTheme(
            user
        )
    }

    suspend fun updateUserTheme(
        user : String,
        theme : String
    ){
        userThemeDAO.updateUserTheme(
            user,
            theme
        )
    }
}