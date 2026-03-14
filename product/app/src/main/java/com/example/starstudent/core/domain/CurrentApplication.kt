package com.example.starstudent.core.domain

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.room.Room
import com.example.starstudent.core.data.AppDatabase
import com.example.starstudent.core.data.DatabaseSingleton
import com.example.starstudent.core.data.NetworkConnectivity
import com.example.starstudent.userAccounts.data.entities.UserInfo
import com.google.firebase.FirebaseApp

class CurrentApplication : Application(){

    companion object {
        lateinit var instance: CurrentApplication
            private set
    }

    lateinit var connectivityManager: ConnectivityManager
        private set

    var user = User()
        private set

    private var userInfo = UserInfo(
        "",
        "",
        0,
        false
    )

    val networkConnectivity: NetworkConnectivity by lazy {
        NetworkConnectivity(connectivityManager)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE)
            as ConnectivityManager
        FirebaseApp.initializeApp(this)
    }

    fun setUser(emailValue: String){
        user.setEmail(emailValue)
    }

    suspend fun setUserInfo(){

        val userInfoDAO = DatabaseSingleton
            .getDatabase(instance)
            .userInfoDao()

        val infoResult = userInfoDAO.getUserInfo(user.email.getEmail())

        if (infoResult?.isEmpty() == false){
            userInfo = UserInfo(
                infoResult[0].id,
                infoResult[0].username,
                infoResult[0].birthday,
                infoResult[0].locationAccess
            )
        }
    }

    fun getUserInfo() : UserInfo{
        return userInfo
    }
}