package com.example.starstudent.signInRegister.domain

import android.R.attr.password
import android.util.Log
import com.example.starstudent.core.data.DatabaseInteractions
import com.example.starstudent.core.domain.CurrentApplication
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await


/* Model for the email information.
*/
class Email {

    private var email = ""

    private val applicationContext = CurrentApplication

    private val di = DatabaseInteractions()

    private var storedPassword: String? = ""

    //Check if the email contains an @ symbol, throws NotRealEmailAddress otherwise.
    fun checkRealEmail(): Boolean {
        if (containsAtSymbol()) {
            return true
        }
        throw NotRealEmailAddress()
    }

    //Checks the email for an @ symbol
    fun containsAtSymbol(): Boolean {
        return email.contains('@')
    }

    //Getter method for email
    fun getEmail(): String {
        return email
    }

    //Setter method for email
    fun setEmail(emailValue: String) {
        email = emailValue
    }

    suspend fun  preexistingUser(): Boolean{
        val context = applicationContext.instance

        di.checkDatabaseConnection(context, "test", "testFile").await()
        if (di.resultDoc[0] != "File is empty") {
            //Queries the database
            val result = Firebase.firestore
                .collection("users")
                .whereEqualTo("username", getEmail())
                .get()
                .await()
            if(result.isEmpty){
                return false
            }
        }

        throw ExistingUserException()
    }

    //Checks if the email has been stored within the firestore database.
    suspend fun checkEmailExists(userEmail: String, userPassword: String): Boolean {
        val context = applicationContext.instance

        di.checkDatabaseConnection(context, "test", "testFile").await()
        if (di.resultDoc[0] != "File is empty") {
            //Queries the database
            val result = Firebase.firestore
                .collection("users")
                .whereEqualTo("username", userEmail)
                .get()
                .await()

            Log.d("TEST", "Result $result")

            storedPassword = result.documents
                .firstOrNull()
                ?.getString("password")
        }

        return comparePassword(userPassword, storedPassword)

    }

    //Compares the value of 2 passwords entered to see if they are the same.
    fun comparePassword(userPassword: String, realPassword: String?): Boolean {
        if (userPassword == realPassword.toString()){
            return true
        }
        throw EmailOrPasswordNotCorrectException()
    }


    //Buffering for the database connection.
    private fun Unit.await() {
        Log.d("AWAIT", "DATABASE CONNECTION....")
    }

}
