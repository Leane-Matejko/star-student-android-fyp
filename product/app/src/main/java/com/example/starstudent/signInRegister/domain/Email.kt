package com.example.starstudent.signInRegister.domain

import android.R.attr.password
import android.util.Log
//import androidx.lifecycle.viewModelScope
import com.example.starstudent.core.data.DatabaseInteractions
//import com.example.starstudent.core.data.NetworkConnectivity
import com.example.starstudent.core.domain.CurrentApplication
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
//import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class Email {

    private var email = ""

    private val applicationContext = CurrentApplication

    private val di = DatabaseInteractions()

    private var storedPassword: String? = ""
    fun checkRealEmail(): Boolean {
        if (containsAtSymbol()) {
            return true
        }
        throw NotRealEmailAddress()
    }

    fun containsAtSymbol(): Boolean {
        return email.contains('@')
    }

    fun getEmail(): String {
        return email
    }

    fun setEmail(emailValue: String) {
        email = emailValue
    }

    suspend fun checkEmailExists(userEmail: String, userPassword: String): Boolean {
        val context = applicationContext.instance

        di.checkDatabaseConnection(context, "test", "testFile").await()
        if (di.resultDoc[0] != "File is empty") {
            val result = Firebase.firestore
                .collection("users")
                .whereEqualTo("username", userEmail)
                .get()
                .await()

            storedPassword = result.documents
                .firstOrNull()
                ?.getString("password")

            Log.d("TEST", "Password $userEmail")

            Log.d("TEST", "Password $storedPassword")
        }

        return comparePassword(userPassword, storedPassword)

    }

    fun comparePassword(userPassword: String, realPassword: String?): Boolean {
        return userPassword == realPassword.toString()
    }


    private fun Unit.await() {
        Log.d("AWAIT", "DATABASE CONNECTION....")
    }

}
