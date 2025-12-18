package com.example.starstudent.signInRegister.screens

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.starstudent.core.data.DatabaseInteractions
import com.example.starstudent.core.domain.CurrentApplication
import com.example.starstudent.core.domain.navigation.Screens
import com.example.starstudent.signInRegister.domain.SendEmail
import kotlinx.coroutines.launch

//import kotlinx.coroutines.runBlocking

class SignInRegisterViewModel : ViewModel(){

    fun navigateToSignInScreen(navController: NavController){
        navController.navigate(Screens.SignInScreen.route)
    }

    fun testDB(){
//        runBlocking {
            viewModelScope.launch {


                val db = DatabaseInteractions()

                db.checkDatabaseConnection(CurrentApplication.instance, "test", "testingFile")

                Log.d("TEST", db.resultDoc.contentToString())
            }
//        }
    }

}

