package com.example.starstudent.core.domain

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager

class CurrentApplication : Application(){
    lateinit var connectivityManager: ConnectivityManager
        private set

    companion object {
        lateinit var instance: CurrentApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE)
            as ConnectivityManager
    }
}