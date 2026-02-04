package com.example.starstudent.core.domain

import android.util.Log
import com.example.starstudent.core.data.DatabaseInteractions
import com.example.starstudent.signInRegister.domain.Email
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

class User {

    var email = Email()
        private set


    private val applicationContext = CurrentApplication

    private val di = DatabaseInteractions()

    fun setEmail(emailValue: String){
        email.setEmail(emailValue)
    }

    suspend fun addUserAccount(passwordValue: String){
        val context = applicationContext.instance

        di.checkDatabaseConnection(context, "test", "testFile").await()
        if (di.resultDoc[0] != "File is empty") {
            Log.d("email", CurrentApplication.instance.user.email.getEmail())
            Log.d("password", passwordValue)

            Firebase.firestore
                .collection("users")
                .add(createUserHashMap(passwordValue))
                .await()
        }

    }

    fun createUserHashMap(password: String): HashMap<String, String>{
        return hashMapOf(
            "username" to CurrentApplication.instance.user.email.getEmail(),
            "password" to password
        )
    }

    private fun Unit.await() {
        Log.d("AWAIT", "DATABASE CONNECTION....")
    }
}