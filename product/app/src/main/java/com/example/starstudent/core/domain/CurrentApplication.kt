package com.example.starstudent.core.domain

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.example.starstudent.core.data.NetworkConnectivity
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

//    fun getNetworkConnectivity(): NetworkConnectivity{
//        return networkConnectivity
//    }
}