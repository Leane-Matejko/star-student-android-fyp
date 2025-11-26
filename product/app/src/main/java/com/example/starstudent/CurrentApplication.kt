package com.example.starstudent

import android.app.Application

class CurrentApplication : Application(){
    init{
        instance = this
    }

    companion object {
        lateinit var instance: CurrentApplication
            private set
    }
}