package com.example.starstudent.core

import android.content.Context
import android.content.ContentValues.TAG
import android.util.Log
import android.view.View
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class DatabaseInteractions{

    var resultDoc = arrayOf<String>()

    fun checkDatabaseConnection(context: Context, view: View) {

        if(context.testingInternetConnection()) {
            Log.d("FirestoreCheck", "Starting to connect to database")
            val db = Firebase.firestore
            db.collection("test").document("testingFile")
                .get()
                .addOnSuccessListener { result ->
                    Log.d("FirestoreDebug", "Firestore call succeeded")

                    if (result == null) {
                        Log.d(TAG, "Testing file cannot be found.");
                        resultDoc = arrayOf("File is Empty")
                    }else {
                        Log.d(TAG, "${result.id} => ${result.data}")
                        resultDoc = arrayOf(result.id.toString(), result.data.toString())
                    }

                }.addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents.", exception)
                    Log.d("FirestoreCheck", "Cannot connect to database")
                }

        }else{
            throw NoInternetConnection()
        }
    }

}